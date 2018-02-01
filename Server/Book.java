import java.util.HashMap;

public class Book {
	HashMap<String,String> fields = new HashMap<String,String>();
	public Book() {
	}
	
	public String toString() {
		final String[] keys = {"ISBN", "AUTHOR", "TITLE", "YEAR", "PUBLISHER"};
		String result = "";
		
		for (String key:keys)
			if (this.fields.containsKey(key))
				result += String.format("%s %s\n", key, fields.get(key));
			
		return result;
	}
}
