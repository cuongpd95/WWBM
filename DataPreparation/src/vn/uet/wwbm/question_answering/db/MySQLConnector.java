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
import java.util.List;
import java.util.Properties;

import vn.uet.wwbm.question_answering.entities.Passage;
import vn.uet.wwbm.question_answering.helpers.PropertyLoader;

/**
 * @author "PhanDoanCuong"
 *
 */
public class MySQLConnector {
	private static MySQLConnector instance;
	private Connection con;
	private Statement statement;
	@SuppressWarnings("unused")
	private PreparedStatement prepared;
	private PropertyLoader loader;
	private Properties prop;

	/**
	 * design pattern singleton to get object JDBCManager.
	 * 
	 * @return Instance of JDBCManager
	 */
	public static MySQLConnector getInstance() {
		if (null == instance) {
			instance = new MySQLConnector();
		}
		return instance;
	}

	/**
	 * Constructor for class JDBCManager. Not allow Object use JDBCManager can
	 * create new JDBCManager().
	 */
	private MySQLConnector() {
		loader = new PropertyLoader();
		prop = loader.getpropObject("mysql_config.properties");
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
			// System.out.println(Constants.ERROR_CONNECT_DATABASE_STR);
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			// System.out.println(Constants.ERROR_DRIVER_NAME);
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
			// System.out.println(Constants.ERROR_DISCONNECT_DATABASE_STR);
			System.out.println(e);
		}
	}

	/**
	 * @return
	 * @throws SQLException 
	 */
	public List<Passage> queryMySQLPassages() throws SQLException {
		List<Passage> passages = new ArrayList<Passage>();
		// STEP 4: Execute a query
		connect();
		statement = con.createStatement();
		String sql;
		sql = "SELECT id, title, text FROM passages";
		ResultSet rs = statement.executeQuery(sql);

		// STEP 5: Extract data from result set
		while (rs.next()) {
			// Retrieve by column name
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String passage = rs.getString("text");
			passages.add(new Passage(id, title, passage));
		}
		// STEP 6: Clean-up environment
		rs.close();
		statement.close();
		con.close();
		return passages;
	}
}
