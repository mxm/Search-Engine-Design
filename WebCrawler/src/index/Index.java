package index;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import webcrawler.Document;

public class Index {

	/*
	 * The Index data structure for storing the key words and matching documents
	 * typically called reversed index we use a hash table to store the index
	 * terms and lists for storing the documents
	 */

	private TreeMap<String, IndexEntry> index;

	public Index() {
		index = new TreeMap<String, IndexEntry>();
	}

	public IndexEntry getDocuments(String term) {
		return index.get(term);
	}

	public void insertKeywords(String[] keywords, Document doc) {
		for (String word : keywords) {
			insertTerm(word, doc);
		}
	}

	public void insertTerm(String keyword, Document doc) {
		IndexEntry docs = index.get(keyword);
		if (docs == null) {
			docs = new IndexEntry();
			docs.add(new DocumentEntry(doc));
			index.put(keyword, docs);
		} else {
			docs.add(doc);
		}
	}

	public String toString() {
		String out = "";
		for (Map.Entry<String, IndexEntry> e : index.entrySet()) {
			String key = (String) e.getKey();
			IndexEntry docs = (IndexEntry) e.getValue();
			out += key + " => ";
			for (DocumentEntry doc : docs) {
				out += doc.toString() + ", ";
			}
			out += "\n";
		}
		return out;
	}

	public static void main(String[] args) throws MalformedURLException {
		Index index = new Index();
		Document doc = new Document(new URL("http://heise.de"));
		index.insertTerm("test", doc);
		doc = new Document(new URL("http://wikipedia.de"));
		index.insertTerm("test", doc);
		doc = new Document(new URL("http://wikipedia.de"));
		index.insertTerm("fest", doc);
		doc = new Document(new URL("http://heise.de"));
		index.insertTerm("fest", doc);
		index.insertTerm("fest", doc);
		index.insertTerm("fest", doc);

		System.out.println(index);

	}
}
