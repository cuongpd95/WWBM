/**
 * 
 */
package vn.uet.wwbm.models;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import vn.uet.wwbm.filters.FilterPipeline;
import vn.uet.wwbm.main.entities.Question;
import vn.uet.wwbm.question_answering.helpers.FileHelper;

/**
 * @author "PhanDoanCuong"
 *
 */
public class FeatureExtraction {

	public static void main(String[] args) throws IOException, SQLException {
		List<Question> questions = FileHelper.readData("src/data_test2.txt");

		FilterPipeline filterPipeline = new FilterPipeline();

		Question question;
		List<PassageFeature> passages;
		for (int i = 0; i < questions.size(); i++) {
			question = questions.get(i);
			passages = filterPipeline.generateFeaturesOfPassage(
					question.getQuestion(), question.getCandidateA(),
					question.getCandidateB(), question.getCandidateC(),
					question.getCandidateD());
			FileHelper.writePassageExtraction("D:\\datafeatures\\q" + (i + 51) + ".csv",question.getQuestion(), passages);
		}

	}

}
