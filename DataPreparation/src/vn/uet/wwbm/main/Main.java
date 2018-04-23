/**
 * 
 */
package vn.uet.wwbm.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.uet.wwbm.answer_scoring.AnswerScoring;
import vn.uet.wwbm.answer_scoring.ExactSubsequenComporator;
import vn.uet.wwbm.answer_scoring.LCSComporator;
import vn.uet.wwbm.answer_scoring.OverlapComporator;
import vn.uet.wwbm.answer_scoring.SumCriterionComporator;
import vn.uet.wwbm.answer_scoring.entities.AnswerScoreCriterion;
import vn.uet.wwbm.decision_making.DecisionMarking;
import vn.uet.wwbm.main.entities.Question;
import vn.uet.wwbm.question_answering.QuestionAnswering;
import vn.uet.wwbm.question_answering.helpers.FileHelper;

/**
 * @author "PhanDoanCuong"
 *
 */
public class Main {

//	public static void main(String[] args) throws Exception {
//		for (int turn = 9; turn <= 10; turn++) {
//			try {
//				
//				List<Question> questions = FileHelper.readData("src/testdata/ql" + turn + ".txt");
//				QuestionAnswering qa = new QuestionAnswering();
//				AnswerScoring answerScoring = new AnswerScoring(qa);
//				Question question;
//
//				List<Integer> choiceLevenshtein = new ArrayList<Integer>();
//				List<Integer> choiceES = new ArrayList<Integer>();
//				List<Integer> choiceLCS = new ArrayList<Integer>();
//				List<Integer> choiceOverlap = new ArrayList<Integer>();
//				List<Integer> choiceAll = new ArrayList<Integer>();
//				for (int i = 0; i < questions.size(); i++) {
//					question = questions.get(i);
//					List<AnswerScoreCriterion> answers = answerScoring.scoring(question.getQuestion(),
//							question.getCandidateA(), question.getCandidateB(), question.getCandidateC(),
//							question.getCandidateD());
//
//					if (answers != null) {
//						if (!answers.isEmpty()) {
//							// answers.sort(new LevenshteinComporator());
//							// Collections.reverse(answers);
//							// choiceLevenshtein.add(answers.get(0).getCandidateCode());
//
//							answers.sort(new ExactSubsequenComporator());
//							Collections.reverse(answers);
//							choiceES.add(answers.get(0).getCandidateCode());
//
//							answers.sort(new OverlapComporator());
//							Collections.reverse(answers);
//							choiceOverlap.add(answers.get(0).getCandidateCode());
//
//							answers.sort(new LCSComporator());
//							Collections.reverse(answers);
//							choiceLCS.add(answers.get(0).getCandidateCode());
//
//							answers.sort(new SumCriterionComporator());
//							Collections.reverse(answers);
//							choiceAll.add(answers.get(0).getCandidateCode());
//						}
//					}
//				}
//
//				// Sum of all criterions
//				FileHelper.writeAnswer(choiceAll, questions, "src/result3/call" + turn + ".csv");
//				
//				// Result of Exact Subsequence
//				FileHelper.writeAnswer(choiceES, questions, "src/result3/ces" + turn + ".csv");
//				
//				// Result of 
//				FileHelper.writeAnswer(choiceLCS, questions, "src/result3/clcs" + turn + ".csv");
////				 FileHelper.writeAnswer(choiceLevenshtein, questions,
////				 "src/choice_Levenshtein1.csv");
//				
//				// Result of overlap criteria
//				FileHelper.writeAnswer(choiceOverlap, questions, "src/result/cov" + turn + ".csv");
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	// public static void main(String[] args) throws Exception {
	// StringBuilder data = new StringBuilder();
	// List<Question> questions = FileHelper.readData("src/test_data.txt");
	// FilterPipeline filterPipeline = new FilterPipeline();
	// Question question;
	// List<Passage> passages;
	// for (int i = 0; i < questions.size(); i++) {
	// question = questions.get(i);
	// passages = filterPipeline.filterPassages(question.getQuestion(),
	// question.getCandidateA(), question.getCandidateB(),
	// question.getCandidateC(), question.getCandidateD());
	// data.append(question.getQuestion() + "\n");
	// for (int j = 0; j < passages.size(); j++) {
	// data.append(passages.get(j).getTitle() + "\t"
	// + passages.get(j).getPassage() + "\t"
	// + passages.get(j).getUsefulProbability() + "\n");
	// }
	//
	// }
	// FileHelper.writeFile("src/mlresult.csv", data.toString());
	// }
	
	public static void main(String[] args) throws Exception {
		String dir = "src/testdata/ql";
		float ratioThreshold = 0.1f;
		float pollAudienceThreshold = 0.4f;
		int numberOfPassages = 2;
		QuestionAnswering qa = new QuestionAnswering();
		List<Question> questions;
		DecisionMarking dm;
		for (int i = 1; i <= 2; i++) {
			System.out.println("Turn " + i);
			questions = FileHelper.readData(dir + i + ".txt");
			dm = new DecisionMarking(questions);
			System.out.println(dm.playGame(qa, numberOfPassages, ratioThreshold, pollAudienceThreshold));
		
		}
	}
}
