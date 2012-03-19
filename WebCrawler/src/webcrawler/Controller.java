package webcrawler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CyclicBarrier;

public class Controller extends Thread {

	/*
	 * Controller.java The controller takes control of the crawling threads
	 * called Workers It will start a numThreads threads let them work on a
	 * queue. When the Workers finish they will wait for the Controller to
	 * increase the depth of the crawling process and provide a new queue, that
	 * is switching the lists in the Queue object.
	 * 
	 * 2011 - Maximilian Michels max.michels@fu-berlin.de
	 */

	int numThreads;
	int depth = 0;
	int maxDepth;
	volatile boolean stop;
	Queue queue;

	Worker[] workers;
	CyclicBarrier barrier;

	// Code being executed once all threads reach the barrier
	// Basically we switch the two queues and advance to next depth
	Runnable syncCode = new Runnable() {
		public void run() {
			if (++depth > maxDepth) {
				stopCrawling();
				saveDocuments();
			} else {
				queue.setDepth(depth);
				System.out.println("depth: " + depth);
				try {
					sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	public Controller(int numThreads, final int maxDepth) {
		this.numThreads = numThreads;
		this.maxDepth = maxDepth;
		workers = new Worker[numThreads];
		queue = new Queue();
		barrier = new CyclicBarrier(numThreads, syncCode);
		// initiate Workers
		for (int i = 0; i < numThreads; i++)
			workers[i] = new Worker(this, barrier, queue, i);
	}

	public void run() {

		for (Worker worker : workers) {
			worker.start();
		}

	}

	public void stopCrawling() {
		stop = true;
		for (Worker worker : workers) {
			try {
				worker.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Crawler stopped!");
	}

	public Document[] getDocuments() {
		int numDocuments = 0;
		for (Worker worker : workers) {
			numDocuments += worker.docs.size();
		}

		int k = 0;
		Document[] docs = new Document[numDocuments];
		for (Worker worker : workers) {
			for (int i = 0; i < worker.docs.size(); k++, i++) {
				docs[k] = worker.docs.get(i);
			}
		}

		return docs;
	}

	public boolean saveDocuments() {

		Document[] docs = getDocuments();

		FileOutputStream fout = null;
		ObjectOutputStream objout = null;

		try {
			fout = new FileOutputStream("documents.bin");
			objout = new ObjectOutputStream(fout);
			objout.writeObject(docs);
			return true;

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}

	}

	public boolean loadDocuments() {
		//TODO implementation not complete!
		// Read from disk using FileInputStream
		FileInputStream fin;
		try {
			fin = new FileInputStream("documents.bin");

			ObjectInputStream objin = new ObjectInputStream(fin);

			Object obj = objin.readObject();
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
