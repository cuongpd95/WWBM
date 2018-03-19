/**
 * 
 */
package vn.uet.wwbm.question_answering.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import vn.uet.wwbm.question_answering.BM25Okapi;
import vn.uet.wwbm.question_answering.BM25Score;
import vn.uet.wwbm.question_answering.helpers.FileHelper;

/**
 * @author "PhanDoanCuong"
 *
 */
public class BM25Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String query = "Ai là người đã lãnh đạo cuộc chiến với quân Nam Hán trên sông Bạch Đằng?";
			BM25Okapi bm25Okapi = new BM25Okapi(0.75d);

//			List<BM25Score> scores = bm25Okapi.search(query);
			
//			List<BM25Score> scores = bm25Okapi.searchAllTerm(query);
			
//			FileHelper.writeBM25Score(query, scores, "src/score.csv");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
