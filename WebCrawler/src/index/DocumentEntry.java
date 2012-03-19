package index;

import webcrawler.Document;

public class DocumentEntry implements Comparable<DocumentEntry> {

	private Document doc;
	private int occurances;

	public DocumentEntry(Document doc) {
		this.doc = doc;
		occurances = 1;
	}

	public void count() {
		occurances++;
	}

	public Document getDoc() {
		return doc;
	}

	public String toString() {
		return "[" + doc.toString() + ", " + occurances + "]";
	}

	@Override
	public int compareTo(DocumentEntry o) {
		return doc.compareTo(o.doc);
	}

	
	
}
