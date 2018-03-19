/**
 * 
 */
package vn.uet.wwbm.question_answering.comparators;

import java.util.Comparator;

import vn.uet.wwbm.question_answering.entities.Passage;

/**
 * @author "PhanDoanCuong"
 *
 */
public class ProbabilityPassageComporator implements Comparator<Passage> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Passage p0, Passage p1) {
		if (p0.getUsefulProbability() > p1.getUsefulProbability()) {
			return 1;
		} else if (p0.getUsefulProbability() < p1.getUsefulProbability()) {
			return -1;
		} else {
			return 0;
		}
	}

}
