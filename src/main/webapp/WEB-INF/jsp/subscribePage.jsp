<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 25.02.2022
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Subscribe Page</title>
</head>
<body>
<h1><c:out value="${requestScope.journalName}"/></h1>
<h2>Price: <c:out value="${requestScope.journalPrice}"/> $/month</h2>
<h2>Your balance: <c:out value="${requestScope.balance}"/> $</h2>
<form action=Controller method="get">
    <input type="hidden" name="command" value="subscribePage">
    <input type="hidden" name="journalName" value="${requestScope.journalName}">
    <h2>
        Period in months: <input type="number" name="subDuration"/>
    </h2>
    <input type="submit" value="Enter"/>
</form>
<a href="Controller?command=accountPage">To account page</a>
</body>
</html>
