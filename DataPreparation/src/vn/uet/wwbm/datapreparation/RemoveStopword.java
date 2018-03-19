/**
 * 
 */
package vn.uet.wwbm.datapreparation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.uet.wwbm.question_answering.db.DBHelpers;

/**
 * @author "PhanDoanCuong"
 *
 */
public class RemoveStopword {

	public static void main(String[] args) {
		List<String> stopwords = new ArrayList<String>();
		try {
			String line;
			@SuppressWarnings("resource")
			BufferedReader bufferreader = new BufferedReader(new FileReader(
					"src/vietnamese-stopwords.txt"));
			line = bufferreader.readLine();
			while (line != null) {
				// do whatever here
				stopwords.add(line);
				line = bufferreader.readLine();
			}
			DBHelpers dbHelpers = DBHelpers.getInstance();
			dbHelpers.removeStopWord(stopwords);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
