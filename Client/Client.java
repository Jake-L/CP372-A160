import java.io.* ;
import java.net.* ;
import java.util.* ;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class Client 
{
	// Get the port number from the user.
	private static int port = 5555;
		
	// Get the server address from the user
	private static String host;//"10.84.98.43";

	public static void main(String argv[]) throws IOException  
	{
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				openGUI();
			}
		});
	}

	private static JLabel errorLabel;
	private static JRadioButton optionSubmit;
	private static JRadioButton optionUpdate;
	private static JRadioButton optionGet;
	private static JRadioButton optionRemove;
	private static JTextField isbnTextField;
	private static JTextField authorTextField;
	private static JTextField titleTextField;
	private static JTextField yearTextField;
	private static JTextField publisherTextField;

	private static void openGUI()
	{
		JFrame frame = new JFrame("CP372A1");
		frame.getContentPane().setLayout(null);

		//setBounds(x, y, width, height)
		frame.setBounds(100,100,400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // components to let user select the operation
        JLabel keywordLabel = new JLabel("Please select the operation to perform:");
        keywordLabel.setBounds(20, 20, 350, 20);
        optionSubmit = new JRadioButton("SUBMIT");
        optionSubmit.setBounds(20, 40, 80, 20);
        optionUpdate = new JRadioButton("UPDATE");
        optionUpdate.setBounds(100, 40, 80, 20);
        optionGet = new JRadioButton("GET");
        optionGet.setBounds(180, 40, 80, 20);
        optionRemove = new JRadioButton("REMOVE");
        optionRemove.setBounds(260, 40, 80, 20);

        ButtonGroup keywordGroup = new ButtonGroup();
        keywordGroup.add(optionSubmit);
        keywordGroup.add(optionUpdate);
        keywordGroup.add(optionGet);
        keywordGroup.add(optionRemove);
 
 		frame.add(keywordLabel);
        frame.add(optionSubmit);
        frame.add(optionUpdate);
        frame.add(optionGet);
        frame.add(optionRemove);

        // Input field for the ISBN
        JLabel isbnLabel = new JLabel("13 digit ISBN:");
        isbnLabel.setBounds(20, 70, 100, 20);
        isbnTextField = new JTextField();
        isbnTextField.setBounds(140, 70, 150, 20);
        frame.getContentPane().add(isbnLabel);
        frame.getContentPane().add(isbnTextField);

        // Input field for the author
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 100, 100, 20);
        authorTextField = new JTextField();
        authorTextField.setBounds(140, 100, 150, 20);
        frame.getContentPane().add(authorLabel);
        frame.getContentPane().add(authorTextField);

        // Input field for the title
        JLabel titleLabel = new JLabel("Book Title:");
        titleLabel.setBounds(20, 130, 100, 20);
        titleTextField = new JTextField();
        titleTextField.setBounds(140, 130, 150, 20);
        frame.getContentPane().add(titleLabel);
        frame.getContentPane().add(titleTextField);
				
		// Input field for the year
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(20, 160, 100, 20);
        yearTextField = new JTextField();
        yearTextField.setBounds(140, 160, 150, 20);
        frame.getContentPane().add(yearLabel);
        frame.getContentPane().add(yearTextField);

        // Input field for the publisher
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setBounds(20, 190, 100, 20);
        publisherTextField = new JTextField();
        publisherTextField.setBounds(140, 190, 150, 20);
        frame.getContentPane().add(publisherLabel);
        frame.getContentPane().add(publisherTextField);

        // button to submit request
        JButton submitButton = new JButton();
        submitButton.setText("Submit Request");
        submitButton.setBounds(135, 300, 130, 20);
        frame.getContentPane().add(submitButton);
        submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// attempt to create and submit the request
				createRequest();
			}
		});

		// text field to show eerors
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setBounds(0, 330, 400, 20);
		errorLabel.setForeground(Color.red);
		frame.getContentPane().add(errorLabel);

        //Display the window.
        frame.setVisible(true);
	}

	private static void createRequest()
	{
		System.out.println("Creating request...");
		String request;
		boolean isValid = false;
		errorLabel.setText("");

		// SUBMIT request
		if (optionSubmit.isSelected())
		{
			request = "SUBMIT";
		}
		else if (optionUpdate.isSelected())
		{
			request = "UPDATE";
		}
		else if (optionGet.isSelected())
		{
			request = "GET";
		}
		else if (optionRemove.isSelected())
		{
			request = "REMOVE";
		}
		else
		{
			errorLabel.setText("Please select an operation");
			return;
		}

		if (isbnTextField.getText() != null && !isbnTextField.getText().isEmpty())
		{

			if (isbnTextField.getText().trim().length() == 13 && isInteger(isbnTextField.getText().trim()))
			{
				request += "\n" + isbnTextField.getText();
				isValid = true;
			}
			else
			{
				System.out.println(isbnTextField.getText().trim().length());
				errorLabel.setText("Please enter valid 13 digit ISBN");
				return;
			}
		}
		else if (optionSubmit.isSelected())
		{
			errorLabel.setText("Must enter ISBN for this operation");
			return;
		}

		if (authorTextField.getText() != null && !authorTextField.getText().isEmpty())
		{
			request += "\n" + authorTextField.getText();
			isValid = true;
		}

		if (titleTextField.getText() != null && !titleTextField.getText().isEmpty())
		{
			request += "\n" + titleTextField.getText();
			isValid = true;
		}

		if (yearTextField.getText() != null && !yearTextField.getText().isEmpty())
		{
			if (isInteger(yearTextField.getText()) && yearTextField.getText().trim().length() <= 4 && Integer.parseInt(yearTextField.getText()) <= 2018)
			{
				request += "\n" + yearTextField.getText();
				isValid = true;
			}
			else
			{
				errorLabel.setText("Please enter valid year");
				return;
			}
		}

		if (publisherTextField.getText() != null && !publisherTextField.getText().isEmpty())
		{
			request += "\n" + publisherTextField.getText();
			isValid = true;
		}

		if (isValid)
		{
			errorLabel.setText("Request submitted");
			submitRequest(request);
		}
		else
		{
			errorLabel.setText("All text fields cannot be left blank");
		}
	}

	private static void submitRequest(String request)
	{
		Socket socket = null;
		PrintWriter out = null;
		Scanner in = null;
		String fromServer = "";

		try 
		{
			host = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());

			// send the request to the server
			out.println(request);
			System.out.println("Submitting request" + request);
			

			// get the response from the server
			while (in.hasNextLine()) 
			{
				String temp = in.nextLine();
				System.out.println(temp);
				if (temp.trim().equals("SUCCESS"))
				{
					errorLabel.setText("Server processed request successfully!");
					System.out.println("Ending connection");
					break;
				}
				else
				{
					fromServer += temp;
				}
				
			}

			out.close();
			in.close();
			socket.close();
		} 
		catch (UnknownHostException e) 
		{
			errorLabel.setText("Connection error. Check your internet or try a different port.");
		} 
		catch (IOException e) 
		{
			errorLabel.setText("Connection error. Check your internet or try a different port.");
		}
	}

	private static void openGetResponse(String response)
	{
		JFrame frame = new JFrame("GET Results");
		frame.getContentPane().setLayout(null);

		//setBounds(x, y, width, height)
		frame.setBounds(500,200,400,400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // create table to hold the data
        JTable table = new JTable(20, 2);
        //table.setBounds(50, 50, 300, 300);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // create scroll bar for the table
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 50, 300, 300);
        frame.getContentPane().add(scroll);

        //Display the window.
        frame.setVisible(true);
	}

	private static boolean isInteger(String s)
	{
		try
		{
			if (s != null && Long.parseLong(s) > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}