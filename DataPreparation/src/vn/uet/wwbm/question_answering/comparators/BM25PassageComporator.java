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
public class BM25PassageComporator implements Comparator<Passage>{

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Passage o1, Passage o2) {
		if (o1.getBm25Score() > o2.getBm25Score()) {
			return 1;
		}
		else if (o1.getBm25Score() < o2.getBm25Score()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
