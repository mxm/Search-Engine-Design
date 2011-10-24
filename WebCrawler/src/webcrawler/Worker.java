package webcrawler;

import java.net.*;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread {

	/*
	 * Worker.java 
	 * The workers crawl websites, tokenize them and generate Ngrams
	 * 
	 * 2011 - Maximilian Michels
	 * max.michels@fu-berlin.de
	 * 
	 */

	Queue queue;
	Controller con;
	int id;
	CyclicBarrier barrier;
	
	Vector<Document> docs;

	public Worker(Controller con, CyclicBarrier barrier, Queue queue, int id) {
		this.con = con;
		this.queue = queue;
		this.id = id;
		this.barrier = barrier;
		docs = new Vector<Document>();
	}

	public void run() {

		while (true) {

			URL url;
			
			while ((url = queue.getURL()) != null) {
				
				if (con.stop)
					break;
				
				Document doc = new Document(url);
				//continue if download fails
				if(!doc.download()){
					continue;
				}
				Collection<URL> col = doc.extractLinks();
				queue.insertURLCollection(col);
				System.out.println("Thread "+id+": "+queue.newURLs.size()+" URLs queued!");
				
				doc.tokenize();
				doc.generateNGram(3);
				
				//we will store them in memory for now...
				//doc.save();
				docs.add(doc);
			}

			/* We have to wait for the other works to advance to
			 * next depth
			 */
			try {
				barrier.await();
				if (con.stop)
					break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
	}

}
