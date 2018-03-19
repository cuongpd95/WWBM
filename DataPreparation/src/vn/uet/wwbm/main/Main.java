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
import vn.uet.wwbm.answer_scoring.interfaces.IAnswerScoring;
import vn.uet.wwbm.filters.FilterPipeline;
import vn.uet.wwbm.main.entities.Question;
import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.helpers.FileHelper;

/**
 * @author "PhanDoanCuong"
 *
 */
public class Main {

	// public static void main(String[] args) {
	// try {
	// List<Question> questions = FileHelper.readData("src/test_data.txt");
	// // List<Question> questions = new ArrayList<Question>();
	//
	// // questions
	// // .add(new Question(
	// //
	// "Dấu tích Người tối cổ đã được tìm thấy đầu tiên ở tỉnh nào của Việt Nam?",
	// // "Nghệ An", "Thanh Hóa", "Cao Bằng", "Lạng Sơn", 1));
	// // questions.add(new
	// //
	// Question("Ai chỉ huy trận Vân Đồn đánh tan đoàn thuyền lương của quân xâm lược nhà Nguyên?",
	// // "Phạm Ngũ Lão", "Trần Khánh Dư",
	// // "Trần Quang Khải","Trần Nhật Duật", 1));
	// // questions.add(new
	// // Question("Thành cổ Sơn Tây do vị vua nào xây dựng nên vào năm 1822",
	// // "Gia Long", "Minh Mạng", "Thiệu Trị","Tự Đức", 1));
	// // questions.add(new
	// //
	// Question("Chùa nào được Lý Nam Đế cho xây sau khi lên ngôi hoàng đế năm 544",
	// // "Chùa Trấn Quốc", "Chùa Một Cột", "Chùa Tây Phương",
	// // "Chùa Tứ Pháp", 1));
	// // questions.add(new
	// // Question("Thành Long Biên được ai chọn làm kinh đô",
	// // "An Dương Vương", "Lý Nam Đế", "Ngô Quyền","Đinh Tiên Hoàng",
	// // 1));
	// // questions.add(new
	// //
	// Question("Vị vua nào ban hành chiếu \"Cần Vương\" kêu gọi đồng bào, tướng lĩnh, sĩ phu ra sức giúp vua cứu nước khi thực dân Pháp đặt ách thống trị lên đất nước chúng ta",
	// // "Hàm Nghi", "Duy Tân", "Thành Thái","Dục Đức", 1));
	//
	// IAnswerScoring answerScoring = new AnswerScoring();
	// Question question;
	//
	// List<Integer> choiceLevenshtein = new ArrayList<Integer>();
	// List<Integer> choiceES = new ArrayList<Integer>();
	// List<Integer> choiceLCS = new ArrayList<Integer>();
	// List<Integer> choiceOverlap = new ArrayList<Integer>();
	// List<Integer> choiceAll = new ArrayList<Integer>();
	// for (int i = 0; i < questions.size(); i++) {
	// question = questions.get(i);
	// List<AnswerScoreCriterion> answers = answerScoring.scoring(
	// question.getQuestion(), question.getCandidateA(),
	// question.getCandidateB(), question.getCandidateC(),
	// question.getCandidateD());
	//
	// if (answers != null) {
	// if (!answers.isEmpty()) {
	// // answers.sort(new LevenshteinComporator());
	// // Collections.reverse(answers);
	// // choiceLevenshtein.add(answers.get(0).getCandidateCode());
	//
	// answers.sort(new ExactSubsequenComporator());
	// Collections.reverse(answers);
	// choiceES.add(answers.get(0).getCandidateCode());
	//
	// answers.sort(new OverlapComporator());
	// Collections.reverse(answers);
	// choiceOverlap.add(answers.get(0).getCandidateCode());
	//
	// answers.sort(new LCSComporator());
	// Collections.reverse(answers);
	// choiceLCS.add(answers.get(0).getCandidateCode());
	//
	// answers.sort(new SumCriterionComporator());
	// Collections.reverse(answers);
	// choiceAll.add(answers.get(0).getCandidateCode());
	// }
	// }
	// }
	//
	// FileHelper.writeAnswer(choiceAll, questions, "src/call1.csv");
	// FileHelper.writeAnswer(choiceES, questions, "src/ces1.csv");
	// FileHelper.writeAnswer(choiceLCS, questions, "src/clcs1.csv");
	// // FileHelper.writeAnswer(choiceLevenshtein, questions,
	// "src/choice_Levenshtein1.csv");
	// FileHelper.writeAnswer(choiceOverlap, questions, "src/cov1.csv");
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// }

	public static void main(String[] args) throws Exception {
		StringBuilder data = new StringBuilder();
		List<Question> questions = FileHelper.readData("src/test_data.txt");
		FilterPipeline filterPipeline = new FilterPipeline();
		Question question;
		List<Passage> passages;
		for (int i = 0; i < questions.size(); i++) {
			question = questions.get(i);
			passages = filterPipeline.filterPassages(question.getQuestion(),
					question.getCandidateA(), question.getCandidateB(),
					question.getCandidateC(), question.getCandidateD());
			data.append(question.getQuestion() + "\n");
			for (int j = 0; j < passages.size(); j++) {
				data.append(passages.get(j).getTitle() + "\t"
						+ passages.get(j).getPassage() + "\t"
						+ passages.get(j).getUsefulProbability() + "\n");
			}

		}
		FileHelper.writeFile("src/mlresult.csv", data.toString());
	}
}
