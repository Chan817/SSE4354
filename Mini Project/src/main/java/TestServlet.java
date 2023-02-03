

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejb.Account;
import com.ejb.AccountSessionBean;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@javax.ejb.EJB
	private AccountSessionBean account;
	Account newAccount;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	//acc from jsp
    	String accStr = request.getParameter("acc");
        int acc=0;
        if(accStr!=null && !accStr.equals(""))
        	acc=Integer.parseInt(accStr);
        
        //pin from jsp
        String pinStr = request.getParameter("pin");
        int pin=0;
        if(pinStr!=null && !pinStr.equals(""))
        	pin=Integer.parseInt(pinStr);
       
        newAccount = account.getAccount(acc, pin);
        if (newAccount != null) {
        	request.setAttribute("account", newAccount);
            request.getRequestDispatcher("/details.jsp").forward(request, response);
        }else {
        	// if account cannot be found, print error message
        	request.setAttribute("errorMessage", "Error: Account not found!");
        	request.getRequestDispatcher("/OnlineBanking.jsp").forward(request, response);
        }
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
