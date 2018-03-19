/**
 * 
 */
package vn.uet.wwbm.question_answering.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;
import vn.uet.wwbm.question_answering.entities.MyWord;
import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.entities.TFIDFNER;
import vn.uet.wwbm.question_answering.entities.Term;
import vn.uet.wwbm.question_answering.helpers.PropertyLoader;
import vn.uet.wwbm.question_answering.helpers.StringUtils;

/**
 * @author "PhanDoanCuong"
 *
 */
public class DBHelpers {

	private static DBHelpers instance;
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
	public static DBHelpers getInstance() {
		if (null == instance) {
			instance = new DBHelpers();
		}
		return instance;
	}

	/**
	 * Constructor for class JDBCManager. Not allow Object use JDBCManager can
	 * create new JDBCManager().
	 */
	private DBHelpers() {
		loader = new PropertyLoader();
		prop = loader.getpropObject("config.properties");
	}

	/**
	 * Connect database with CONNECTION_URL.
	 * 
	 * @return boolean
	 */
	private boolean connect() {
		try {
			// check connect is null or close
			if (null == con || con.isClosed()) {
				// register the driver class with DriverManager
				Class.forName(prop.getProperty("drivername"));
				// open connect
				con = DriverManager.getConnection(prop
						.getProperty("connectionString")
				// ,prop.getProperty("uid"), prop.getProperty("pwd")
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
	 * @return
	 * @throws SQLException
	 * 
	 */
	public List<Passage> queryPassages() throws SQLException {
		List<Passage> passages = new ArrayList<Passage>();
		// STEP 4: Execute a query
		connect();
		statement = con.createStatement();
		String sql;
		sql = "SELECT id, title, [text] FROM passages";
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
			if (count > 150001 ) {
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

	/**
	 * @param docId
	 * @param wordList
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public void insertDFTable(int docId, List<MyWord> wordList)
			throws SQLException {
		// STEP 4: Execute a query
		connect();
//		String sql = "INSERT INTO tfidf_ner(id, term, term_count) values(?,?,?)";
		String sql = "INSERT INTO passage_vectorilize(docid, term, term_count) values(?,?,?)";
		prepared = con.prepareStatement(sql);

		@SuppressWarnings("unused")
		int count = 1;
		for (Iterator iterator = wordList.iterator(); iterator.hasNext(); count++) {
			MyWord myWord = (MyWord) iterator.next();
			if(myWord.getTerm().length() < 100){
				// Insert to DF table
				prepared.setInt(1, docId);
				prepared.setNString(2, myWord.getTerm().toLowerCase());
				prepared.setInt(3, myWord.getCount());
				prepared.executeUpdate();
			}
			
		}

		prepared.close();
		disconnect();
	}

	public void insertToSQLServer(List<Passage> passages) throws SQLException {
		connect();
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
		disconnect();
	}

	public void removeId(List<Integer> ids) throws SQLException {
		connect();
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
		disconnect();
	}

	public void removeStopWord(List<String> stopwords) throws SQLException {
		connect();
		statement = con.createStatement();
		String sql = "DELETE FROM tfidf WHERE lower(term) = ?";
		prepared = con.prepareStatement(sql);

		for (Iterator iterator = stopwords.iterator(); iterator.hasNext();) {
			String passage = (String) iterator.next();
			System.out.println(passage);
			// Insert to DF table
			prepared.setNString(1,
					passage.trim().toLowerCase().replace(" ", "_"));
			prepared.executeUpdate();
		}

		prepared.close();
		disconnect();
	}

	/**
	 * @param docId
	 * @param term
	 * @return
	 * @throws SQLException
	 */
	public int getTermFrequency(int docId, String term) throws SQLException {
		int count = 0;

		connect();

		String sql = "SELECT term_count FROM tfidf_hashtb WHERE CHECKSUM(?) = cs_lower_term AND LowerTerm = ? AND id = ?";
		prepared = con.prepareStatement(sql);
		prepared.setNString(1, term.toLowerCase());
		prepared.setNString(2, term);
		prepared.setInt(3, docId);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			count = rs.getInt("term_count");
		}

		rs.close();
		prepared.close();
		con.close();

		return count;
	}

	/**
	 * @param docId
	 * @return
	 * @throws SQLException
	 */
	public int getDocLength(int docId) throws SQLException {
		int length = 0;

		connect();

		String sql = "SELECT sum(term_count) as length FROM tfidf WHERE id = ?";
		prepared = con.prepareStatement(sql);
		prepared.setInt(1, docId);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			length = rs.getInt("length");
		}

		rs.close();
		prepared.close();
		con.close();

		return length;
	}

	/**
	 * @param term
	 * @return
	 * @throws SQLException
	 */
	public int getDocumentFrequency(String term) throws SQLException {
		int docFrequency = 0;

		connect();

		String sql = "SELECT count(*) as docf FROM (select distinct id from tfidf_hashtb where CHECKSUM(?) = cs_lower_term AND LowerTerm = ?) as dt";
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
		con.close();

		return docFrequency;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getNumberOfDocument() throws SQLException {
		int numberOfDocument = 0;

		connect();

		String sql = "select count(id) as nDoc from passages";

		prepared = con.prepareStatement(sql);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			numberOfDocument = rs.getInt("nDoc");
		}

		rs.close();
		prepared.close();
		con.close();

		return numberOfDocument;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getDatasetLength() throws SQLException {
		int datasetLength = 0;

		connect();

		String sql = "select sum(term_count) as datasetLength from tfidf";

		prepared = con.prepareStatement(sql);

		ResultSet rs = prepared.executeQuery();

		while (rs.next()) {
			// Retrieve by column name
			datasetLength = rs.getInt("datasetLength");
		}

		rs.close();
		prepared.close();
		con.close();

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
	 * @param pipeline
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<TFIDFNER> getTFIDFNERData(VnCoreNLP pipeline)
			throws SQLException, IOException {
		connect();

		List<TFIDFNER> datas = new ArrayList<TFIDFNER>();
		Annotation annotation = new Annotation("");
		statement = con.createStatement();
		String sql = "SELECT id, term, term_count  FROM tfidf_hashtb order by id";

		ResultSet rs = statement.executeQuery(sql);

		int id;
		String term;
		int term_count;
		List<Word> words;
		Word word;
		while (rs.next()) {
			// Retrieve by column name
			id = rs.getInt("id");
			System.out.println(id);
			term = rs.getNString("term");
			term_count = rs.getInt("term_count");
			annotation.setRawText(term.replace("_", " "));
			pipeline.annotate(annotation);
			words = annotation.getWords();

			for (int i = 0; i < words.size(); i++) {
				word = words.get(i);
				if (StringUtils.isEntity(word.getNerLabel())) {
					// System.out.println(word.getForm());
					datas.add(new TFIDFNER(id, term, term_count));
				}
			}

		}

		rs.close();
		statement.close();
		disconnect();

		return datas;
	}

	/**
	 * @param terms
	 * @throws SQLException
	 */
	public void updateTFIDFNER(List<TFIDFNER> terms) throws SQLException {
		connect();
		statement = con.createStatement();
		String sql = "INSERT INTO tfidf_ner(id, term, term_count) values(?,?,?)";
		prepared = con.prepareStatement(sql);
		int count = 1;

		for (Iterator iterator = terms.iterator(); iterator.hasNext(); count++) {
			TFIDFNER term = (TFIDFNER) iterator.next();
			// Insert to DF table
			System.out.println(term.getId());
			prepared.setInt(1, term.getId());
			prepared.setNString(2, term.getTerm());
			prepared.setInt(3, term.getTerm_count());
			prepared.executeUpdate();
		}

		prepared.close();
		disconnect();

	}

	/**
	 * @return
	 * @throws SQLException 
	 */
	public List<Term> getListTerm() throws SQLException {
		connect();

		List<Term> datas = new ArrayList<Term>();
		
		statement = con.createStatement();
		String sql = "SELECT id, tterm  FROM ndictionary";

		ResultSet rs = statement.executeQuery(sql);

		int id;
		String term;
		int count = 0;
		while (rs.next()) {
			// Retrieve by column name
			id = rs.getInt("id");
			term = rs.getNString("tterm");
			datas.add(new Term(id, term));
		}

		rs.close();
		statement.close();
		disconnect();

		return datas;
	}

	/**
	 * @param terms
	 * @throws SQLException 
	 */
	public void updateTermId(List<Term> terms) throws SQLException {
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

}
