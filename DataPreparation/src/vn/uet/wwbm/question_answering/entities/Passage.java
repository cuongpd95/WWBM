/**
 * 
 */
package vn.uet.wwbm.question_answering.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class Passage {
	
	private int id;
	
	private String title;
	
	private String passage;
	
	private double bm25Score;
	
	private double usefulProbability;

	public Passage(int id, String title, String passage) {
		super();
		this.id = id;
		this.title = title;
		this.passage = passage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	 * @param bm25Score the bm25Score to set
	 */
	public void setBm25Score(double bm25Score) {
		this.bm25Score = bm25Score;
	}
	
	/**
	 * @return the bm25Score
	 */
	public double getBm25Score() {
		return bm25Score;
	}
	
	/**
	 * @param usefulProbability the usefulProbability to set
	 */
	public void setUsefulProbability(double usefulProbability) {
		this.usefulProbability = usefulProbability;
	}
	
	/**
	 * @return the usefulProbability
	 */
	public double getUsefulProbability() {
		return usefulProbability;
	}
}
