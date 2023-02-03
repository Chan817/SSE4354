<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Online Banking</title>

</head>
<body>

<h1>Welcome</h1>  
<form action="<%=request.getContextPath()%>/TestServlet" method = "POST">
  <label for="acc">Account Number:</label>
  <input type="text" id="acc" name="acc"><br><br>
  <label for="pin">Online PIN:</label>
  <input type="text" id="pin" name="pin"><br><br>
  <input type="submit" value="Submit">
</form>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if(errorMessage != null){
        out.println("<p style='color:red'>" + errorMessage + "</p>");
    }
%>
</body>
</html>