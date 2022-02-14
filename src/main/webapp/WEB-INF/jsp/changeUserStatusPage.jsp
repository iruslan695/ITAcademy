<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 09.02.2022
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>ChangeUserStatusPage</title>
</head>
<body>
<form action=Controller method="get">
    <input type="hidden" name="command" value="changeUserStatusPage">
    <p>
        <c:if test="${not empty param.changeResult}" var="changeResult">
            <c:if test="${param.changeResult eq 'success'}">
                Publisher successfully added <br>
            </c:if>
            <c:if test="${param.changeResult eq 'error'}">
                Something went wrong, please try again <br>
            </c:if>
        </c:if>
        User login: <input type="text" name="login"/> <br>
        Choose new user status :<select name="role">
        <option value="reader">reader</option>
        <option value="publisher">publisher</option>
        <option value="admin">admin</option>
    </select>
    </p>
    <input type="submit" value="Enter"/>
    <a href="Controller?command=accountPage">To account page</a>
</form>
</body>
</html>
