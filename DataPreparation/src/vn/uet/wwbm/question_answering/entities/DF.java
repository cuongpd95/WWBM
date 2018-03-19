/**
 * 
 */
package vn.uet.wwbm.question_answering.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class DF {
	
	private int docId;
	
	private String term;
	
	private int count;

	public DF(int docId, String term, int count) {
		super();
		this.docId = docId;
		this.term = term;
		this.count = count;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
