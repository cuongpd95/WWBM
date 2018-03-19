/**
 * 
 */
package vn.uet.wwbm.main.entities;

/**
 * @author "PhanDoanCuong"
 *
 */
public class Question {
	
	private String question;
	
	private String candidateA;
	
	private String candidateB;
	
	private String candidateC;
	
	private String candidateD;
	
	private int rightAnswer;
	
	private int choiceAnswer;

	public Question(String question, String candidateA, String candidateB,
			String candidateC, String candidateD, int rightAnswer) {
		super();
		this.question = question;
		this.candidateA = candidateA;
		this.candidateB = candidateB;
		this.candidateC = candidateC;
		this.candidateD = candidateD;
		this.rightAnswer = rightAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCandidateA() {
		return candidateA;
	}

	public void setCandidateA(String candidateA) {
		this.candidateA = candidateA;
	}

	public String getCandidateB() {
		return candidateB;
	}

	public void setCandidateB(String candidateB) {
		this.candidateB = candidateB;
	}

	public String getCandidateC() {
		return candidateC;
	}

	public void setCandidateC(String candidateC) {
		this.candidateC = candidateC;
	}

	public String getCandidateD() {
		return candidateD;
	}

	public void setCandidateD(String candidateD) {
		this.candidateD = candidateD;
	}

	public int getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public int getChoiceAnswer() {
		return choiceAnswer;
	}

	public void setChoiceAnswer(int choiceAnswer) {
		this.choiceAnswer = choiceAnswer;
	}
	
	
	

}
