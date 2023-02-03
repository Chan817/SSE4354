<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Before proceed..</title>
</head>
<body>
<%
    String acc = (String) session.getAttribute("acc");
    String amount = (String) session.getAttribute("amount"); 
  
%>
<h1> Please enter your OTP</h1>
<form action="<%=request.getContextPath()%>/checkOTPServlet" method = "POST">
<input type="hidden" name="acc" value="<%=acc%>">
<input type="hidden" name="amount" value="<%=amount%>">
<label for="otp">OTP:</label>
  <input type="text" id="otp" name="otp"><br><br>
<input type = "submit" value = "submit">
</form>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
	String correctMessage = (String) request.getAttribute("correctMessage");
    if(errorMessage != null){
        out.println("<p style='color:red'>" + errorMessage + "</p>");
    }
    if(correctMessage != null){
        out.println("<p style='color:green'>" + correctMessage + "</p>");
    }
%>
</body>
</html>