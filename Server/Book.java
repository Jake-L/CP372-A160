import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Book {
	HashMap<String,String> fields = new HashMap<String,String>();
	final String[] keys = {"ISBN", "AUTHOR", "TITLE", "YEAR", "PUBLISHER"};
	public Book() {
	}
	
	public String toString() {
		String result = "";
		
		for (String key:keys)
			if (this.fields.containsKey(key))
				result += String.format("%s %s\n", key, fields.get(key));
			
		return result;
	}
	
	public boolean matchesFields(ArrayList<String> keys, ArrayList<String> values) {
		if (keys.size() != values.size()) throw new IllegalArgumentException("Invalid input");
		
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = values.get(i);
			if (!Arrays.asList(this.keys).contains(key)) return false;
			if (!this.fields.containsKey(key)) return false;
			if (!this.fields.get(key).equals(value)) return false;
		}
		return true;
	}
}
