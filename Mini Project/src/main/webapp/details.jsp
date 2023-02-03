<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Details</title>
</head>
<body>
<h1>Account Details</h1>
      
      <ul>
         <li><p><b>Name:</b>
            ${account.name}
         </p></li>
         <li><p><b>Account Number:</b>
            ${account.id}
         </p></li>
         <li><p><b>Balance:</b>
            ${account.balance}
         </p></li>
      </ul>
</body>
</html>