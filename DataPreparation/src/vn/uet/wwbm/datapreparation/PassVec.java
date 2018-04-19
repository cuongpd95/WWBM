package vn.uet.wwbm.datapreparation;

public class PassVec {
	private int id;
	private String term;
	private int term_id;
	private int docid;
	
	public PassVec(int id, String term) {
		this.id = id;
		this.term = term;
		term_id = -1;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public void setTerm_id(int term_id) {
		this.term_id = term_id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTerm_id() {
		return term_id;
	}
	
	public String getTerm() {
		return term;
	}
	
	public int getDocid() {
		return docid;
	}
}
