<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 09.02.2022
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>TopUpBalance</title>
</head>
<body>
<c:if test="${param.topUpResult eq 'ok'}" var="isOk">
    <p>Balance top upped successfully</p> <br>
</c:if>
<c:if test="${param.topUpResult eq 'error'}" var="isOk">
    <p>Something went wrong, please try again</p> <br>
</c:if>
<p>Please, enter value($).</p>
<form action="Controller" method="get">
    <input type="hidden" name="command" value="topUpBalancePage">
        <p>Value: <input type="number" name="value" min="1"/> <input type="submit" value="Enter"/></p>
</form>
<p>
    <a href="Controller?command=accountPage">To account page</a>
</p>
</body>
</html>
