import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.ConcurrentHashMap;

public final class Server 
{
	public static ConcurrentHashMap<String, Book> books;

	public static void main(String argv[]) throws Exception 
	{
		// Get the port number from the command line.
		int port;
		if (argv.length > 0)
		{
			try
			{
				port = Integer.valueOf(argv[0]);
			}
			catch (NumberFormatException e)
			{
				System.out.println("User entered port not valid, defaulting to 5555");
				port = 5555;
			}
		}
		else
		{
			System.out.println("No port entered by user, defaulting to 5555");
			port = 5555;
		}
		ConcurrentHashMap<String,Book> books = new ConcurrentHashMap<String,Book>();
		// Establish the listen socket.
		ServerSocket socket;

		try
		{
			socket = new ServerSocket(port);
		} 
		catch (IllegalArgumentException e)
		{
			System.out.println("User entered port not valid, defaulting to 5555");
			port = 5555;
			socket = new ServerSocket(port);
		}
		
		// Process HTTP service requests in an infinite loop.
		System.out.println("Starting server");
		while (true) 
		{
			// Listen for a TCP connection request.
			Socket connection = socket.accept();
			
			// Construct an object to process the HTTP request message.
			HttpRequest request = new HttpRequest(connection,books);
			
			// Create a new thread to process the request.
			Thread thread = new Thread(request);
			
			// Start the thread.
			thread.start();
		}
    }
}
