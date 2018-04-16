package vn.uet.wwbm.decision_making.entities;

import java.util.Comparator;

import vn.uet.wwbm.answer_scoring.entities.AnswerScoreCriterion;

public class ConfidenceComparator implements Comparator<AnswerScoreCriterion> {

	@Override
	public int compare(AnswerScoreCriterion o1, AnswerScoreCriterion o2) {
		if (o1.getConfidence() > o2.getConfidence()) {
			return 1;
		} else if (o1.getConfidence() < o2.getConfidence()) {
			return -1;
		} else {
			return 0;
		}
	}

}
