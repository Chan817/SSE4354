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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MerchantServlet
 */
@WebServlet("/MerchantServlet")
public class MerchantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MerchantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get values from jsp
		String acc = request.getParameter("acc");
        String pin = request.getParameter("pin");
        String amount = request.getParameter("amount");
        request.setAttribute("acc", acc);
        request.setAttribute("amount", amount);
        HttpSession session = request.getSession();
		session.setAttribute("acc", acc);
		session.setAttribute("amount", amount);
		
        if (isValidAccount(acc, pin)) {
        	if (enufMoney(acc,amount)) {
        		sendOTP(acc);
        		request.getRequestDispatcher("requestOTP.jsp").forward(request, response);
        	}else {
        		// print error message if top up amount entered is more than
        		// account balance
        		request.setAttribute("errorMessage", "Error: Not enough balance!");
	        	request.getRequestDispatcher("topup.jsp").forward(request, response);
        	}
        }else {
        	// print error message if the account does not exist in database
        	request.setAttribute("errorMessage", "Error: Account not found!");
        	request.getRequestDispatcher("topup.jsp").forward(request, response);
        }
	}

	// call web service from bank to send otp to user 
	private void sendOTP(String acc) {
		try {
			URL url = new URL("http://localhost:8080/Mini_Project/sendOTP?acc="+acc);
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

	// call web service from bank to ensure the account balance is more than 
	// the top up amount entered, will return true or false
	private boolean enufMoney(String acc, String amount) {
		boolean enoughBalance = false;
		try {
			URL url = new URL("http://localhost:8080/Mini_Project/checkBalance?acc="+acc+"&amount="+amount);
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
	        enoughBalance = Boolean.parseBoolean(c);
	        
			}catch(Exception e) {
				e.printStackTrace();
			}
		return enoughBalance;
	}

	// call web service to check the account is valid
	private boolean isValidAccount(String acc, String pin) {
		boolean validAcc = false;
		try {
			URL url = new URL("http://localhost:8080/Mini_Project/checkAcc?acc="+acc+"&pin="+pin);
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
	        validAcc = Boolean.parseBoolean(c);
	        
			}catch(Exception e) {
				e.printStackTrace();
			}
		return validAcc;
	}
}
