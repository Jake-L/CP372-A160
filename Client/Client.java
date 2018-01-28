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
		/*
		Socket socket = null;
		PrintWriter out = null;
		Scanner in = null;

		try 
		{
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());
		} 
		catch (UnknownHostException e) 
		{
			System.err.println("Don't know about host.");
			System.exit(1);
		} 
		catch (IOException e) 
		{
			System.err.println("Couldn't get I/O for the connection.");
			System.exit(1);
		}

		Scanner keyboardIn = new Scanner(System.in);
		String fromServer;
		String fromUser;*/

 		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				openGUI();
			}
		});
		
		/*
		System.out.println("Connected to server");

		while (in.hasNextLine()) 
		{
			fromServer = in.nextLine();
			System.out.println("Server: " + fromServer);

			System.out.println("Enter input to send to server, or press a number from 1-6 to send a premade message\n1 - SUBMIT book1\n2 - UPDATE book1\n3 - GET book1 & book2 by author\n4 - GET ALL\n5 - SUBMIT book2\n6 - REMOVE book1 & book2 by author");
	
			fromUser = keyboardIn.nextLine();

			if (fromUser.contains("1"))
			{
				out.println("SUBMIT\nISBN 9783161484100\nTITLE Modular Algorithms\nAUTHOR Gerhard\nPUBLISHER Mir");
			}
			else if (fromUser.contains("2"))
			{
				out.println("UPDATE\nISBN 9783161484100\nYEAR 2004\nPUBLISHER Springer");
			}
			else if (fromUser.contains("3"))
			{
				out.println("GET\nAUTHOR Gerhard");
			}
			else if (fromUser.contains("4"))
			{
				out.println("GET\nALL");
			}
			else if (fromUser.contains("5"))
			{
				out.println("SUBMIT\nISBN 9780785195368\nTITLE Daredevil\nAUTHOR Gerhard\nPUBLISHER Marvel");
			}
			else if (fromUser.contains("6"))
			{
				out.println("REMOVE\nAUTHOR Gerhard");
			}
			else if (fromUser != null) 
			{
				System.out.println("Client: " + fromUser);
				out.println(fromUser + "\ntestline2");
			}
		}

		out.close();
		in.close();
		keyboardIn.close();
		socket.close();*/
	}

	private static JLabel errorLabel;
	private static JRadioButton optionSubmit;
	private static JRadioButton optionUpdate;
	private static JRadioButton optionGet;
	private static JRadioButton optionRemove;
	private static JTextField isbnTextField;
	private static JTextField authorTextField;
	private static JTextField titleTextField;

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
				String response = createRequest();
				errorLabel.setText(response);
			}
		});

		// text field to show eerors
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setBounds(50, 330, 300, 20);
		errorLabel.setForeground(Color.red);
		frame.getContentPane().add(errorLabel);

        //Display the window.
        frame.setVisible(true);
	}

	private static String createRequest()
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
			return "Please select an operation";
		}

		if (isbnTextField.getText() != null && !isbnTextField.getText().isEmpty())
		{
			request += "\n" + isbnTextField.getText();
			isValid = true;
		}
		else if (optionSubmit.isSelected())
		{
			return "Must enter ISBN for this operation";
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

		if (isValid)
		{
			submitRequest(request);
			return "Request submitted";
		}
		else
		{
			System.out.println("Request is not valid :(");
			return "All text fields cannot be left blank";
		}
	}

	private static String submitRequest(String request)
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
			return "Couldn't connect to host. Check your internet or try a different port.";
		} 
		catch (IOException e) 
		{
			return "Couldn't get I/O for the connection.";
		}



		return fromServer;
	}
}