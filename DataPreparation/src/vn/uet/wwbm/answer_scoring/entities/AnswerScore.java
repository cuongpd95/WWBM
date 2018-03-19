/**
 * 
 */
package vn.uet.wwbm.answer_scoring.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class AnswerScore {

	private int candidateCode;

	private double score;

	public AnswerScore(int candidateCode, double score) {
		super();
		this.candidateCode = candidateCode;
		this.score = score;
	}

	public int getCandidateCode() {
		return candidateCode;
	}

	public void setCandidateCode(int candidateCode) {
		this.candidateCode = candidateCode;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
