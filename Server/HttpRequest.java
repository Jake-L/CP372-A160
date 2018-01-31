import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.util.concurrent.ConcurrentHashMap;

final class HttpRequest implements Runnable 
{
	final static String CRLF = "\r\n";
	Socket socket;
	ConcurrentHashMap<String, Book> books;
	
	// Constructor
	public HttpRequest(Socket socket, ConcurrentHashMap<String,Book> books) throws Exception 
	{
		this.socket = socket;
		this.books = books;
	}
	
	// Implement the run() method of the Runnable interface.
	public void run() 
	{
		try 
		{
			processRequest();
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}

  private void processRequest() throws Exception 
	{
		// Get a reference to the socket's input and output streams.
		System.out.println("processing client request");

		// read from the client
		Scanner input = new Scanner(socket.getInputStream());
		
		// write to the client
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("connected to server");

		// Get the request line of the HTTP request message.
		String operation = input.nextLine();
		Book book = new Book();
		
		System.out.println("Operation: " + operation);
		ArrayList<String> allTokens = new ArrayList<String>();
		
		String requestLine = input.nextLine();
		
		while (!requestLine.equals("END"))
		{
			out.println("Request recieved: " + requestLine);
			System.out.println("Request recieved: " + requestLine);
			String[] tokens = requestLine.split(" ");
			book.fields.put(tokens[0], tokens[1]);
			allTokens.add(tokens[0]);
			requestLine = input.nextLine();
		}
		
		System.out.println("Doing things");
		for (String k:allTokens) {
			System.out.println(String.format("%s: %s", k, book.fields.get(k)));
		}

		input.close();
		out.close();
		socket.close();
	}

  private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception 
	{
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;
	
		// Copy requested file into the socket's output stream.
		while ((bytes = fis.read(buffer)) != -1) 
		{
				os.write(buffer, 0, bytes);
		}
  }

  private static String contentType(String fileName) 
	{
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) 
		{
			return "text/html";
		}
		
		if(fileName.endsWith(".ram") || fileName.endsWith(".ra")) 
		{
	    return "audio/x-pn-realaudio";
		}
		
		return "application/octet-stream" ;
  }
}

