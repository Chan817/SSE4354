package com.ejb;

import java.sql.*;

import javax.ws.rs.*;

/** create endpoint/web services for merchant to connect**/
@Path("/")
public class Service {
	
	// check whether the account exist in database, will return true or false
	@Path("/checkAcc")
	@POST
	@Produces("text/plain")
	public boolean checkAccount(@QueryParam("acc")int id, @QueryParam("pin")int pin) {
		boolean validAcc = false;
		try {
			// create database connection
			ConnectDB db = new ConnectDB();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			String query = "SELECT * FROM accounts WHERE id = ? and online_pin = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1,id);
			stmt.setInt(2,  pin);
			ResultSet rs = stmt.executeQuery();
			 if (rs.next()) {
	               	validAcc = true;
	          }
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print("Something went wrong");
		}
		return validAcc;
	}
	
	// check balance of particular account, make sure the amount entered for 
	// top up is not larger than the account balance, will return true or false
	@Path("/checkBalance")
	@POST
	@Produces("text/plain")
	public boolean checkBalance(@QueryParam("acc")int id, @QueryParam("amount")double amount) {
		boolean enoughBalance = false;
		String balanceStr;
		double balance;
		try {
			// create database connection
			ConnectDB db = new ConnectDB();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			//get balance amount from database
			String query = "SELECT balance FROM accounts WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			 if (rs.next()) {
				 balanceStr = rs.getString("balance");
				 balance = Double.valueOf(balanceStr);
				 if (balance >= amount)
					 enoughBalance = true;
	          }
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print("Something went wrong");
		}
		return enoughBalance;
	}
	
	// update the account balance if the transaction is successful
	@POST
	@Path("/updateAccBalance")
	public void updateAccBalance(@QueryParam("acc")int id, @QueryParam("amount")double amount) {
		String balanceStr;
		double balance = 0.0;
		try {
			// create database connection
			ConnectDB db = new ConnectDB();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			//get balance amount from database
			String query = "SELECT balance FROM accounts WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			 if (rs.next()) {
				 balanceStr = rs.getString("balance");
				 balance = Double.valueOf(balanceStr);
				 balance = balance - amount;
	          }
			 String updateSQL = "UPDATE accounts SET balance = ? WHERE id = ?";
			 stmt = conn.prepareStatement(updateSQL);
			 stmt.setDouble(1, balance);
			 stmt.setInt(2, id);
			 stmt.executeUpdate();
			 conn.close();			 
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print("Something went wrong");
		}
	}

}
