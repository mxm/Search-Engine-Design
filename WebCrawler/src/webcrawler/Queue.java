package webcrawler;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*
 * The Queue Class used to store URLs
 * It uses to LinkedList. One list to store links yet to be visited
 * and another one to store visited links.
 */

public class Queue {

	int depth = 0;
	// Two lists per depth: one for current URLs, one for new ones
	LinkedList<URL> curURLs;
	LinkedList<URL> newURLs;
	Set<URL> visitedURLs;

	public Queue() {
		curURLs = new LinkedList<URL>();
		newURLs = new LinkedList<URL>();
		visitedURLs = new HashSet<URL>();
	}

	public void setDepth(int depth) {
		this.depth = depth;
		/*
		 * switch the lists. The new URL list will become the current list
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
	}

}
