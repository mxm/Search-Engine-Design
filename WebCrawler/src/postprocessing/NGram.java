package postprocessing;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class NGram {

	/*
	 * NGram.java Creates an ngram from a given string.
	 * 
	 * 2011 - Maximilian Michels
	 * max.michels@fu-berlin.de
	 */

	int n;
	Vector<NFragment> ngram;

	public NGram(int n, String str) {

		ngram = new Vector<NFragment>();

		/*
		 * Create the ngram fragments Insert into vector without adding the same
		 * nfragment twice
		 */
		for (int i = 0; i < str.length(); i++) {
			if (i + n > str.length())
				break;
			NFragment nfrag = new NFragment(str.substring(i, i + n));
			insert(nfrag);
		}

		ngram.trimToSize();

	}

	/*
	 * Insert and sort the fragments by lexigraphical order This is necessary so
	 * we can find duplicates fast
	 */
	public void insert(NFragment word) {
		int a = 0, b = ngram.size() - 1;

		if (b < 0) {
			ngram.add(word);
			return;
		}

		char status = 'n';
		int elem = 0;
		while (a <= b) {
			elem = (b + a) / 2;
			if (word.compareTo(ngram.get(elem)) < 0) {
				b = elem - 1;
				status = 'l';
			} else if (word.compareTo(ngram.get(elem)) > 0) {
				a = elem + 1;
				status = 'r';
			} else {
				NFragment temp = ngram.remove(elem);
				temp.countOccurance();
				insert(temp);
				return;
			}
		}

		if (status == 'l') {
			ngram.add(elem, word);
		} else {
			ngram.add(elem + 1, word);
		}

	}

	/* Find an fragment in an ngram */
	public NFragment find(NFragment word) {

		int a = 0, b = ngram.size() - 1;

		if (b < 0)
			return null;

		int elem = 0;
		while (a <= b) {
			elem = (b + a) / 2;
			if (word.compareTo(ngram.get(elem)) < 0) {
				b = elem - 1;
			} else if (word.compareTo(ngram.get(elem)) > 0) {
				a = elem + 1;
			} else {
				return ngram.get(elem);
			}
		}

		return null;

	}

	public NFragment getMostImportantFragment() {
		return ngram.lastElement();
	}

	public String toString() {
		/* It is sorted lexigraphically but now we sort it by occurance too */
		Vector<NFragment> copy = (Vector<NFragment>) ngram.clone();
		/*for (int i = ngram.size() - 1; i > 0; i--) {
			copy.add(ngram.get(i));
		}*/
		Collections.sort(copy, new Comparator<NFragment>() {
			@Override
			public int compare(NFragment fragment, NFragment other) {
				return -fragment.TwoCompareTo(other);
			}
		});

		return copy.toString();
	}

}
