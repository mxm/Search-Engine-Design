package postprocessing;

public class NFragment implements Comparable<NFragment> {

	/*
	 * NFragment.java Data structure for the fragments that a ngram consists of
	 * 
	 * 2011 - Maximilian Michels
	 * max.michels@fu-berlin.de
	 */

	String fragment;
	int occurance;

	public NFragment(String fragment) {
		this.fragment = fragment;
		this.occurance = 1;
	}

	public void countOccurance() {
		occurance++;
	}

	public String getFragment() {
		return fragment;
	}

	// we overwrite this to compare to nfragments
	@Override
	public boolean equals(Object other) {
		NFragment o = (NFragment) other;
		return fragment.equals(o.fragment);
	}

	@Override
	public int compareTo(NFragment other) {
		return fragment.compareTo(other.fragment);
	}

	/* Orders first by number of occurances then by lexigraphical order */
	public int TwoCompareTo(NFragment other) {
		
		int occuranceCompare = new Integer(occurance)
				.compareTo(other.occurance);

		if (occuranceCompare == 0)
			return fragment.compareTo(other.fragment);
		else
			return occuranceCompare;

	}

	@Override
	public String toString() {
		return fragment + " = " + occurance;
	}

}
