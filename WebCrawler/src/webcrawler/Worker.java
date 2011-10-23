package webcrawler;

import java.io.*;
import java.net.*;
import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread {

	/*
	 * Worker.java The Workers crawl websites
	 */

	Queue queue;
	Controller con;
	int id;
	CyclicBarrier barrier;

	public Worker(Controller con, CyclicBarrier barrier, Queue queue, int id) {
		this.con = con;
		this.queue = queue;
		this.id = id;
		this.barrier = barrier;
	}

	public void run() {

		while (true) {

			URL url;
			URLConnection conn;			

			while ((url = queue.getURL()) != null) {
				Document doc = new Document(url);
				//continue if download fails
				if(!doc.download()){
					continue;
				}
				Collection<URL> col = doc.extractLinks();
				//System.out.println(col);
				queue.insertURLCollection(col);
				System.out.println("Thread "+id+": "+queue.newURLs.size()+" URLs queued!");
			}

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
