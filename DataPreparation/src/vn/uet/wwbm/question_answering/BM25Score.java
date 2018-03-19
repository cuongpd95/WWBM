/**
 * 
 */
package vn.uet.wwbm.question_answering;

/**
 * @author "PhanDoanCuong"
 *
 */
public class BM25Score {

	public int docId;

	public double score;

	public BM25Score(int docId, double score) {
		super();
		this.docId = docId;
		this.score = score;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + docId + ";" + score + "]";
	}
}
