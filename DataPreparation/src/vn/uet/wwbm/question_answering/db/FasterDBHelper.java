/**
 * 
 */
package vn.uet.wwbm.question_answering.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import vn.pipeline.Word;
import vn.uet.wwbm.datapreparation.PassVec;
import vn.uet.wwbm.question_answering.entities.MyWord;
import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.entities.Term;
import vn.uet.wwbm.question_answering.helpers.PropertyLoader;

/**
 * @author "PhanDoanCuong"
 *
 */
public class FasterDBHelper {

	private static FasterDBHelper instance;
	private Connection con;
	private Statement statement;
	private PreparedStatement prepared;
	private PropertyLoader loader;
	private Properties prop;

	/**
	 * design pattern singleton to get object JDBCManager.
	 * 
	 * @return Instance of JDBCManager
	 */
	public static FasterDBHelper getInstance() {
		if (null == instance) {
			instance = new FasterDBHelper();
		}
		return instance;
	}

	/**
	 * Constructor for class JDBCManager. Not allow Object use JDBCManager can
	 * create new JDBCManager().
	 */
	private FasterDBHelper() {
		loader = new PropertyLoader();
		prop = loader.getpropObject("config.properties");
	}

	/**
	 * Connect database with CONNECTION_URL.
	 * 
	 * @return boolean
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean connect() throws InstantiationException, IllegalAccessException {
		try {
			// check connect is null or close
			if (null == con || con.isClosed()) {
				// register the driver class with DriverManager
				Class.forName(prop.getProperty("drivername")).newInstance();
				
				// open connect
				con = DriverManager.getConnection(prop.getProperty("connectionString")
						, prop.getProperty("uid"),prop.getProperty("pwd")
						);
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * Disconnect database.
	 */
	public void disconnect() {
		try {
			// check connect is null or close
			if (null != con && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * @param docId
	 * @param wordList
	 * @throws SQLException
	 */
	public void insertDFTable(int docId, List<MyWord> wordList) throws SQLException {
		// STEP 4: Execute a query
		// connect();
		statement = con.createStatement();
		String sql = "INSERT INTO tfidf(id, term, term_count) values(?,?,?)";
		prepared = con.prepareStatement(sql);

		int count = 1;
		for (Iterator iterator = wordList.iterator(); iterator.hasNext(); count++) {
			MyWord myWord = (MyWord) iterator.next();

			System.out.println(myWord.getTerm());
			// Insert to DF table
			prepared.setInt(1, docId);
			prepared.setNString(2, myWord.getTerm());
			prepared.setInt(3, myWord.getCount());
			prepared.executeUpdate();
		}

		prepared.close();
		// con.close();
	}

	public void insertToSQLServer(List<Passage> passages) throws SQLException {
		// connect();
		statement = con.createStatement();
		String sql = "INSERT INTO passages(id, title, text) values(?,?,?)";
		prepared = con.prepareStatement(sql);
		int count = 1;

		for (Iterator iterator = passages.iterator(); iterator.hasNext(); count++) {
			Passage passage = (Passage) iterator.next();
			// Insert to DF table
			prepared.setInt(1, passage.getId());
			prepared.setNString(2, passage.getTitle());
			prepared.setNString(3, passage.getPassage());
			prepared.executeUpdate();
		}

		prepared.close();
		// disconnect();
	}

	public void removeId(List<Integer> ids) throws SQLException {
		// connect();
		statement = con.createStatement();
		String sql = "DELETE FROM tfidf WHERE id = ?";
		prepared = con.prepareStatement(sql);

		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			Integer passage = (Integer) iterator.next();
			System.out.println(passage);
			// Insert to DF table
			prepared.setInt(1, passage.intValue());
			prepared.executeUpdate();
		}

		prepared.close();
		// disconnect();
	}

	public void removeStopWord(List<String> stopwords) throws SQLException {
		// connect();
		statement = con.createStatement();
		String sql = "DELETE FROM tfidf WHERE lower(term) = ?";
		prepared = con.prepareStatement(sql);

		for (Iterator iterator = stopwords.iterator(); iterator.hasNext();) {
			String passage = (String) iterator.next();
			System.out.println(passage);
			// Insert to DF table
			prepared.setNString(1, passage.trim().toLowerCase().replace(" ", "_"));
			prepared.executeUpdate();
		}

		prepared.close();
		// disconnect();
	}

	/**
	 * @param docId
	 * @param term
	 * @return
	 * @throws SQLException
	 */
	public int getTermFrequency(int docId, String term) throws SQLException {
		int count = 0;

		// connect();

		String sql = "SELECT term_count FROM tfidf_ner WHERE id = ? AND CHECKSUM(?) = cs_lower_term AND LowerTerm = ?";
		prepared = con.prepareStatement(sql);
		prepared.setNString(2, term.toLowerCase());
		prepared.setNString(3, term.toLowerCase());
		prepared.setInt(1, docId);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			count = rs.getInt("term_count");
		}

		rs.close();
		prepared.close();
		// con.close();

		return count;
	}

	/**
	 * @param docId
	 * @return
	 * @throws SQLException
	 */
	public int getDocLength(int docId) throws SQLException {
		int length = 0;

		// connect();

		String sql = "SELECT docLength FROM docLengthNERTB WHERE id = ?";
		prepared = con.prepareStatement(sql);
		prepared.setInt(1, docId);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			length = rs.getInt("docLength");
		}

		rs.close();
		prepared.close();
		// con.close();

		return length;
	}

