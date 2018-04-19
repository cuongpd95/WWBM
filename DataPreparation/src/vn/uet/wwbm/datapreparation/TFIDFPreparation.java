/**
 * 
 */
package vn.uet.wwbm.datapreparation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;
import vn.uet.wwbm.question_answering.db.DBHelpers;
import vn.uet.wwbm.question_answering.db.FasterDBHelper;
import vn.uet.wwbm.question_answering.db.MySQLConnector;
import vn.uet.wwbm.question_answering.entities.MyWord;
import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.entities.TFIDFNER;
import vn.uet.wwbm.question_answering.entities.Term;
import vn.uet.wwbm.question_answering.helpers.StringUtils;

/**
 * @author "PhanDoanCuong"
 *
 */
public class TFIDFPreparation {
	
	private FasterDBHelper dbHelpers;
	
	private MySQLConnector mySQL;
	
	public TFIDFPreparation(){
		dbHelpers = FasterDBHelper.getInstance();
//		mySQL = MySQLConnector.getInstance();
	}
	
	//get data from wiki table
	public List<Passage> getDocumentData() throws SQLException, InstantiationException, IllegalAccessException{
		return dbHelpers.queryPassages();
	}
	
	//calculate count then insert data into df table
	/**
	 * For each passage
	 * 1. Title + passage
	 * 2. word segmentation -> term list
	 * 3. update count then put into MyWord list
	 * 4. insert to df table
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void calculateDFData(List<Passage> passages, VnCoreNLP pipeline) throws IOException, SQLException, InstantiationException, IllegalAccessException{
		int length = passages.size(); 
        Annotation annotation;
        Passage psg;
        List<MyWord> wordList = new ArrayList<MyWord>();
        List<Word> words;
        int id = 6055567;
		for (int i = 0; i < length; i++) {
			psg = passages.get(i);
			// Annotation for title + passage
			annotation = new Annotation(psg.getPassage()); 
		    pipeline.annotate(annotation); 
		    words = annotation.getWords();
		    int wordIndex;
		    
		    for (int j = 0; j < words.size(); j++) {
//		    	if (StringUtils.isEntity(words.get(j).getNerLabel())) {
////					System.out.println(word.getForm() + "-" + word.getNerLabel());
//					wordIndex = getIndexOfWord(wordList, words.get(j));
//					if (wordIndex != -1) {
//						wordList.get(wordIndex).increaseCount();
//					}
//					else {
//						wordList.add(new MyWord(words.get(j).getForm(), 1));
//					}
//				}
		    	
		    	if (StringUtils.isWord(words.get(j).getForm().replace("_", " "))) {
		    		wordIndex = getIndexOfWord(wordList, words.get(j));
					if (wordIndex != -1) {
						wordList.get(wordIndex).increaseCount();
					}
					else {
						wordList.add(new MyWord(words.get(j).getForm(), 1));
					}
				}
		    	
			}
		    System.out.println(psg.getId()+1);
		    updateDFTable(id, psg.getId(), wordList);
		    id += wordList.size();
		    wordList.clear();
		}
	}
	

	/**
	 * @param i
	 * @param wordList
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void updateDFTable(int id, int docId, List<MyWord> wordList) throws SQLException, InstantiationException, IllegalAccessException {
		if (!wordList.isEmpty()) {
			dbHelpers.insertDFTable(id, docId, wordList);
		}
	}

	/**
	 * @param wordList
	 * @param word
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int getIndexOfWord(List<MyWord> wordList, Word word) {
		if (wordList == null || wordList.isEmpty()) {
			return -1;
		}
		else {
			int index = -1;
			int count = -1;
			for (Iterator iterator = wordList.iterator(); iterator.hasNext();) {
				count++;
				MyWord myWord = (MyWord) iterator.next();
				if (word.getForm().equalsIgnoreCase(myWord.getTerm())) {
					index = count;
					break;
				}
			}
			return index;
		}
	}
	
	public void copyMySQLToSQLServer() throws SQLException{
		List<Passage> passages = mySQL.queryMySQLPassages();
		dbHelpers.insertToSQLServer(passages);
	}
	
//	public List<TFIDFNER> getNERTerm() throws IOException, SQLException{
////		List<TFIDFNER> terms = new ArrayList<TFIDFNER>();
//		
//		String[] annotators = {"wseg","pos","ner"};
//		VnCoreNLP pipeline = new VnCoreNLP(annotators);
//		System.out.println("query");
////		List<TFIDFNER> allDatas = dbHelpers.getTFIDFNERData(pipeline);
////		System.out.println(allDatas.size());
////		Annotation annotation = new Annotation("");
////		for (Iterator iterator = allDatas.iterator(); iterator.hasNext();) {
////			TFIDFNER tfidfner = (TFIDFNER) iterator.next();
////			annotation.setRawText(tfidfner.getTerm().replace("_", " "));
////			System.out.println(tfidfner.getTerm().replace("_", " "));
////			pipeline.annotate(annotation);
////			List<Word> words = annotation.getWords();
////			for (Iterator iterator2 = words.iterator(); iterator2.hasNext();) {
////				Word word = (Word) iterator2.next();
////				System.out.println(word.getForm());
////				if (StringUtils.isEntity(word.getNerLabel())) {
////					System.out.println(word.getForm());
////					terms.add(tfidfner);
////				}
////			}
////		} 
//		
//		return allDatas;
//	}

	/**
	 * @param terms
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
//	public void updateTFIDFNER(List<TFIDFNER> terms) throws SQLException {
//		dbHelpers.updateTFIDFNER(terms);
//	}
	
//	public List<Term> getListTerm() throws SQLException{
//		return dbHelpers.getTermList();
//	}
	
	public void updateTermId(List<Term> terms) throws SQLException, InstantiationException, IllegalAccessException{
		dbHelpers.updateTermId(terms);
	}
	
//	public static void main(String[] args) {
//		TFIDFPreparation tf = new TFIDFPreparation();
//		try {
//			List<Term> terms = tf.getListTerm();
//			System.out.println(terms.size());
//			tf.updateTermId(terms);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
}
