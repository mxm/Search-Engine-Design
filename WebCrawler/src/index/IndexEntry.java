package index;

import java.util.ArrayList;
import java.util.Collections;

import webcrawler.Document;

public class IndexEntry extends ArrayList<DocumentEntry> {

	/**
	 * A Custom HashSet for Document Entries
	 */
	private static final long serialVersionUID = 1L;

	public IndexEntry() {
		super();
	}

	public DocumentEntry findEntry(DocumentEntry docEntry) {
		int pos = findEntryPos(docEntry);
		if (pos >= 0)
			return this.get(pos);
		else
			return null;
	}
	
	private int findEntryPos(DocumentEntry docEntry) {
		return Collections.binarySearch(this, docEntry);
	}

	public boolean add(Document entry) {
		DocumentEntry docEntry = new DocumentEntry(entry);
		int test = findEntryPos(docEntry);
		if (test >= 0) {
			// if we find, we count up occurances
			this.get(test).count();
			return false;
		} else {
			// if we don't find, we insert at the right point...
			this.add(-test - 1, docEntry);
			return true;
		}
	}

}
