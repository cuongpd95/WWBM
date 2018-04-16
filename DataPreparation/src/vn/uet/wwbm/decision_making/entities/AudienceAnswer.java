package vn.uet.wwbm.decision_making.entities;

public class AudienceAnswer {
	private int answerCode;
	
	private double confidence;
	
	public AudienceAnswer(int answerCode, double confidence) {
		this.answerCode = answerCode;
		this.confidence = confidence;
	}
	
	public int getAnswerCode() {
		return answerCode;
	}
	
	public double getConfidence() {
		return confidence;
	}
}
