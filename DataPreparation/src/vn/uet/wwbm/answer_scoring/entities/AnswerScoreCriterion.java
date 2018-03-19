/**
 * 
 */
package vn.uet.wwbm.answer_scoring.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class AnswerScoreCriterion {
	
	private int candidateCode;
	
	private double es;
	
	private double ls;
	
	private double lcs;
	
	private double ov;

	public AnswerScoreCriterion(int candidateCode, double es, double ls, double lcs, double ov) {
		super();
		this.candidateCode = candidateCode;
		this.es = es;
		this.ls = ls;
		this.lcs = lcs;
		this.ov = ov;
	}

	public double getEs() {
		return es;
	}

	public void setEs(double es) {
		this.es = es;
	}

	public double getLs() {
		return ls;
	}

	public void setLs(double ls) {
		this.ls = ls;
	}

	public double getLcs() {
		return lcs;
	}

	public void setLcs(double lcs) {
		this.lcs = lcs;
	}

	public double getOv() {
		return ov;
	}

	public void setOv(double ov) {
		this.ov = ov;
	}

	/**
	 * @param candidateCode the candidateCode to set
	 */
	public void setCandidateCode(int candidateCode) {
		this.candidateCode = candidateCode;
	}
	
	/**
	 * @return the candidateCode
	 */
	public int getCandidateCode() {
		return candidateCode;
	}
}
