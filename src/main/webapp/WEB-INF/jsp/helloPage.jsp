<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>helloPage</title>
</head>
<body>
<h3>
    Welcome
    <h1>login</h1>
    <c:if test="${not empty param.logInfo}" var="loginError">
        <p>Please try again</p> <br>
    </c:if>
    <form action=Controller method="get">
        <input type="hidden" name="command" value="loginationPage">
        <p>
            User login: <input type="text" name="login"/>
        </p>
        <p>
            User password: <input type="password" name="password"/>
        </p>
        <input type="submit" value="Enter"/>
    </form>
    <br>or you can <a href="Controller?command=goToRegistrationPage">register</a>
</h3>
</body>
</html>