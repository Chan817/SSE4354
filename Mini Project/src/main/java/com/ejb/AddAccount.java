package com.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddAccount {

	public static void main(String[] args) {
		//addAcc("CHAN", "30000", "chanenghui2001@gmail.com", "123456");
		show();

	}

	//show details for every account in console
	private static void show() {
		try {
			// create database connection
			ConnectDB db = new ConnectDB();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			String query = "SELECT * FROM accounts";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {
				 	String id = rs.getString("id");
				    System.out.print(id);
				    String name = rs.getString("name");
				    System.out.print(name);
				    String balance = rs.getString("balance");
				    System.out.println(balance);
				    String otp = rs.getString("otp");
				    System.out.println(otp);
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	//add account in database
	private static void addAcc(String name, String balance, String email, String pin) {
		try {
			// create database connection
			ConnectDB db = new ConnectDB();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			//get balance amount from database
			String insertSQL = "INSERT INTO accounts (name,balance,email,online_pin) VALUES (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(insertSQL);
			stmt.setString(1, name);
			stmt.setString(2, balance);
			stmt.setString(3, email);
			stmt.setString(4, pin);
			
			// Execute the statement
            stmt.executeUpdate();

            // Close the connection
            conn.close();
            System.out.print("new account added");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print("Something went wrong");
		}
		
	}

}
