/**
 * 
 */
package vn.uet.wwbm.answer_scoring.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import vn.uet.wwbm.answer_scoring.entities.AnswerScoreCriterion;

/**
 * @author "PhanDoanCuong"
 *
 */
public interface IAnswerScoring {
	
	public List<AnswerScoreCriterion> scoring(String question, String A, String B, String C, String D) throws IOException, SQLException, Exception;

}
