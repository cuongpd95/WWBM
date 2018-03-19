/**
 * 
 */
package vn.uet.wwbm.question_answering.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class TFIDFNER {
	
	private int id;
	
	private String term;
	
	private int term_count;

	public TFIDFNER(int id, String term, int term_count) {
		super();
		this.id = id;
		this.term = term;
		this.term_count = term_count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getTerm_count() {
		return term_count;
	}

	public void setTerm_count(int term_count) {
		this.term_count = term_count;
	}
	
	

}
