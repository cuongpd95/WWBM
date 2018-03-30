/**
 * 
 */
package vn.uet.wwbm.question_answering;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import vn.uet.wwbm.main.entities.Question;
import vn.uet.wwbm.question_answering.comparators.BM25ScoreComparator;
import vn.uet.wwbm.question_answering.db.FasterDBHelper;
import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.helpers.FileHelper;
import vn.uet.wwbm.question_answering.interfaces.IQuestionAnswering;

/**
 * @author "PhanDoanCuong"
 *
 */
public class QuestionAnswering implements IQuestionAnswering {
	public static final int NUMBER_OF_PASSAGES = 30;
	private BM25Okapi searcher;

	public QuestionAnswering() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		searcher = new BM25Okapi(0.75d);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.uet.wwbm.question_answering.interfaces.IQuestionAnswering#
	 * getBestRelevantDoc(java.lang.String)
	 */
	@Override
	public List<BM25Score> getBestRelevantDoc(String query, String a, String b,
			String c, String d) throws IOException, SQLException, InstantiationException, IllegalAccessException {
		List<BM25Score> docs = searcher.searchAllTerm(query, a, b, c, d);

		if (docs.size() >= NUMBER_OF_PASSAGES) {
			docs.sort(new BM25ScoreComparator());
			Collections.reverse(docs);
			return docs.subList(0, NUMBER_OF_PASSAGES);
			// int[] ids = new int[2];
			// // Sort score;

			// for (int i = 0; i < 2; i++) {
			// ids[i] = docs.get(i).getDocId();
			// }
			// return ids;
		} else {
			return docs;
		}
	}

	public static void main(String[] args) throws IOException, SQLException, InstantiationException, IllegalAccessException {
		FasterDBHelper fasterDBHelper = FasterDBHelper.getInstance();
		List<Question> questions = FileHelper.readData("src/data_test2.txt");
		IQuestionAnswering qa = new QuestionAnswering();
		Question question;
		for (int i = 0; i < questions.size(); i++) {
			StringBuilder str = new StringBuilder();
			question = questions.get(i);
			str.append(question.getQuestion());
			List<BM25Score> psgs = qa.getBestRelevantDoc(question.getQuestion(),
					question.getCandidateA(), question.getCandidateB(),
					question.getCandidateC(), question.getCandidateD());
			if (psgs != null) {
				if (!psgs.isEmpty()) {
					for (int j = 0; j < psgs.size(); j++) {
						Passage passage = fasterDBHelper.getPassage(psgs.get(j).getDocId());
						str.append("\n" + passage.getTitle() + "\t" + passage.getPassage());
					}
				}
			}
			FileHelper.writeFile("src/d2p" + i + ".csv", str.toString());
		}

	}

}
