package com.greenhub.utils;

import java.sql.*;
import com.greenhub.utils.*;

// SQLite Database Connection.
// Connected successfully!!

// 已经写完的就这样吧，屎山就屎山吧，不敢乱动，后续的话看看尽量做一下解耦合。
public class DBUtils {
	
	private static utils db_util = new utils();
	public static void main(String[] args) {
		Connection connection = null;
		try {
			// Load JDBC Driver.
			Class.forName("org.sqlite.JDBC");
			
			// Load SQL DB File.
			String dbFile = "jdbc:sqlite:Database/database.db";
			
			// Connect to the SQL Database.
			connection = DriverManager.getConnection(dbFile);
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from db_table");
			
			// process results.
			while (resultSet.next()) {
				System.out.println(resultSet.getString("value"));
			}
		} catch (Exception e) {
			db_util.generateLogs_Window(e);
		} finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            	db_util.generateLogs_Window(e);
            }
        }
		System.out.println("Sqlite Connection.");
	}
}
