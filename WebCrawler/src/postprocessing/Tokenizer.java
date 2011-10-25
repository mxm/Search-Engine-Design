package postprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;


public class Tokenizer {

	/*
	 * 
	 * Tokenizer
	 * A class that tokenizes a HTML document. It removes tags, 
	 * strips out the script and style section and removes comments.
	 * Then it removes stop words. 
	 * 
	 * 2011 - Maximilian Michels
	 * max.michels@fu-berlin.de
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
			String[] tempTokens = tokenize(str).split(" ");

			// filter stop words out of tokens
			tokens = new Vector<String>();
			for (int i = 0; i < tempTokens.length; i++) {
				if (!stopList.find(tempTokens[i]))
					tokens.add(tempTokens[i]);
			}

		}  catch(Exception e){
			e.printStackTrace();
		}

	}

	public static String tokenize(String str){
		// remove tags and punctuation
		// tokenize
		String tokens = str
				.replaceAll(
						"<style[^<]*</style>|<script[^<]*</script>|<[^>]*>|<!--[^>]>|[^a-zA-Z0-9' ]|\\s*",
						"");
		
		return tokens;
	}


}
