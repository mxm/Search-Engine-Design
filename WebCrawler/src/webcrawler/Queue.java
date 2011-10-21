package webcrawler;

import java.net.URL;
import java.util.LinkedList;
import java.util.Set;

/*
 * The Queue Class used to store URLs
 * It uses to LinkedList. One list to store links yet to be visited
 * and another one to store visited links.
 */

public class Queue {

	int depth = 0;
	LinkedList<URL> list1;
	LinkedList<URL> list2;
	// current list to work with;
	LinkedList<URL> list;
	Set visitedURLs;

	public Queue() {
		list1 = new LinkedList<URL>();
		list2 = new LinkedList<URL>();

	}

	public synchronized URL getURL() {
		if (list.isEmpty()) {
			return null;
		} else {
			return list.pop();
		}
	}

	public synchronized void insertURL(URL url) {
		list.add(url);
	}

}
