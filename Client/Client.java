import java.io.* ;
import java.net.* ;
import java.util.* ;
import javax.swing.*; 
import javax.swing.table.*;
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
            	try
            	{
            		host = InetAddress.getLocalHost().getHostAddress();
            	}
            	catch (UnknownHostException e)
            	{

            	}
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
	private static JTextField portTextField;
	private static JTextField hostTextField;
	private static JCheckBox bibtexOption;
	private static JCheckBox getAllOption;

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

        optionSubmit.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                	isbnTextField.setEnabled(true);
					authorTextField.setEnabled(true);
					titleTextField.setEnabled(true);
					yearTextField.setEnabled(true);
					publisherTextField.setEnabled(true);
					getAllOption.setSelected(false);
				}
            }
        });

        optionUpdate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                	isbnTextField.setEnabled(true);
					authorTextField.setEnabled(true);
					titleTextField.setEnabled(true);
					yearTextField.setEnabled(true);
					publisherTextField.setEnabled(true);
					getAllOption.setSelected(false);
				}
            }
        });

        optionRemove.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                	isbnTextField.setEnabled(true);
					authorTextField.setEnabled(true);
					titleTextField.setEnabled(true);
					yearTextField.setEnabled(true);
					publisherTextField.setEnabled(true);
					getAllOption.setSelected(false);
				}
            }
        });

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

        JLabel bibtexLabel = new JLabel("bibtex format?");
        bibtexLabel.setBounds(20, 215, 100, 20);
        bibtexOption = new JCheckBox();
        bibtexOption.setBounds(140, 215, 20, 20);
        frame.getContentPane().add(bibtexLabel);
        frame.getContentPane().add(bibtexOption);

        JLabel getAllLabel = new JLabel("GET ALL");
        getAllLabel.setBounds(20, 240, 100, 20);
        getAllOption = new JCheckBox();
        getAllOption.setBounds(140, 240, 20, 20);
        frame.getContentPane().add(getAllLabel);
        frame.getContentPane().add(getAllOption);
        getAllOption.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                	isbnTextField.setEnabled(false);
					authorTextField.setEnabled(false);
					titleTextField.setEnabled(false);
					yearTextField.setEnabled(false);
					publisherTextField.setEnabled(false);
					optionGet.setSelected(true);
                }
                else
                {
                	isbnTextField.setEnabled(true);
					authorTextField.setEnabled(true);
					titleTextField.setEnabled(true);
					yearTextField.setEnabled(true);
					publisherTextField.setEnabled(true);
                }
            }
        });

        // Input field for the host
        JLabel hostLabel = new JLabel("Host:");
        hostLabel.setBounds(20, 270, 50, 20);
        hostTextField = new JTextField();
        hostTextField.setBounds(75, 270, 120, 20);
        hostTextField.setText(host);
        frame.getContentPane().add(hostLabel);
        frame.getContentPane().add(hostTextField);

        // Input field for the port
        JLabel portLabel = new JLabel("Port:");
        portLabel.setBounds(250, 270, 50, 20);
        portTextField = new JTextField();
        portTextField.setBounds(305, 270, 50, 20);
        portTextField.setText(Integer.toString(port));
        frame.getContentPane().add(portLabel);
        frame.getContentPane().add(portTextField);

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

		if (isInteger(portTextField.getText()))
		{
			port = Integer.parseInt(portTextField.getText());
		}
		else
		{
			errorLabel.setText("Please enter a valid port");
			return;
		}
		host = hostTextField.getText();

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
			if (getAllOption.isSelected())
			{
				request = "GET ALL";
				errorLabel.setText("Request submitted");
				submitRequest(request);
			}
			else
			{
				request = "GET";
			}
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

			if (isValidIsbn(isbnTextField.getText().trim().replaceAll("\\-","")))
			{
				request += "\nISBN " + isbnTextField.getText().trim().replaceAll("\\-","");
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
			request += "\nAUTHOR " + authorTextField.getText();
			isValid = true;
		}

		if (titleTextField.getText() != null && !titleTextField.getText().isEmpty())
		{
			request += "\nTITLE " + titleTextField.getText();
			isValid = true;
		}

		if (yearTextField.getText() != null && !yearTextField.getText().isEmpty())
		{
			if (isInteger(yearTextField.getText()) && yearTextField.getText().trim().length() <= 4 && Integer.parseInt(yearTextField.getText()) <= 2018)
			{
				request += "\nYEAR " + yearTextField.getText();
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
			request += "\nPUBLISHER " + publisherTextField.getText();
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
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());

			// send the request to the server
			out.println(request + "\nSTOP");
			System.out.println("Submitting request:\n" + request);
			

			// get the response from the server
			while (in.hasNextLine()) 
			{
				String temp = in.nextLine();
				System.out.println("Read from server :" + temp);
				if (temp.trim().equals("SUCCESS"))
				{
					errorLabel.setText("Server processed request successfully!");
					System.out.println("Server processed request successfully!");
					System.out.println("Ending connection");

					if (optionGet.isSelected())
					{
						if (bibtexOption.isSelected())
						{
							openGetResponseBibtex(fromServer);
						}
						else
						{
							openGetResponse(fromServer);
						}
					}

					break;
				}
				else if (temp.trim().equals("FAILURE"))
				{
					errorLabel.setText("Server returned: invalid request!");
					System.out.println("Server returned: invalid request!");
					System.out.println("Ending connection");
				}
				else
				{
					fromServer += temp + "\n";
				}
			}

			out.close();
			in.close();
			socket.close();
		} 
		catch (UnknownHostException e) 
		{
			errorLabel.setText("Connection error. Check your internet and the selected host and port");
		} 
		catch (IOException e) 
		{
			errorLabel.setText("Connection error. Check your internet and the selected host and port");
		}
	}

	private static void openGetResponse(String r)
	{
		JFrame frame = new JFrame("GET Results");
		System.out.println("OpenGetResponse: " + r);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setBounds(500,200,600,400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        String[] columns = {"ISBN", "Title", "Author", "Publisher", "Year"};
 		int[] columnWidth = {50, 50, 50, 80, 50};
 		Object[][] data = getDataFromResponse(r);

        // create table to hold the data
        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < table.getRowCount(); i++)
        {
        	for (int j = 0; j < table.getColumnCount(); j++)
        	{
        		TableCellRenderer render = table.getCellRenderer(i, j);
	            Component c = table.prepareRenderer(render, i, j);
	            columnWidth[j] = Math.max(c.getPreferredSize().width + 4, columnWidth[j]);
        	}
        }

        for (int j = 0; j < table.getColumnCount(); j++)
        {
        	table.getColumnModel().getColumn(j).setPreferredWidth(columnWidth[j]);
        }

        // create scroll bar for the table
        JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scroll);

        //Display the window.
        frame.setVisible(true);
	}

	private static void openGetResponseBibtex(String r)
	{
		JFrame frame = new JFrame("GET Results");
		frame.getContentPane().setLayout(null);
		frame.setBounds(500,200,400,400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	    // create the text area
	    JTextArea display = new JTextArea(16, 58);
	    display.setEditable(false); // set textArea non-editable
	    display.setLineWrap(true);
		display.setWrapStyleWord(true);

	    // create scroll bar for the text area
	    JScrollPane scroll = new JScrollPane(display);
	    //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(50, 50, 300, 300);
	    frame.getContentPane().add(scroll);	

	    String[] columns = {"ISBN", "Title", "Author", "Publisher", "Year"};
	    Object[][] data = getDataFromResponse(r);

	    for (int i = 0; i < data.length; i++)
	    {
	    	String code = "";
	    	if (data[i][2] != null)
	    	{
	    		code = data[i][2].toString().split(" ")[0];
	    	}
	    	if (data[i][4] != null)
	    	{
	    		code += data[i][4];
	    	}
	    	display.append("@book {" + code + "\n");

	    	for (int j = 0; j < data[i].length; j++)
	    	{
	    		if (data[i][j] != null)
	    		{
	    			display.append(columns[j] + " = {" + data[i][j] + "},\n");
	    		}
	    	}

	    	display.append("]\n\n");
	    }

        //Display the window.
        frame.setVisible(true);
	}

	private static Object[][] getDataFromResponse(String r)
	{
        String[] response = r.split("\n");

        // count the number of records, to know how big to make the table
        int n = 0;
        for (int i = 0; i < response.length; i++)
        {
        	String[] tokens = response[i].split(" ");
        	if (tokens[0].equals("ISBN"))
        	{
	        	n++;
	        }
	    }
	    if (response.length > 0 && n == 0)
	    {
	    	n = 1;
	    }

        Object[][] data = new Object[n][5];
        n = 0;

        for (int i = 0; i < response.length; i++)
        {
        	String[] tokens = response[i].split(" ");
        	System.out.println(response[i]);

        	if (tokens[0].equals("ISBN"))
        	{
        		if (i > 0)
        		{
	        		n++;
	        	}

	        	data[n][0] = response[i].substring(tokens[0].length() + 1);
        	}
        	else if (tokens[0].equals("TITLE"))
        	{
        		data[n][1] = response[i].substring(tokens[0].length() + 1);
        	}
        	else if (tokens[0].equals("AUTHOR"))
        	{
        		data[n][2] = response[i].substring(tokens[0].length() + 1);
        	}
        	else if (tokens[0].equals("PUBLISHER"))
        	{
        		data[n][3] = response[i].substring(tokens[0].length() + 1);
        	}
        	else if (tokens[0].equals("YEAR"))
        	{
        		data[n][4] = response[i].substring(tokens[0].length() + 1);
        	}
        }

        return data;
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

	private static boolean isValidIsbn(String isbn)
	{
		if (isInteger(isbn) && isbn.length() == 13)
		{
			int checksum = 0;

			for (int i = 0; i < isbn.length(); i++)
			{
				if (i % 2 == 0)
				{
					checksum += Character.getNumericValue(isbn.charAt(i));
				}
				else
				{
					checksum += 3 * Character.getNumericValue(isbn.charAt(i));
				}
			}

			if (checksum % 10 == 0)
			{
				return true;
			}       
		}

		return false;
	}
}