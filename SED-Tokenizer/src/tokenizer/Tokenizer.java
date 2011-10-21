package tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import tokenizer.StopList;

public class Tokenizer {

	/*
	 * 
	 * Tokenizer
	 * A class that tokenizes a HTML document. It removes tags, 
	 * strips out the script and style section and removes comments.
	 * Then it removes stop words. 
	 * Elements can be retrieved via the function getNext()
	 */
	
	 /* 
	  * StringTokenizer shouldn't be used anymore:
	 *
	 * StringTokenizer is a legacy class that is retained for compatibility
	 * reasons although its use is discouraged in new code. It is recommended
	 * that anyone seeking this functionality use the split method of String or
	 * the java.util.regex package instead.
	 */

	StopList stopList;
	Vector<String> tokens;
	Iterator<String> iter;
	
	public Tokenizer(File file) {
		String str;
		try {
			FileInputStream in = new FileInputStream(file);
			int len = (int) file.length();
			byte[] arr = new byte[len];

			int t, count = 0;
			while ((t = in.read()) != -1) {
				arr[count++] = (byte) t;
			}

			str = new String(arr, "utf-8");

			stopList = new StopList();

			// remove tags and punctuation
			// tokenize
			String[] tempTokens = str
					.replaceAll(
							"<style[^<]*</style>|<script[^<]*</script>|<[^>]*>|<!--[^>]>|[^a-zA-Z0-9' ]",
							"").split(" ");

			// filter stop words out of tokens
			tokens = new Vector<String>();
			for (int i = 0; i < tempTokens.length; i++) {
				if (!stopList.find(tempTokens[i]))
					tokens.add(tempTokens[i]);
			}
			
			iter = tokens.iterator();


		}  catch(Exception e){
			e.printStackTrace();
		}

	}

	public String nextToken() {
		try {
			return iter.next();
		}catch(NoSuchElementException e){
			return null;
		}
	}

	public boolean hasNext() {
		return iter.hasNext();
	}


	public static void main(String[] args) {
		File file = new File("test.html");
		Tokenizer tok = new Tokenizer(file);
		// testing
		while(tok.hasNext()){
			System.out.println(tok.nextToken());
		}
		
	}

}
