package vn.uet.wwbm.question_answering.comparators;

import java.util.Comparator;

import vn.uet.wwbm.question_answering.entities.Passage;

public class CriteriaPassageComporator implements Comparator<Passage> {

	@Override
	public int compare(Passage o1, Passage o2) {
		if(o1.getEs() + o1.getOv() > o2.getEs() + o2.getOv()) {
			return 1;
		}
		else if (o1.getEs() + o1.getOv() < o2.getEs() + o2.getOv()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
