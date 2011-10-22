package webcrawler;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread {

	Queue q;
	Controller con;
	int id;
	CyclicBarrier barrier;

	public Worker(Controller con, CyclicBarrier barrier, Queue q, int id) {
		this.con = con;
		this.q = q;
		this.id = id;
		this.barrier = barrier;
	}

	public void run() {

		while (true) {

			System.out.println("here is " + id);

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
