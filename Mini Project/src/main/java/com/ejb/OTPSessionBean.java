package com.ejb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Session Bean implementation class OTPSessionBean
 */
@Stateless
@LocalBean
public class OTPSessionBean implements OTPSessionBeanRemote, OTPSessionBeanLocal {

    LocalDateTime otpSentTime;
	/**
     * Default constructor. 
     */
    public OTPSessionBean() {
        
    }
    
    // get otp from cloud service
    @Override
	public String getOTPFromCloud() {
		String otp = "";
		try {
			// Create a URL object for the web service endpoint
	        URL url = new URL("https://fastidious-pothos-4bd0f8.netlify.app/.netlify/functions/generateOTP");

	        // Open a connection to the web service
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        // Set the request method to GET
	        con.setRequestMethod("GET");

	        // Read the response
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	        otp = content.toString();
	        // Close the connection
	        con.disconnect();
			}catch(Exception e) {
				System.out.println("Something went wrong!");
			}
		return otp;
	}
    
    // send otp using email but failed (show on console instead)
    @Override
	public void sendEmail(int id,int otp) {
		/*String email = "";
		try {
			ConnectDB db = new ConnectDB();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT email FROM accounts where id=?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					email =  rs.getString("email");
					System.out.println(email);
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
		 Properties properties = new Properties();
		  properties = System.getProperties();
		  properties.put("mail.smtp.host", "smtp.gmail.com");
		  properties.put("mail.smtp.port", "25");
		  properties.put("mail.smtp.auth", "true");
		  properties.put("mail.smtp.ssl.enable", "true");
	
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	        	   protected PasswordAuthentication getPasswordAuthentication() {
	        		    return new PasswordAuthentication("......", 
	        		    		".....");
	        		   }
	        		  });
	        session.setDebug(true);
	        
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("......."));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	            message.setSubject("Top Up Payment");
	            message.setText("Dear customer!This is an verification email"
	            		+ "from Bank A. Your OTP: " + otp);

	            Transport.send(message);
	            System.out.println("Email sent successfully.");*/
    	otpSentTime = LocalDateTime.now();
    	System.out.println(otpSentTime);
    	System.out.println("Dear Customer!");
		System.out.println("This is an Email from Bank A");
		System.out.println("Your OTP: " + otp);
		
	}

	//store otp in database
    @Override
    public void storeOTP(int otp, int id) {
		try {
			ConnectDB db = new ConnectDB();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = db.createConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET otp = ? WHERE id = ?");
			stmt.setInt(1, otp);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
    
  //get otpSentTime 
    @Override
    public LocalDateTime getOtpSentTime() {
		return this.otpSentTime;
	}

}
