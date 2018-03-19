/**
 * 
 */
package vn.uet.wwbm.datapreparation;

import java.io.BufferedReader;
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
public class DataCleanup {
	public static void main(String[] args) {

        String csvFile = "src/idremove.csv";
        String line = "";

        List<Integer> ids = new ArrayList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	ids.add(Integer.parseInt(line));
     

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        DBHelpers dbHelpers = DBHelpers.getInstance();
        try {
			dbHelpers.removeId(ids);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
