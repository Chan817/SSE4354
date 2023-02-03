package com.ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class MyEJB
 */
@Stateless
@LocalBean
public class AccountSessionBean implements AccountSessionBeanRemote, AccountSessionBeanLocal {
	private Connection connection;

    //connect to database 
	public AccountSessionBean() {
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@fsktmdbora.upm.edu.my:1521:fsktm", "nky", "nky");
    	} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	//get account details using id and pin entered
	@Override
	public Account getAccount(int id, int pin) {
		 String sql = "SELECT id, name, balance FROM accounts WHERE id = ? AND online_pin = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setInt(1, id);
	            pstmt.setInt(2, pin);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return new Account(rs.getInt("id"), rs.getString("name"), rs.getString("balance"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	}

}
