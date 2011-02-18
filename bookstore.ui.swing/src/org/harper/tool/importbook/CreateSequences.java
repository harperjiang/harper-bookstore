package org.harper.tool.importbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CreateSequences {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(
		// "jdbc:mysql://localhost:3306/bookstore", "root", "jieninan");
				"jdbc:mysql://harper.gotoip1.com:3306/harper", "harper", "jieninan");
		con.setAutoCommit(false);
		String[] tables = new String[] { "order_common", "order_item",
				"order_delivery", "order_delivery_item", "profile_customer",
				"profile_supplier", "store_site", "store_entry",
				"store_transfer", "store_trans_item", "profile_book",
				"library_record", "library_record_item", "library_entry",
				"profile_borrower", "order_disp_item" };
		PreparedStatement stmt = con
				.prepareStatement("insert into sys_sequence(table_name,seq) values(?,?)");
		for (String table : tables) {
			stmt.setString(1, table);
			stmt.setInt(2, 0);
			stmt.addBatch();
		}
		stmt.executeBatch();

		stmt.execute("insert into sys_num_gen(type,prefix,current,length) values('PO','PO',1,10)");
		stmt.execute("insert into sys_num_gen(type,prefix,current,length) values('SO','SO',1,10)");
		stmt.execute("insert into sys_num_gen(type,prefix,current,length) values('TR','TR',1,10)");
		stmt.execute("insert into sys_num_gen(type,prefix,current,length) values('BR','BR',1,10)");

		stmt.close();

		con.commit();
		con.close();
	}

}
