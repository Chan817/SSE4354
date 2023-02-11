package com.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import javax.ws.rs.*;

import com.ejb.OTPSessionBean;

/** create endpoint/web services for merchant to connect, this class focuses on
    the web services used in OTP processing**/
@Path("/")
public class OTPService {
	
	@javax.ejb.EJB
	private OTPSessionBean otpSession;
	
	//get otp from cloud service, send otp to the console(email not successful)
	//and store otp in database
	@POST
	@Path("/sendOTP")
	public void processOTP(@QueryParam("acc")int id) {
		String otpStr = otpSession.getOTPFromCloud();
		String otpClean = otpStr.substring(1, otpStr.length()-1);
		int otp = Integer.valueOf(otpClean);
		otpSession.sendEmail(id, otp);
		otpSession.storeOTP(otp,id);
	}
	
	// check whether the otp entered by user is correct, return true or false
	@POST
	@Path("/checkOTP")
	@Produces("text/plain")
	public boolean checkOTP(@QueryParam("acc")int id, @QueryParam("otp")int otp) {
		boolean correctOTP = false;
		String otpStr;
		int otpdb;
		try {
			// create database connection
			ConnectDB db = new ConnectDB();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			//get otp from database
			String query = "SELECT otp FROM accounts WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			//set expiry time for OTP
			LocalDateTime expiryTime = otpSession.getOtpSentTime().plusMinutes(5);
			
			//current time
			LocalDateTime now = LocalDateTime.now();
			System.out.println("otp sent: " + otpSession.getOtpSentTime());
			System.out.println("expiry: " + expiryTime);
			System.out.println("now: " + now);
			 if (rs.next()) {
				 otpStr = rs.getString("otp");
				 otpdb = Integer.valueOf(otpStr);
				 if (otp == otpdb && now.isBefore(expiryTime))
					 correctOTP = true;
	          }
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print("Wrong otp");
		}
		return correctOTP;
	}

}
