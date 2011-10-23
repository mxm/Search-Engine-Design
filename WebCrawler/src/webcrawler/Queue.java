package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*
 * Queue.java
 * The Queue Class is used to store URLs
 * It uses two LinkedLists. One list is used to store URLs to be crawled
 * for the current depth, the other one to store the next depth level.
 */

public class Queue {

	int depth = 0;
	// Two lists per depth: one for current URLs, one for new ones
	LinkedList<URL> curURLs;
	LinkedList<URL> newURLs;
	Set<URL> visitedURLs;
	Set<URL> extractedURLs;

	public Queue() {
		curURLs = new LinkedList<URL>();
		newURLs = new LinkedList<URL>();
		visitedURLs = new HashSet<URL>();
		extractedURLs = new HashSet<URL>();
		
		try {
			curURLs.add(new URL("http://wikipedia.de"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDepth(int depth) {
		this.depth = depth;
		/*
		 * switch the lists. this can only be done when all
		 * threads are waiting through the controller
		 * The new URL list will become the current list
		 * because we advanced to the next depth
		 */
		LinkedList<URL> temp = curURLs;
		curURLs = newURLs;
		newURLs = temp;

	}

	public synchronized URL getURL() {
		if (curURLs.isEmpty()) {
			return null;
		} else {
			URL newURL = curURLs.pop();
			visitedURLs.add(newURL);
			return newURL;
		}
	}

	public synchronized void insertURL(URL url) {
		newURLs.add(url);
		extractedURLs.add(url);
	}
	
	public synchronized void insertURLCollection(Collection<URL> col){
		for(URL url : col){
			if(!extractedURLs.contains(url)){
				insertURL(url);				
			}
		}
	}

}
