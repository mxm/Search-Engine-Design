package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;

import postprocessing.NFragment;
import postprocessing.NGram;

public class Main {

	/**
	 * Main class of the WebCrawler
	 * 
	 * This Web Crawler has no GUI yet, but will soon have one... So for now we
	 * have to use the main method to test it
	 * 
	 * 
	 * 2011 - Maximilian Michels
	 * max.michels@fu-berlin.de
	 * 
	 */

	public static void main(String[] args) throws InterruptedException,
			MalformedURLException {

		// Start the web crawler's with 5 threads and a depth of 3
		Controller con = new Controller(5, 3);

		// Start at some websites
		con.queue.curURLs.add(new URL("http://en.wikipedia.org"));
		con.queue.curURLs.add(new URL("http://ce.istanbul.edu.tr"));
		// start the crawling
		con.start();

		// wait 10 seconds for the crawler to get some pages
		Thread.sleep(10000);
		// stop the crawler
		con.stopCrawling();

		// get the documents
		Document[] docs = con.getDocuments();

		// pick a search string
		String searchString = "wikimedia best wiki technology";
		// Generate ngram of the search string
		NGram ngramSearchString = new NGram(3, searchString);
		// Pick most common fragment of ngram
		NFragment fragment = ngramSearchString.getMostImportantFragment();

		// Search through document ngrams and find the given fragment
		System.out.println("------Results-------");
		System.out.println("most common ngram fragment: " + fragment);
		for (Document doc : docs) {
			NFragment foundFrag;
			if ((foundFrag = doc.ngram.find(fragment)) != null) {
				System.out.println("////////");
				System.out.println("We found ngram in " + doc.url);
				System.out.println("Content (only 500 chars): " + doc.content.substring(0, 500));
				System.out.println("NGrams (only most popular): " + doc.ngram.toString().substring(0, 500));
			}
		}
		System.out.println("------End-----------");

	}

}
