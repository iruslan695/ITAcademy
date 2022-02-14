<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 11.02.2022
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add publisher information</title>
</head>
<body>
<form action=Controller method="get">
    <input type="hidden" name="command" value="changeUserStatusPage">
    <input type="hidden" name="login" value="${param.login}">
    <input type="hidden" name="role" value="publisher">
    <p>Please add company information</p>
    <p>Publisher name: <input type="text" name="pubName"/></p>
    <p>Publisher royalty: <input type="number" name="pubRoyalty" min="1" max="90"/></p>
    <input type="submit" value="Enter"/>
</form>
<a href="Controller?command=accountPage">To account page</a>
</body>
</html>
