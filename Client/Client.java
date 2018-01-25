import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class Client 
{
	public static void main(String argv[]) throws IOException  
	{
		// Get the port number from the user.
		int port = 5555;
		
		// Get the server address from the user
		String host = InetAddress.getLocalHost().getHostAddress();//"10.84.98.43";
		
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
		String fromUser;
		
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
		socket.close();
	}
}