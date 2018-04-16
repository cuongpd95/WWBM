/**
 * 
 */
package vn.uet.wwbm.filters;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.uet.wwbm.models.PassageFeature;
import vn.uet.wwbm.question_answering.BM25Score;
import vn.uet.wwbm.question_answering.QuestionAnswering;
import vn.uet.wwbm.question_answering.comparators.CriteriaPassageComporator;
import vn.uet.wwbm.question_answering.comparators.SumCriteria;
import vn.uet.wwbm.question_answering.db.FasterDBHelper;
import vn.uet.wwbm.question_answering.entities.Passage;

/**
 * @author "PhanDoanCuong"
 *
 */
public class FilterPipeline {
	public static final int NUMBER_OF_FILTER_PSGS = 2;

	private QuestionAnswering questionAnswering;

	private FasterDBHelper fasterDBHelper;

	private StringSimilarity stringSimilarity;

	public FilterPipeline() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		questionAnswering = new QuestionAnswering();
		fasterDBHelper = FasterDBHelper.getInstance();
		stringSimilarity = StringSimilarity.getInstance();
	}
	
	public List<Passage> filterPassagesHardCode(String question, String cA, String cB, String cC, String cD) throws IOException, SQLException, InstantiationException, IllegalAccessException{
		List<Passage> passages = new ArrayList<Passage>();
		List<BM25Score> psgs = questionAnswering.getBestRelevantDoc(question,
				cA, cB, cC, cD);
		//
		if (psgs != null) {
			if (!psgs.isEmpty()) {
				System.out.println("Rerank passages");
				BM25Score psg;
				double es;
				double ov;
				Passage passage;
				for (int i = 0; i < psgs.size(); i++) {
					psg = psgs.get(i);
					passage = fasterDBHelper.getPassage(psg.getDocId());
					passage.setBm25Score(psg.getScore());

					// Calculate filter scores
					es = stringSimilarity.calculateExactSubsequence(question,
							passage.getPassage());
					ov = stringSimilarity.calculateOverlap(question,
							passage.getPassage());
					passage.setEs(es);
					passage.setOv(ov);
					passages.add(passage);
				}
			}
		}
		passages.sort(new CriteriaPassageComporator());
		Collections.reverse(passages);
		if (passages.size() >= NUMBER_OF_FILTER_PSGS) {
			return passages.subList(0, NUMBER_OF_FILTER_PSGS);
		}
		else {
			return passages;
		}	
	}

	public List<Passage> filterPassages(String question, String cA, String cB,
			String cC, String cD) throws Exception {
		List<Passage> passages = new ArrayList<Passage>();
		List<BM25Score> psgs = questionAnswering.getBestRelevantDoc(question,
				cA, cB, cC, cD);
		//
		if (psgs != null) {
			if (!psgs.isEmpty()) {
//				Logistic logistic = (Logistic) CreterionLogisticRegression.loadModel();
//				Instances dataSet = CreterionLogisticRegression.loadDataSet();
				
				BM25Score psg;
				double es;
				double ov;
				Passage passage;
//				Instance instance = new Instance(3);
//				instance.setDataset(dataSet);
				
				double totalBM25 = 0;
				double totalES = 0;
				double totalOV = 0;
				for (int i = 0; i < psgs.size(); i++) {
					psg = psgs.get(i);
					passage = fasterDBHelper.getPassage(psg.getDocId());
					passage.setBm25Score(psg.getScore());

					// Calculate filter scores
					es = stringSimilarity.calculateExactSubsequence(question,
							passage.getPassage());
					ov = stringSimilarity.calculateOverlap(question,
							passage.getPassage());
					passage.setEs(es);
					passage.setOv(ov);
//					instance.setValue(0, passage.getBm25Score());
//					instance.setValue(1, es);
//					instance.setValue(2, ov);
					
//					passage.setUsefulProbability(logistic.distributionForInstance(instance)[1]);
					totalBM25 += psg.getScore();
					totalES += es;
					totalOV += ov;
					passages.add(passage);
				}
				
				//Normalize score
				for (int i = 0; i < passages.size(); i++) {
					passages.get(i).normalizeScore(totalBM25, totalES, totalOV);
				}
			}
		}
//		passages.sort(new ProbabilityPassageComporator());
		
		passages.sort(new SumCriteria());
		
//		passages.sort(new );
		
		Collections.reverse(passages);
		
		System.out.println(question);
		for (int i = 0; i < passages.size(); i++) {
			System.out.println(passages.get(i).getBm25Score() + " - " + passages.get(i).getEs() + " - " + passages.get(i).getOv() + " - " +passages.get(i).getPassage());
			
		}
		if (passages.size() >= 2) {
			return passages.subList(0, 2);
		}
		else {
			return passages;
		}
		
	}
	
	
	public List<PassageFeature> generateFeaturesOfPassage(String question, String cA, String cB,
			String cC, String cD) throws IOException, SQLException, InstantiationException, IllegalAccessException {
		List<PassageFeature> passages = new ArrayList<PassageFeature>();
		List<BM25Score> psgs = questionAnswering.getBestRelevantDoc(question,
				cA, cB, cC, cD);

		//
		if (psgs != null) {
			if (!psgs.isEmpty()) {
				BM25Score psg;
				double es;
				double ov;
				int lcs;
				for (int i = 0; i < psgs.size(); i++) {
					psg = psgs.get(i);
					Passage passage = fasterDBHelper.getPassage(psg.getDocId());
					passage.setBm25Score(psg.getScore());

					// Calculate filter scores
					es = stringSimilarity.calculateExactSubsequence(question,
							passage.getPassage());
					ov = stringSimilarity.calculateOverlap(question,
							passage.getPassage());
//					ngram = stringSimilarity.calculateNGram(question,
//							passage.getPassage());
					lcs = stringSimilarity.calculateLCS(question, passage.getPassage());
					
//					passage.setMeanScore((es + ov) / 2);
					passages.add(new PassageFeature(psg.score, es, ov, lcs, passage.getTitle(), passage.getPassage()));
				}
				
				//Load model and then choice 2 best passages 
				
				// Normalize bm25 score and filters score
				// Calculate sum
//				double bm25Sum = 0;
//				double meanScoreSum = 0;
//				for (int i = 0; i < passages.size(); i++) {
//					bm25Sum += passages.get(i).getBm25Score();
//					meanScoreSum += passages.get(i).getMeanScore();
//				}
//				// Assign weight score
//				for (int i = 0; i < passages.size(); i++) {
//					passages.get(i).setBm25Score(passages.get(i).getBm25Score()/bm25Sum);
//					passages.get(i).setMeanScore(passages.get(i).getMeanScore()/meanScoreSum);
//				}
				
			}
		}
//		Collections.reverse(passages);
//		if (passages.size() >= 2) {
//			return passages.subList(0, 2);
//		}
//		else {
//			return passages;
//		}
		return passages;
		
	}
	
	public static void main(String[] args) {
		Passage pa = new Passage(1, "t1", "p1");
		Passage pb = new Passage(2, "t2", "p2");
		List<Passage> psgs = new ArrayList<Passage>();
		psgs.add(pa);
		psgs.add(pb);
		psgs.get(1).setBm25Score(100);
		System.out.println(psgs.get(1).getBm25Score());
	}

}
