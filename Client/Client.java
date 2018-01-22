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
	
			fromUser = keyboardIn.nextLine();
			
			if (fromUser != null) 
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