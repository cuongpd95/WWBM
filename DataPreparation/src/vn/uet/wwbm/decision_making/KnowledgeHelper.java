package vn.uet.wwbm.decision_making;

import java.util.List;
import java.util.Random;

import vn.uet.wwbm.decision_making.entities.AudienceAnswer;
import vn.uet.wwbm.main.entities.Question;

public class KnowledgeHelper {
	private boolean pollTheAudience;

	private boolean callAFriend;

	private boolean c5050;

	private List<Question> questions;

	private Random random;

	public KnowledgeHelper(List<Question> questions) {
		this.questions = questions;
		pollTheAudience = true;
		callAFriend = true;
		c5050 = true;
		random = new Random();
	}

	public boolean canUsePollTheAudience() {
		return pollTheAudience;
	}

	public boolean canUseCallAFriend() {
		return callAFriend;
	}

	public boolean canUse5050() {
		return c5050;
	}

	/**
	 * Return confidence of candidate answers.
	 * The formula is y = -15x/7 + 435/7
	 * 
	 * @param qIndex
	 * @return
	 */
	public AudienceAnswer usePollTheAudience(int qIndex) {
		pollTheAudience = false;
		int rightAnswer = questions.get(qIndex).getRightAnswer();
		return new AudienceAnswer(rightAnswer, 
				((double) random.nextInt(10) + ((double)((qIndex + 1) * (-15)) + 435)/7) / 100);
		// double[] theConfidence = new double[4];
		// int rightAnswer = questions.get(qIndex).getRightAnswer() - 1;
		//
		// int bound = 100 - 25;
		// int value = random.nextInt(bound) + 1;
		// theConfidence[rightAnswer] = ((double)value)/100;
		// bound = bound - value;
		// for (int i = 0; i < 4; i++) {
		// if (i != rightAnswer) {
		// value = random.nextInt(bound) + 1;
		// theConfidence[i] = ((double)value)/100;
		// bound -= value;
		// }
		// }
		// return theConfidence;
	}

	/**
	 * 
	 * @param qIndex
	 * @return right answer
	 */
	public int callAFriend(int qIndex) {
		callAFriend = false;
		return questions.get(qIndex).getRightAnswer();
	}

	/**
	 * return 2 wrong answers
	 * 
	 * @param qIndex
	 * @return
	 */
	public int[] use5050(int qIndex) {
		c5050 = false;
		int[] result = new int[2];
		int rightAnswer = questions.get(qIndex).getRightAnswer();
		int count = 0;
		int ans;
		while (count < 2) {
			ans = random.nextInt(4) + 1;
			if (ans != rightAnswer) {
				if (count == 0) {
					result[0] = ans;
					count++;
				} else {
					if (result[0] != ans) {
						result[1] = ans;
						count++;
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Random random = new Random();
		random.nextInt(4);
		System.out.println(random.nextInt(4));
	}

}