	/**
	 * @param term
	 * @return
	 * @throws SQLException
	 */
	public int getDocumentFrequency(String term) throws SQLException {
		int docFrequency = 0;

		// connect();

		String sql = "SELECT docf FROM docFrequencyNER_TB where CHECKSUM(?) = termcode AND term = ?";
		prepared = con.prepareStatement(sql);
		prepared.setNString(1, term.toLowerCase());
		prepared.setNString(2, term.toLowerCase());

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			docFrequency = rs.getInt("docf");
		}

		rs.close();
		prepared.close();
		// con.close();

		return docFrequency;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getNumberOfDocument() throws SQLException {
		int numberOfDocument = 0;

		// connect();

		String sql = "select count(id) as nDoc from passages";

		prepared = con.prepareStatement(sql);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			numberOfDocument = rs.getInt("nDoc");
		}

		rs.close();
		prepared.close();
		// con.close();

		return numberOfDocument;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getDatasetLength() throws SQLException {
		int datasetLength = 0;

		// connect();

		String sql = "select sum(term_count) as datasetLength from tfidf_ner";

		prepared = con.prepareStatement(sql);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			datasetLength = rs.getInt("datasetLength");
		}

		rs.close();
		prepared.close();
		// con.close();

		return datasetLength;
	}

	public static void main(String[] args) {
		DBHelpers db = DBHelpers.getInstance();
		try {
			System.out.println(db.getTermFrequency(1, "Lịch_sử"));
			System.out.println(db.getDocLength(1));
			System.out.println(db.getDocumentFrequency("Lịch_sử"));
			System.out.println(db.getDatasetLength());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param tokens
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> getRelevantDocs(List<Integer> tokens) throws SQLException {
		List<Integer> docs = new ArrayList<Integer>();
		List<Integer> tdoc;
		Set<Integer> setDocs = new TreeSet<Integer>();
		for (int i = 0; i < tokens.size(); i++) {
			tdoc = getDocsByTerm(tokens.get(i).intValue());
			if (!tdoc.isEmpty()) {
				for (int j = 0; j < tdoc.size(); j++) {
					setDocs.add(tdoc.get(j).intValue());
				}
			}
		}

		for (Iterator iterator = setDocs.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			docs.add(integer.intValue());
		}
		return docs;
	}

	/**
	 * @param form
	 * @return
	 * @throws SQLException
	 */
	private List<Integer> getDocsByTerm(int termId) throws SQLException {
		List<Integer> docs = new ArrayList<Integer>();
		String sql;
		// sql =
		// "SELECT distinct id FROM tfidf_ner where CHECKSUM(?) = cs_lower_term AND
		// LowerTerm = ?";
		// sql = "SELECT distinct id FROM tfidf where term_id = ?";
		sql = "SELECT distinct docid FROM passage_vectorilize where term_id = ?";

		prepared = con.prepareStatement(sql);
		prepared.setInt(1, termId);
		ResultSet rs = prepared.executeQuery();

		// STEP 5: Extract data from result set
		while (rs.next()) {
			// Retrieve by column name
			docs.add(rs.getInt("docid"));

		}
		// STEP 6: Clean-up environment
		rs.close();
		prepared.close();
		return docs;
	}

	/**
	 * @param integer
	 * @return
	 * @throws SQLException
	 */
	public int getDocLengthAll(int docId) throws SQLException {
		int length = 0;

		// connect();

		String sql = "SELECT docLength FROM ndocLength WHERE docid = ?";
		prepared = con.prepareStatement(sql);
		prepared.setInt(1, docId);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			length = rs.getInt("docLength");
		}

		rs.close();
		prepared.close();
		// con.close();

		return length;
	}

	/**
	 * @param integer
	 * @param form
	 * @return
	 * @throws SQLException
	 */
	public int getTermFrequencyAll(int docId, int termId) throws SQLException {
		String sqla = "SELECT term_count FROM passage_vectorilize WHERE term_id = " + termId + " AND  docid = " + docId;
		String sql = "SELECT term_count FROM passage_vectorilize WHERE docid = 1000 AND term_id = 1897";
		
//		prepared = con.prepareStatement(sql);
//		prepared.setInt(2, termId);
//		prepared.setInt(1, docId);

//		ResultSet rs = prepared.executeQuery();
		statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sqla);

		int count = 0;
		while (rs.next()) {
			// Retrieve by column name
			count = rs.getInt("term_count");
		}

		rs.close();
		prepared.close();
		// con.close();

		return count;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getDatasetLengthAll() throws SQLException {
		int datasetLength = 0;

		// connect();

		String sql = "select sum(term_count) as datasetLength from passage_vectorilize";

		prepared = con.prepareStatement(sql);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			datasetLength = rs.getInt("datasetLength");
		}

		rs.close();
		prepared.close();
		// con.close();

		return datasetLength;
	}

	/**
	 * @param form
	 * @return
	 * @throws SQLException
	 */
	public int getDocumentFrequencyAll(int termId) throws SQLException {
		int docFrequency = 0;

		// connect();

//		String sql = "SELECT docf FROM ndocFrequency where term_id = ?";
		String sqla = "SELECT docf FROM ndocFrequency where term_id = " + termId;
//		prepared = con.prepareStatement(sql);
//		prepared.setInt(1, termId);
//
//		ResultSet rs = prepared.executeQuery();
		statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sqla);

		while (rs.next()) {
			// Retrieve by column name
			docFrequency = rs.getInt("docf");
		}

		rs.close();
		prepared.close();
		// con.close();

		return docFrequency;
	}

	/**
	 * @param docId
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Passage getPassage(int docId) throws SQLException, InstantiationException, IllegalAccessException {

		connect();

		Passage passage = null;
		String sql = "SELECT id, title, `text` FROM passages where id = ?";
		prepared = con.prepareStatement(sql);
		prepared.setInt(1, docId);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			passage = new Passage(rs.getInt("id"), rs.getNString("title"), rs.getNString("text"));
		}

		rs.close();
		prepared.close();
		con.close();

		return passage;
	}

	/**
	 * @param tokens
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<Integer> getTermId(List<Word> tokens) throws SQLException, InstantiationException, IllegalAccessException {
		List<Integer> termIds = new ArrayList<Integer>();
		for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
			Word token = (Word) iterator.next();
			termIds.add(getTermId(token.getForm().toLowerCase()));
		}
		return termIds;
	}

	/**
	 * @param form
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public int getTermId(String term) throws SQLException, InstantiationException, IllegalAccessException {
		String sql = "select id from ndictionary where CONVERT(tterm, BINARY) = CONVERT(?, BINARY)";
		prepared = con.prepareStatement(sql);
		prepared.setNString(1, term.toLowerCase());

		ResultSet rs = prepared.executeQuery();
		int id = -1;
		while (rs.next()) {
			id = rs.getInt("id");
		}
		rs.close();
		prepared.close();
		return id;
	}
	
	public void insertPassages(List<Passage> psgs) throws SQLException {
		String sql = "INSERT INTO passages(id, `title`, `text`) VALUES (?,?,?)";
		prepared = con.prepareStatement(sql);

		for (Iterator iterator = psgs.iterator(); iterator.hasNext();) {
			Passage passage = (Passage) iterator.next();
			System.out.println(passage.getPassage());
			try {
				prepared.setInt(1, passage.getId());
				prepared.setNString(2, passage.getTitle());
				prepared.setNString(3, passage.getPassage());
				prepared.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		

		prepared.close();
	}
	
	public List<Passage> queryPassages() throws SQLException, InstantiationException, IllegalAccessException {
		List<Passage> passages = new ArrayList<Passage>();
		// STEP 4: Execute a query
		connect();
		statement = con.createStatement();
		String sql;
		sql = "SELECT `id`, `title`, `text` FROM passages";
		ResultSet rs = statement.executeQuery(sql);

		// STEP 5: Extract data from result set
		int count = 0;
		while (rs.next()) {
			count++;
//			 Retrieve by column name
//			int id = rs.getInt("id");
//			String title = rs.getString("title");
//			String passage = rs.getString("text");
//			passages.add(new Passage(id, title, passage));
			if (count > 176503 ) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String passage = rs.getString("text");
				passages.add(new Passage(id, title, passage));
			}
//			if (count > 150000) {
//				break;
//			}

		}
		// STEP 6: Clean-up environment
		rs.close();
		statement.close();
		disconnect();
		return passages;
	}

	public void insertDFTable(int id, int docId, List<MyWord> wordList)
			throws SQLException, InstantiationException, IllegalAccessException {
		// STEP 4: Execute a query
		connect();
//		String sql = "INSERT INTO tfidf_ner(id, term, term_count) values(?,?,?)";
		String sql = "INSERT INTO passage_vectorilize(`id`, `docid`, `term`, `term_count`) values(?,?,?,?)";
		prepared = con.prepareStatement(sql);

		@SuppressWarnings("unused")
		int count = 1;
		for (Iterator iterator = wordList.iterator(); iterator.hasNext(); count++) {
			MyWord myWord = (MyWord) iterator.next();
			if(myWord.getTerm().length() < 100){
				// Insert to DF table
				prepared.setInt(1, id + count);
				prepared.setInt(2, docId);
				prepared.setNString(3, myWord.getTerm().toLowerCase());
				prepared.setInt(4, myWord.getCount());
				prepared.executeUpdate();
			}
			
		}

		prepared.close();
		disconnect();
	}
	
	public void updateTermId(List<Term> terms) throws SQLException, InstantiationException, IllegalAccessException {
		connect();
		String sql = "UPDATE passage_vectorilize set term_id = ? where cs_term = CHECKSUM(?) AND term = ?";
		prepared = con.prepareStatement(sql);

		
		int count = 0;
		for (Iterator iterator = terms.iterator(); iterator.hasNext();count++) {
			System.out.println(count);
			Term term = (Term) iterator.next();
			// Insert to DF table
			prepared.setInt(1, term.getId());
			prepared.setNString(2, term.getTerm().toLowerCase());
			prepared.setNString(3, term.getTerm().toLowerCase());
			prepared.executeUpdate();
		}

		prepared.close();
		disconnect();
	}

	public List<PassVec> getNullIDTermList() throws InstantiationException, IllegalAccessException, SQLException {
		List<PassVec> pv = new ArrayList<PassVec>();
		
		statement = con.createStatement();
		String sql;
		sql = "SELECT `id`, `term` FROM passage_vectorilize where term_id = -1";
		ResultSet rs = statement.executeQuery(sql);

		// STEP 5: Extract data from result set
		int count = 0;
		int id = -1;
		String term = "";
		while (rs.next()) {
			term = rs.getNString("term");
			id = rs.getInt("id");
			pv.add(new PassVec(id, term));
		}
		// STEP 6: Clean-up environment
		rs.close();
		statement.close();
		
		return pv;
	}

	public void updateTermId(int id, int term_id) throws SQLException, InstantiationException, IllegalAccessException {
		String sql = "UPDATE passage_vectorilize set term_id = ? where id = ?";
		prepared = con.prepareStatement(sql);

		prepared.setInt(1, term_id);
		prepared.setInt(2, id);
		prepared.executeUpdate();
		prepared.close();
	}
	
	public HashMap<String, Integer> getDictionary() throws SQLException{
		HashMap<String, Integer> dics = new HashMap<>();
		statement = con.createStatement();
		String sql;
		sql = "SELECT `id`, `tterm` FROM ndictionary";
		ResultSet rs = statement.executeQuery(sql);

		// STEP 5: Extract data from result set
		int count = 0;
		int id = -1;
		String term = "";
		while (rs.next()) {
			term = rs.getNString("tterm");
			id = rs.getInt("id");
			dics.put(term, id);
		}
		// STEP 6: Clean-up environment
		rs.close();
		statement.close();
		return dics;
	}

	public void addTermToDictionary(int id, String term) throws SQLException {
		String sql = "INSERT INTO ndictionary(id, tterm) VALUES (?,?)";
		prepared = con.prepareStatement(sql);

		prepared.setInt(1, id);
		prepared.setNString(2, term);
		prepared.executeUpdate();
		prepared.close();
	}
}
