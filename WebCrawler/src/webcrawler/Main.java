package webcrawler;

import java.net.URL;
import java.util.Set;

import javax.swing.*;

public class Main {

	/**
	 * Main class of the WebCrawler
	 * 
	 * This WebCrawler uses a command-line interface making it very easy to
	 * deploy remotely on linux servers that generally lack a graphical user
	 * interface.
	 */
	Controller con;

	public Main(Controller con) {

		this.con = con;
		JFrame frame = new JFrame("WebCrawler and simple Search Engine");
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);

	

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		Set<URL> q = con.queue.extractedURLs;
		String[] listbla = new String[q.size()];
		int i=0;
		for(URL url : q){
			System.out.println(url);
		}

		JList list = new JList(listbla);

		panel.add(list);
		frame.repaint();
		
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		Controller con = new Controller(5, 3);
		con.start();
		//new Main(con);

	}

}
