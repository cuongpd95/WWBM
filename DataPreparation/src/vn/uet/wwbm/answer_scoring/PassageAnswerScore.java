/**
 * 
 */
package vn.uet.wwbm.answer_scoring;

/**
 * @author "PhanDoanCuong"
 *
 */
public class PassageAnswerScore {
	
	private double[] es;
	
	private double[] lcs;
	
	private double[] ov;
	
	private double[] ls;
	
	public PassageAnswerScore(){
		
	}

	public double[] getEs() {
		return es;
	}

	public void setEs(double[] es) {
		this.es = es;
	}

	public double[] getLcs() {
		return lcs;
	}

	public void setLcs(double[] lcs) {
		this.lcs = lcs;
	}

	public double[] getOv() {
		return ov;
	}

	public void setOv(double[] ov) {
		this.ov = ov;
	}

	public double[] getLs() {
		return ls;
	}

	public void setLs(double[] ls) {
		this.ls = ls;
	}

	public PassageAnswerScore(double[] es, double[] lcs, double[] ov, double[] ls) {
		super();
		this.es = es;
		this.lcs = lcs;
		this.ov = ov;
		this.ls = ls;
	}
	
	

}
