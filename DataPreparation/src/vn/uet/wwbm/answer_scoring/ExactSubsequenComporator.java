/**
 * 
 */
package vn.uet.wwbm.answer_scoring;

import java.util.Comparator;

import vn.uet.wwbm.answer_scoring.entities.AnswerScoreCriterion;

/**
 * @author "PhanDoanCuong"
 *
 */
public class ExactSubsequenComporator implements Comparator<AnswerScoreCriterion>{

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(AnswerScoreCriterion o1, AnswerScoreCriterion o2) {
		if (o1.getEs() > o2.getEs()) {
			return 1;
		}
		else if (o1.getEs() < o2.getEs()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
