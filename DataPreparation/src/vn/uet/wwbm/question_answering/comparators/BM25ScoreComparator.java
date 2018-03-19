/**
 * 
 */
package vn.uet.wwbm.question_answering.comparators;

import java.util.Comparator;

import vn.uet.wwbm.question_answering.BM25Score;

/**
 * @author "PhanDoanCuong"
 *
 */
public class BM25ScoreComparator implements Comparator<BM25Score> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(BM25Score score0, BM25Score score1) {
		if (score0.getScore() > score1.getScore()) {
			return 1;
		} else if (score0.getScore() < score1.getScore()) {
			return -1;
		} else
			return 0;
	}

}
