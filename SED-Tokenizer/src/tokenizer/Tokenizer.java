package tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import tokenizer.StopList;

public class Tokenizer {

	/*
	 * strtok = new StringTokenizer(str, "...");
	 * 
	 * StringTokenizer is a legacy class that is retained for compatibility 
	 * reasons although its use is discouraged in new code. It is recommended 
	 * that anyone seeking this functionality use the split method of String 
	 * or the java.util.regex package instead.
	 */

	StopList stopList;
	String str;

	public Tokenizer(File file) {
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
			
			//remove tags and punctuation
			//tokenize
			String[] tokens = str.replaceAll("<style[^<]*</style>|<script[^<]*</script>|<[^>]*>|<!--[^>]>|[^a-zA-Z0-9' ]", "").split(" ");
			
			//filter stop words out of tokens
			for(int i=0; i<tokens.length;i++){
				if(!stopList.find(tokens[i]))
					System.out.println(tokens[i]);
			}

		} catch (IOException e) {
			str = "";
		}

	}
	/*
	public String nextToken() {
		return strtok.nextToken();
	}

	public boolean hasNext() {
		return strtok.hasMoreTokens();
	}

	public String getString() {
		return str;
	}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("test.html");
		Tokenizer tok = new Tokenizer(file);		
		

	}

}
