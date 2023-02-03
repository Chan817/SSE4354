<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mobile Top-up</title>
</head>
<body>
<h1>Want to buy top-ups? Please enter..</h1>  
<form action="<%=request.getContextPath()%>/MerchantServlet" method = "POST">

  <label for="acc">Bank Account Number:</label>
  <input type="text" id="acc" name="acc"><br><br>
  <label for="pin">Online PIN:</label>
  <input type="text" id="pin" name="pin"><br><br>
  <label for="amount">Top-Up Amount:</label>
  <input type="text" id="amount" name="amount"><br><br>
  <input type="submit" value="Pay">
</form>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if(errorMessage != null){
        out.println("<p style='color:red'>" + errorMessage + "</p>");
    }
%>
</body>
</html>