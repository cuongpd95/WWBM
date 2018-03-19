/**
 * 
 */
package vn.uet.wwbm.answer_scoring;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.uet.wwbm.answer_scoring.entities.AnswerScoreCriterion;
import vn.uet.wwbm.answer_scoring.interfaces.IAnswerScoring;
import vn.uet.wwbm.filters.FilterPipeline;
import vn.uet.wwbm.filters.StringSimilarity;
import vn.uet.wwbm.question_answering.entities.Passage;

/**
 * @author "PhanDoanCuong"
 *
 */
public class AnswerScoring implements IAnswerScoring {

	private FilterPipeline filterPipeline;

	private StringSimilarity similarity;

	public AnswerScoring() throws IOException, SQLException {
		filterPipeline = new FilterPipeline();
		similarity = StringSimilarity.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vn.uet.wwbm.answer_scoring.interfaces.IAnswerScoring#scoring(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	// @Override
	// public List<AnswerScore> scoring(String question, String A, String B,
	// String C, String D) throws IOException, SQLException {
	// List<AnswerScore> scores = new ArrayList<AnswerScore>();
	//
	// // Get 2 relevant passages
	// List<BM25Score> docs = questionAnswering.getBestRelevantDoc(question,
	// A, B, C, D);
	// for (int i = 0; i < docs.size(); i++) {
	// System.out.println(docs.get(i).getDocId() + " - " +
	// docs.get(i).getScore());
	// }
	//
	// List<List<AnswerScore>> ansScore = new ArrayList<List<AnswerScore>>();
	// for (Iterator iterator = docs.iterator(); iterator.hasNext();) {
	// BM25Score bm25Score = (BM25Score) iterator.next();
	// ansScore.add(calculateScore(bm25Score, A, B, C, D));
	// }
	//
	// List<AnswerScore> docScore;
	//
	// double[] score = new double[4];
	// double totalBM25Score = 0;
	// for (int i = 0; i < ansScore.size(); i++) {
	// docScore = ansScore.get(i);
	// score[0] += docScore.get(0).getScore()*docs.get(i).getScore();
	// score[1] += docScore.get(1).getScore()*docs.get(i).getScore();
	// score[2] += docScore.get(2).getScore()*docs.get(i).getScore();
	// score[3] += docScore.get(3).getScore()*docs.get(i).getScore();
	// totalBM25Score += docs.get(i).getScore();
	// }
	//
	// for (int i = 0; i < 4; i++) {
	// scores.add(new AnswerScore(i + 1, score[i] / totalBM25Score));
	// }
	//
	// // Find the best answer
	// return scores;
	// }

	/**
	 * @param score
	 * @param score2
	 * @param score3
	 * @param score4
	 * @return
	 */
	// private double getFinalScore(double filterScore1, double bm25Score1,
	// double filterScore2, double bm25Score2) {
	// return (((filterScore1 * bm25Score1) + (filterScore2 * bm25Score2)) /
	// (bm25Score1 + bm25Score2));
	// }

	/**
	 * @param bm25Score
	 * @param d
	 * @param c
	 * @param b
	 * @param a
	 * @return
	 * @throws Exception 
	 */
	// private List<AnswerScore> calculateScore(BM25Score bm25Score, String a,
	// String b, String c, String d) throws SQLException {
	// Passage passage = fasterDBHelper.getPassage(bm25Score.getDocId());
	// if (passage == null) {
	// return null;
	// } else {
	// // Exact Subsequence
	// double p1 = calculateExactSubsequence(a, passage.getPassage());
	// double p2 = calculateExactSubsequence(b, passage.getPassage());
	// double p3 = calculateExactSubsequence(c, passage.getPassage());
	// double p4 = calculateExactSubsequence(d, passage.getPassage());
	// double esA = finalMeanScoring(CandidateAnswerCode.A, p1, p2, p3, p4);
	// double esB = finalMeanScoring(CandidateAnswerCode.B, p1, p2, p3, p4);
	// double esC = finalMeanScoring(CandidateAnswerCode.C, p1, p2, p3, p4);
	// double esD = finalMeanScoring(CandidateAnswerCode.D, p1, p2, p3, p4);
	// // Levenshtein Similarity
	// p1 = calculateLevenshteinSimilarity(a, passage.getTitle());
	// p2 = calculateLevenshteinSimilarity(b, passage.getTitle());
	// p3 = calculateLevenshteinSimilarity(c, passage.getTitle());
	// p4 = calculateLevenshteinSimilarity(d, passage.getTitle());
	// // p1 = p2 = p3 = p4 = 0d;
	// double lsA = finalMeanScoring(CandidateAnswerCode.A, p1, p2, p3, p4);
	// double lsB = finalMeanScoring(CandidateAnswerCode.B, p1, p2, p3, p4);
	// double lsC = finalMeanScoring(CandidateAnswerCode.C, p1, p2, p3, p4);
	// double lsD = finalMeanScoring(CandidateAnswerCode.D, p1, p2, p3, p4);
	// // Overlap
	// p1 = calculateOverlap(a, passage.getPassage());
	// p2 = calculateOverlap(b, passage.getPassage());
	// p3 = calculateOverlap(c, passage.getPassage());
	// p4 = calculateOverlap(d, passage.getPassage());
	// double oA = finalMeanScoring(CandidateAnswerCode.A, p1, p2, p3, p4);
	// double oB = finalMeanScoring(CandidateAnswerCode.B, p1, p2, p3, p4);
	// double oC = finalMeanScoring(CandidateAnswerCode.C, p1, p2, p3, p4);
	// double oD = finalMeanScoring(CandidateAnswerCode.D, p1, p2, p3, p4);
	//
	// //LCS
	// p1 = calculateLCS(a, passage.getPassage());
	// p2 = calculateLCS(b, passage.getPassage());
	// p3 = calculateLCS(c, passage.getPassage());
	// p4 = calculateLCS(d, passage.getPassage());
	// double lscA = finalMeanScoring(CandidateAnswerCode.A, p1, p2, p3, p4);
	// double lscB = finalMeanScoring(CandidateAnswerCode.B, p1, p2, p3, p4);
	// double lscC = finalMeanScoring(CandidateAnswerCode.C, p1, p2, p3, p4);
	// double lscD = finalMeanScoring(CandidateAnswerCode.D, p1, p2, p3, p4);
	// List<AnswerScore> scores = new ArrayList<AnswerScore>();
	// scores.add(new AnswerScore(CandidateAnswerCode.A,
	// (esA + lsA + oA + lscA) / 4));
	// scores.add(new AnswerScore(CandidateAnswerCode.B,
	// (esB + lsB + oB + lscB) / 4));
	// scores.add(new AnswerScore(CandidateAnswerCode.C,
	// (esC + lsC + oC + lscC) / 4));
	// scores.add(new AnswerScore(CandidateAnswerCode.D,
	// (esD + lsD + oD + lscD) / 4));
	//
	// return scores;
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vn.uet.wwbm.answer_scoring.interfaces.IAnswerScoring#scoring(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<AnswerScoreCriterion> scoring(String question, String A,
			String B, String C, String D) throws Exception {
		List<AnswerScoreCriterion> answers = new ArrayList<AnswerScoreCriterion>();

		List<Passage> passages = filterPipeline.filterPassages(question, A, B,
				C, D);
		
		System.out.println(passages.size());

		if (passages != null) {
			if (!passages.isEmpty()) {
				double p1;
				double p2;
				double p3;
				double p4;

				double[] es;
				double[] lcs;
				double[] ls;
				double[] ov;

				Passage passage;

				double totalScore = getTotalScore(passages);

				double psgScore;

				PassageAnswerScore pas;

				List<PassageAnswerScore> rankedScore = new ArrayList<PassageAnswerScore>();

				for (int i = 0; i < passages.size(); i++) {
					pas = new PassageAnswerScore();
					es = new double[4];
					lcs = new double[4];
					ls = new double[4];
					ov = new double[4];

					passage = passages.get(i);
					psgScore = (passage.getUsefulProbability()) / totalScore;

					p1 = similarity.calculateExactSubsequence(A,
							passage.getPassage());
					p2 = similarity.calculateExactSubsequence(B,
							passage.getPassage());
					p3 = similarity.calculateExactSubsequence(C,
							passage.getPassage());
					p4 = similarity.calculateExactSubsequence(D,
							passage.getPassage());
					es = getCriterionScore(p1 * psgScore, p2 * psgScore, p3
							* psgScore, p4 * psgScore);

//					p1 = similarity.calculateLevenshteinSimilarity(A,
//							passage.getTitle());
//					p2 = similarity.calculateLevenshteinSimilarity(B,
//							passage.getTitle());
//					p3 = similarity.calculateLevenshteinSimilarity(C,
//							passage.getTitle());
//					p4 = similarity.calculateLevenshteinSimilarity(D,
//							passage.getTitle());
//					ls = getCriterionScore(p1 * psgScore, p2 * psgScore, p3
//							* psgScore, p4 * psgScore);

					p1 = similarity.calculateOverlap(A, passage.getPassage());
					p2 = similarity.calculateOverlap(B, passage.getPassage());
					p3 = similarity.calculateOverlap(C, passage.getPassage());
					p4 = similarity.calculateOverlap(D, passage.getPassage());
					ov = getCriterionScore(p1 * psgScore, p2 * psgScore, p3
							* psgScore, p4 * psgScore);

					p1 = similarity.calculateLCS(A, passage.getPassage());
					p2 = similarity.calculateLCS(B, passage.getPassage());
					p3 = similarity.calculateLCS(C, passage.getPassage());
					p4 = similarity.calculateLCS(D, passage.getPassage());
					lcs = getCriterionScore(p1 * psgScore, p2 * psgScore, p3
							* psgScore, p4 * psgScore);

					pas.setEs(es);
					pas.setLcs(lcs);
//					pas.setLs(ls);
					pas.setOv(ov);

					rankedScore.add(pas);
				}

				for (int i = 0; i < 4; i++) {
					PassageAnswerScore pasi;
					double esSum = 0;
					double lsSum = 0;
					double lcsSum = 0;
					double ovSum = 0;
					for (int j = 0; j < rankedScore.size(); j++) {
						pasi = rankedScore.get(j);
						esSum += pasi.getEs()[i];
//						lsSum += pasi.getLs()[i];
						lcsSum += pasi.getLcs()[i];
						ovSum += pasi.getOv()[i];
					}
					System.out.println(i + 1 + " - " + esSum + " - " + lsSum + " - "+ lcsSum + " - " + ovSum);
					AnswerScoreCriterion asci = new AnswerScoreCriterion(i + 1,
							esSum, lsSum, lcsSum, ovSum);
					answers.add(asci);
				}
			}
		}

		return answers;
	}

	/**
	 * @param passages
	 * @return
	 */
	private double getTotalScore(List<Passage> passages) {
		if (passages == null) {
			return 0;
		}
		if (passages.isEmpty()) {
			return 0;
		}
		double sum = 0;
		for (int i = 0; i < passages.size(); i++) {
			sum += passages.get(i).getUsefulProbability();
		}
		return sum;
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return
	 */
	private double[] getCriterionScore(double p1, double p2, double p3,
			double p4) {
		double[] result = new double[4];
		if (p1 + p2 + p3 + p4 == 0) {
			for (int i = 0; i < 4; i++) {
				result[i] = 0;
			}
		} else {
			result[0] = p1 / (p1 + p2 + p3 + p4);
			result[1] = p2 / (p1 + p2 + p3 + p4);
			result[2] = p3 / (p1 + p2 + p3 + p4);
			result[3] = p4 / (p1 + p2 + p3 + p4);
		}
		return result;
	}

}
