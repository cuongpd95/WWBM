/**
 * 
 */
package vn.uet.wwbm.datapreparation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import vn.pipeline.VnCoreNLP;
import vn.uet.wwbm.question_answering.entities.Passage;

/**
 * @author "PhanDoanCuong"
 *
 */
public class GenerateTFIDFTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TFIDFPreparation pre = new TFIDFPreparation();
		try {
			
			List<Passage> psgs = pre.getDocumentData();
			//
			String[] annotators = {"wseg","pos","ner"}; 
	        VnCoreNLP pipeline = new VnCoreNLP(annotators);
			pre.calculateDFData(psgs, pipeline);
			
//			pre.copyMySQLToSQLServer();
			
			
			
//			List<TFIDFNER> terms = pre.getNERTerm();
//			pre.updateTFIDFNER(terms);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
