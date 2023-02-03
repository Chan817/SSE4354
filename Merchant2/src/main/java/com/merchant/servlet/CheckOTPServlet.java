package com.merchant.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checkOTPServlet
 */
@WebServlet("/checkOTPServlet")
public class CheckOTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOTPServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accStr = request.getParameter("acc");
        int acc=0;
        if(accStr!=null && !accStr.equals(""))
        	acc=Integer.parseInt(accStr);
        
        String amountStr = request.getParameter("amount");
        double amount=0.0;
        if(amountStr!=null && !amountStr.equals(""))
        	amount=Double.valueOf(amountStr);
        
        String otpStr = request.getParameter("otp");
        int otp=0;
        if(otpStr!=null && !otpStr.equals(""))
        	otp=Integer.parseInt(otpStr);
        
        if (checkOTP(acc, otp)) {
        	updateAccBalance(acc, amount);
        	request.setAttribute("correctMessage", "Transaction Successful");
			request.getRequestDispatcher("requestOTP.jsp").forward(request, response);
        }else {
        	request.setAttribute("errorMessage", "Wrong OTP");
			request.getRequestDispatcher("requestOTP.jsp").forward(request, response);
        }
	}

	private void updateAccBalance(int acc, double amount) {
		try {
			URL url = new URL("http://localhost:8080/Mini_Project/updateAccBalance?acc="+acc+"&amount=" + amount);
			// Open a connection to the web service
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        // Set the request method to POST
	        con.setRequestMethod("POST");

	        // Read the response
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	       
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}

	private boolean checkOTP(int acc, int otp) {
		boolean OTPcorrect = false;
		try {
			URL url = new URL("http://localhost:8080/Mini_Project/checkOTP?acc=" + acc +"&otp="+otp);
			// Open a connection to the web service
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        // Set the request method to POST
	        con.setRequestMethod("POST");

	        // Read the response
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	        String c = content.toString();
	        OTPcorrect = Boolean.parseBoolean(c);
			}catch(Exception e) {
				e.printStackTrace();
			}
		return OTPcorrect;
	}

}
