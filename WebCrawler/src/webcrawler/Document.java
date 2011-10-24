package webcrawler;

import java.io.*;
import java.net.*;
import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.*;

import postprocessing.NGram;
import postprocessing.Tokenizer;

/*
 * This class provides methods for getting the content
 * of a url or tokenizing a document
 */

public class Document implements Serializable {

	private static final long serialVersionUID = -3092849712355372568L;

	URL url;
	String host;
	int len;
	String type;
	String encoding;
	Date fetched;
	long lastModified;
	String content;

	Pattern urlPattern = Pattern
			.compile(
					"<a\\s*href=['\"]([\\w\\/.:\\-\\d]+)[#]?[\\w\\/.:\\-\\d]*['\"]\\s*>",
					Pattern.CASE_INSENSITIVE);

	NGram ngram;

	public Document(URL url) {
		this.url = url;
	}

	public boolean download() {
		URLConnection conn;
		try {
			conn = url.openConnection();
			int estimatedLength = conn.getContentLength();
			if (estimatedLength < 0) {
				estimatedLength = 20000;
			}
			type = conn.getContentType();
			encoding = conn.getContentEncoding();
			fetched = new Date();
			lastModified = conn.getLastModified();
			host = url.getHost();

			System.out.println("Downloading: " + url);

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			StringBuilder variableArray = new StringBuilder(estimatedLength);

			int totalBytesFetched = 0;
			int tempBytesFetched = 0;

			char[] temp = new char[512];

			while ((tempBytesFetched = br.read(temp)) != -1) {
				totalBytesFetched += tempBytesFetched;
				variableArray.append(temp, 0, tempBytesFetched);
			}

			len = totalBytesFetched;

			content = variableArray.toString();// .toLowerCase();

			br.close();
			isr.close();
			is.close();
			return true;

		} catch (FileNotFoundException e) {
			System.out.println("HTTP 404");
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Collection<URL> extractLinks() {

		Matcher m = urlPattern.matcher(content);
		LinkedList<URL> itemsForQueue = new LinkedList<URL>();

		while (m.find()) {
			try {
				if (m.group(1).startsWith("//")) {
					itemsForQueue.add(new URL("http:" + m.group(1)));
				} else if (!m.group(1).startsWith("http")) {
					if (m.group(1).startsWith("/")) {
						itemsForQueue
								.add(new URL("http://" + host + m.group(1)));
					} else {
						itemsForQueue.add(new URL("http://" + host + "/"
								+ m.group(1)));
					}
				} else {
					itemsForQueue.add(new URL(m.group(1)));
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return itemsForQueue;
	}

	public void tokenize() {
		content = Tokenizer.tokenize(content);
	}

	public void generateNGram(int n) {
		ngram = new NGram(n, content);
	}

	public boolean save() {
		FileOutputStream fout = null;
		ObjectOutputStream objout = null;

		try {
			fout = new FileOutputStream(url.toString());
			objout = new ObjectOutputStream(fout);
			objout.writeObject(this);
			return true;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

	}

}
