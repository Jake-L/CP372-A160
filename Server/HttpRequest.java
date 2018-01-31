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

  @SuppressWarnings("resource")
private void processRequest() throws Exception 
	{
		// Get a reference to the socket's input and output streams.
		System.out.println("processing client request");

		// read from the client
		Scanner input = new Scanner(socket.getInputStream());
		
		// write to the client
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("Connection established");

		// Get the request line of the HTTP request message.
		String operation = input.nextLine();
		
		System.out.println("Operation: " + operation);
		
		String requestLine = input.nextLine();
		
		if (operation.equals("GET ALL")) {
			String result = "";
			for(String key:this.books.keySet())
				result += this.books.get(key).toString();
			out.println(result + "\nSUCCESS");
		}
		else if (operation.equals("GET")) {
			String[] isbn = requestLine.split(" ");
			if (isbn.length == 2)
				out.println(this.books.get(isbn[1]).toString() + "\nSUCCESS");
			else 
				throw new IllegalArgumentException("Must be in format \"ISBN nnnnn\"");
		}
		else if (operation.equals("SUBMIT")) {
			String[] isbn = requestLine.split(" ");
			if (isbn.length == 2) {
				Book book = addOrUpdateFields(new Book(),input);
				this.books.put(isbn[1],book);
				out.println("SUCCESS");
			}
			else
				throw new IllegalArgumentException("Must be in format \"ISBN nnnnn\"");
		}
		else if (operation.equals("REMOVE")) {
			String[] isbn = requestLine.split(" ");
			if (isbn.length == 2) {
				this.books.remove(isbn[1]);
				out.println("SUCCESS");
			}
			else 
				throw new IllegalArgumentException("Must be in format \"ISBN nnnnn\"");
		}
		else if (operation.equals("UPDATE")) {
			String[] isbn = requestLine.split(" ");
			if (isbn.length == 2) {
				this.books.put(isbn[1],addOrUpdateFields(this.books.get(isbn[1]),input));
				out.println("SUCCESS");
			}
			else
				throw new IllegalArgumentException("Must be in format \"ISBN nnnnn\"");
		}
		
		out.println("SUCCESS");
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
  
  private Book addOrUpdateFields(Book book, Scanner input) {
	  String requestLine = input.nextLine();
	  while (!requestLine.equals("STOP")) {
		  String[] tokens = requestLine.split(" ");
		  if (tokens.length == 2)
			  book.fields.put(tokens[0], tokens[1]);
		  else
			  throw new IllegalArgumentException("The body of your submit request is formatted incorrectly");
		  requestLine = input.nextLine();
	  }
	  return book;
  }
}

