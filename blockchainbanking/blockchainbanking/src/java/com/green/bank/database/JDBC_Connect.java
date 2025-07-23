package com.green.bank.database;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Connect {
	private Connection connection = null;

	public Connection getConnection() throws SQLException {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blockchainbanking", "root", "root");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your  JDBC Driver?");
			e.printStackTrace();

		}

		return connection;

	}

}
