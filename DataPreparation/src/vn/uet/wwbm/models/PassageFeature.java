/**
 * 
 */
package vn.uet.wwbm.models;

/**
 * @author "PhanDoanCuong"
 *
 */
public class PassageFeature {
	private double bm25score;
	
	private double es;
	
	private double ov;
	
	private int lcs;
	
	private String title;
	
	private String passage;

	public PassageFeature(double bm25score, double es, double ov, int lcs, String title,
			String passage) {
		super();
		this.bm25score = bm25score;
		this.es = es;
		this.ov = ov;
		this.lcs = lcs;
		this.title = title;
		this.passage = passage;
	}

	public double getEs() {
		return es;
	}

	public void setEs(double es) {
		this.es = es;
	}

	public double getOv() {
		return ov;
	}

	public void setOv(double ov) {
		this.ov = ov;
	}

	public int getLcs() {
		return lcs;
	}

	public void setLcs(int lcs) {
		this.lcs = lcs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPassage() {
		return passage;
	}

	public void setPassage(String passage) {
		this.passage = passage;
	}
	
	/**
	 * @return the bm25score
	 */
	public double getBm25score() {
		return bm25score;
	}
	
	/**
	 * @param bm25score the bm25score to set
	 */
	public void setBm25score(double bm25score) {
		this.bm25score = bm25score;
	}

}
