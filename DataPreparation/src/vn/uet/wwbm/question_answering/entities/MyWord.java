/**
 * 
 */
package vn.uet.wwbm.question_answering.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class MyWord {
	private String term;
	
	private int count;

	public MyWord(String term, int count) {
		super();
		this.term = term;
		this.count = count;
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
	
	public void increaseCount(){
		count ++;
	}
}
