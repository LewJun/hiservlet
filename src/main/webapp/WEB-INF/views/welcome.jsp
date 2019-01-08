<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Welcome</title>
</head>
<body>
<h2>Welcome!</h2>
<%=new Date()%>
<br/>
<a href="logoutServlet">Logout</a>
<br/>
<a href="redirectpageServlet">redirectpageServlet</a>
</body>
</html>