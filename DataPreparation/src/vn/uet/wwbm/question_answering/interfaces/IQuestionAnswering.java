/**
 * 
 */
package vn.uet.wwbm.question_answering.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import vn.uet.wwbm.question_answering.BM25Score;

/**
 * @author "PhanDoanCuong"
 *
 */
public interface IQuestionAnswering {

	/**
	 * Get 2 best relevant documents
	 * 
	 * @param query
	 * @return document id
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<BM25Score> getBestRelevantDoc(String query, String a, String b, String c, String d) throws IOException, SQLException, InstantiationException, IllegalAccessException;

}
