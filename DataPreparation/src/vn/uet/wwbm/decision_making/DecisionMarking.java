package vn.uet.wwbm.decision_making;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import vn.uet.wwbm.answer_scoring.AnswerScoring;
import vn.uet.wwbm.answer_scoring.entities.AnswerScoreCriterion;
import vn.uet.wwbm.decision_making.entities.AudienceAnswer;
import vn.uet.wwbm.decision_making.entities.ConfidenceComparator;
import vn.uet.wwbm.main.entities.Question;
import vn.uet.wwbm.question_answering.QuestionAnswering;

public class DecisionMarking {
	private static final int RETIRE = -1;
	private KnowledgeHelper helper;
	private List<Question> questions;

	public DecisionMarking(List<Question> questions) {
		helper = new KnowledgeHelper(questions);
		this.questions = questions;
	}

	public int playGame(QuestionAnswering qa, int numberOfPassages, float ratioThreshold, float pollAudienceThreshold) throws Exception {
		int questionGain = 15;
		AnswerScoring answerScoring = new AnswerScoring(qa);
		Question question;
		List<AnswerScoreCriterion> answerScore;
		int choice;
		for (int i = 0; i < questions.size(); i++) {
			question = questions.get(i);
			answerScore = answerScoring.scoringEdit(question.getQuestion(), question.getCandidateA(),
					question.getCandidateB(), question.getCandidateC(), question.getCandidateD(), numberOfPassages);
			answerScore = updateConfidence(answerScore);
			answerScore.sort(new ConfidenceComparator());
			Collections.reverse(answerScore);
			choice = makeDecision(answerScore, i, ratioThreshold, pollAudienceThreshold);
			if (choice != RETIRE) {
				if (choice != questions.get(i).getRightAnswer()) {
					if (i >= 0 && i < 5) {
						questionGain = 0;
						break;
					} else if (i >= 5 && i < 10) {
						questionGain = 5;
						break;
					} else {
						questionGain = 10;
						break;
					}
				}
			} else {
				questionGain = i + 1;
				break;
			}

		}
		return questionGain;
	}

	private int makeDecision(List<AnswerScoreCriterion> answerScore, int indexOfQuestion, float ratioThreshold, float pollAudienceThreshold) {
		AnswerScoreCriterion bestAnswer = answerScore.get(0);
		AnswerScoreCriterion secondBestAnswer = answerScore.get(1);
//		System.out.println("The confidence: " + bestAnswer.getConfidence() + " - " + secondBestAnswer.getConfidence());
		if (bestAnswer.getConfidence() == 0d || bestAnswer.getConfidence()
				- secondBestAnswer.getConfidence() < bestAnswer.getConfidence() * ratioThreshold) {
			if (helper.canUsePollTheAudience()) {
				System.out.println((indexOfQuestion + 1) + ",Use poll the audience");
				AudienceAnswer audienceAnswer = helper.usePollTheAudience(indexOfQuestion);
				if (audienceAnswer.getConfidence() > pollAudienceThreshold) {
					return audienceAnswer.getAnswerCode();
				}
			}
			if (helper.canUseCallAFriend()) {
				System.out.println((indexOfQuestion + 1) + ", Use call a friend " );
				return helper.callAFriend(indexOfQuestion);
			}
			if (helper.canUse5050()) {
				System.out.println((indexOfQuestion + 1) + ",Use 50:50 " );
				int[] wrongAnswer = helper.use5050(indexOfQuestion);
				List<AnswerScoreCriterion> anss = new ArrayList<AnswerScoreCriterion>();
				for (int i = 0; i < answerScore.size(); i++) {
					if (answerScore.get(i).getCandidateCode() != wrongAnswer[0]
							&& answerScore.get(i).getCandidateCode() != wrongAnswer[1]) {
						anss.add(answerScore.get(i));
					}
				}
				anss.sort(new ConfidenceComparator());
				Collections.reverse(anss);
				if (anss.get(0).getConfidence() - anss.get(1).getConfidence() > anss.get(0).getConfidence()
						* ratioThreshold) {
					return anss.get(0).getCandidateCode();
				}
			}

			// Safe question
			if (indexOfQuestion == 5 || indexOfQuestion == 10) {
				return new Random().nextInt(4) + 1;
			} else {
				return RETIRE;
			}
		} else {
			return bestAnswer.getCandidateCode();
		}
	}

	private List<AnswerScoreCriterion> updateConfidence(List<AnswerScoreCriterion> answerScore) {
		double sum = 0;
		for (int i = 0; i < answerScore.size(); i++) {
			sum += answerScore.get(i).getEs() + answerScore.get(i).getOv();
		}

		if (sum != 0) {
			for (int i = 0; i < answerScore.size(); i++) {
				answerScore.get(i).setConfidence((answerScore.get(i).getEs() + answerScore.get(i).getOv()) / sum);
			}
		} else {
			for (int i = 0; i < answerScore.size(); i++) {
				answerScore.get(i).setConfidence(0);
			}
		}

		return answerScore;
	}
}
