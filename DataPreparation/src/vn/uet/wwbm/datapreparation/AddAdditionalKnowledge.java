package vn.uet.wwbm.datapreparation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import vn.uet.wwbm.question_answering.db.DBHelpers;
import vn.uet.wwbm.question_answering.db.FasterDBHelper;
import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.entities.Term;
import vn.uet.wwbm.question_answering.helpers.FileHelper;

public class AddAdditionalKnowledge {

//	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
//		String path = "src/data.csv";
//		int currentPsgId = 176503;
//		List<Passage> psgs = FileHelper.readPassages(currentPsgId, path);
//		FasterDBHelper dbHelper = FasterDBHelper.getInstance();
//		dbHelper.connect();
//		dbHelper.insertPassages(psgs);
//		dbHelper.disconnect();
//	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {
		FasterDBHelper dbHelper = FasterDBHelper.getInstance();
		dbHelper.connect();
		List<PassVec> vecs = dbHelper.getNullIDTermList();
//		int dicsize =  170482;
//		PassVec pv;
//		for (int i = 0; i < vecs.size(); i++) {
//			pv = vecs.get(i);
//			dbHelper.addTermToDictionary(dicsize, pv.getTerm());
//			dicsize++;
//		}
		
		HashMap<String, Integer> dics = dbHelper.getDictionary();
		System.out.println("dic " + dics.size());
		PassVec pv;
		for (int i = 0; i < vecs.size(); i++) {
			System.out.println(i);
			pv = vecs.get(i);
			if(dics.containsKey(pv.getTerm())) {
				dbHelper.updateTermId(pv.getId(), dics.get(pv.getTerm()));
			}
		}
		dbHelper.disconnect();
	}

//	public static void main(String[] args) throws SQLException {
//		DBHelpers db = DBHelpers.getInstance();
//		List<Term> terms = db.getListTerm();
//		db.updateTermId(terms);
//	}
}
