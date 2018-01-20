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
		String host = "10.133.181.231";
		
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try 
		{
				socket = new Socket(host, port);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;
		String fromUser;
		
		System.out.println("Connected to server");

		while ((fromServer = in.readLine()) != null) 
		{
			System.out.println("Server: " + fromServer);
	
			fromUser = stdIn.readLine();
			
			if (fromUser != null) 
			{
				System.out.println("Client: " + fromUser);
				out.println(fromUser);
			}
		}

		out.close();
		in.close();
		stdIn.close();
		socket.close();
	}
}