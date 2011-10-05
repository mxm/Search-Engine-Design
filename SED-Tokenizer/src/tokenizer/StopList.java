package tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StopList {

	String[] list;

	public StopList() throws IOException {
		this(new File("stop_list.txt"));
	}

	public StopList(File file) throws IOException {
		FileInputStream ins;
		InputStreamReader isr;
		BufferedReader br;

		try {

			ins = new FileInputStream(file);
			isr = new InputStreamReader(ins);
			br = new BufferedReader(isr);

			// first line is the number of entries in stop word list
			int len = Integer.parseInt(br.readLine());
			list = new String[len];

			int c = 0;
			while (c < len) {
				list[c++] = br.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean find(String word) {

		int a = 0, b = list.length - 1;
		while (a <= b) {
			//System.out.println("a: "+a+", b:"+b);
			int elem = (b + a) / 2;
			if (word.compareTo(list[elem]) < 0) {
				b = elem - 1;
			} else if (word.compareTo(list[elem]) > 0) {
				a = elem + 1;
			} else {
				return true;
			}
		}
		return false;

	}

}
