/**
 * 
 */
package vn.uet.wwbm.datapreparation;

import java.sql.SQLException;

/**
 * @author "PhanDoanCuong"
 *
 */
public class CopyFromMySQL {

	public static void main(String[] args) throws SQLException {
		TFIDFPreparation pre = new TFIDFPreparation();
		pre.copyMySQLToSQLServer();
	}
}
