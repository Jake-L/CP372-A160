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

		// Get the request line of the HTTP request message.
		String operation = input.nextLine();
		
		System.out.println("Operation: " + operation);
		
		
		if (operation.equals("GET ALL")) {
			String result = "";
			for(String key:this.books.keySet())
				result += this.books.get(key).toString();
			out.println(result + "SUCCESS");
		}
		else if (operation.equals("GET")) {
			ArrayList<Book> books = getMatchingBooks(input);
			StringBuilder result = new StringBuilder();
			for (Book book: books) {
				result.append(book.toString());
			}
			System.out.print(result.toString() + "SUCCESS");
			out.println(result.toString() + "SUCCESS");
		}
		else if (operation.equals("SUBMIT")) {
			String[] isbn = input.nextLine().split(" ");
			if (isbn.length == 2) {
				Book book = addOrUpdateFields(new Book(),input);
				book.fields.put("ISBN",isbn[1]);
				this.books.put(isbn[1],book);
				out.println("SUCCESS");
			}
			else {
				out.println("FAILURE");
				throw new IllegalArgumentException("Must be in format \"ISBN nnnnn\"");
			}
		}
		else if (operation.equals("REMOVE")) {
			ArrayList<Book> books = getMatchingBooks(input);
			for (Book book: books) {
				this.books.remove(book.fields.get("ISBN"));
			}
			out.println("SUCCESS");
		}
		else if (operation.equals("UPDATE")) {
			String[] isbn = input.nextLine().split(" ");
			if (isbn.length == 2) {
				this.books.put(isbn[1],addOrUpdateFields(this.books.get(isbn[1]),input));
				out.println("SUCCESS");
			}
			else {
				out.println("FAILURE");
				throw new IllegalArgumentException("Must be in format \"ISBN nnnnn\"");
			}
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
  
  private Book addOrUpdateFields(Book book, Scanner input) {
	  String requestLine = input.nextLine();
	  while (!requestLine.equals("STOP")) {
		  String[] tokens = requestLine.split(" ");
		  if (tokens.length >= 2) {
			  StringBuilder sb = new StringBuilder();
			  for(int i = 1; i < tokens.length; i++)
				  sb.append(tokens[i] + " ");
			  book.fields.put(tokens[0], sb.toString().trim());
		  }
		  else
			  throw new IllegalArgumentException("The body of your submit request is formatted incorrectly");
		  requestLine = input.nextLine();
	  }
	  return book;
  }
  
  private ArrayList<Book> getMatchingBooks(Scanner input) {
	  String requestLine = input.nextLine();
	  ArrayList<Book> result = new ArrayList<Book>();
	  ArrayList<String> keys = new ArrayList<String>(); 
	  ArrayList<String> values = new ArrayList<String>();
	  
	  if (requestLine.split(" ")[0] == "ISBN") {
		  String isbn = requestLine.split(" ")[1];
		  result.add(books.get(isbn));
		  return result;
	  }
	  
	  while (!requestLine.equals("STOP")) {
		  String[] tokens = requestLine.split(" ");
		  if (tokens.length >= 2) {
			  keys.add(tokens[0]);
			  StringBuilder sb = new StringBuilder();
			  for(int i = 1; i < tokens.length; i++)
				  sb.append(tokens[i]);
			  values.add(sb.toString());
		  }
		  requestLine = input.nextLine();
	  }
	  for(String key:this.books.keySet()) {
		  Book book = this.books.get(key);
		  if (book.matchesFields(keys, values))
			  result.add(book);
	  }
	  return result;
  }
}

