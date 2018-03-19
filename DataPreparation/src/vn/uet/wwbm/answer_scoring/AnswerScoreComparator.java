/**
 * 
 */
package vn.uet.wwbm.answer_scoring;

import java.util.Comparator;

import vn.uet.wwbm.answer_scoring.entities.AnswerScore;

/**
 * @author "PhanDoanCuong"
 *
 */
public class AnswerScoreComparator implements Comparator<AnswerScore> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(AnswerScore as1, AnswerScore as2) {
		if (as1.getScore() > as2.getScore()) {
			return 1;
		} else if (as1.getScore() < as2.getScore()) {
			return -1;
		} else
			return 0;
	}

}
