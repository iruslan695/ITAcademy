<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="utf-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="urf-8">
    <title>AccountPage</title>
</head>
<body>

<c:if test="${not empty param.regInfo}" var="regSuccess">
    <h1>Welcome</h1> <br>
</c:if>

<c:if test="${not empty param.logInfo}" var="logSucces">
    <h1>Welcome back</h1> <br>
</c:if>

<h1><c:out value="${sessionScope.user.name}"/>
    <c:out value="${sessionScope.user.surname}"/>
</h1>

<h2>UserID: <c:out value="${sessionScope.user.id}"/></h2>
<h2>Status: <c:out value="${sessionScope.user.role}"/></h2>


<c:if test="${sessionScope.user.role !='admin'}" var="isNotAdmin">
    <h2>Balance: <c:out value="${sessionScope.user.balance}"/></h2>
    <h2><a href="Controller?command=goToTopUpBalancePage">Top up balance</a></h2>
</c:if>
<h2><a href="Controller?command=goToCatalogPage">Book Catalog</a></h2>
<c:if test="${sessionScope.user.role =='reader'}" var="isReader">
    <h2><a href="Controller?command=goToBecomePublisherPage">Become Publisher</a></h2>
</c:if>


<c:if test="${sessionScope.user.role =='admin'}" var="isAdmin">
    <h2><a href="Controller?command=goToChangeUserStatusPage">Change user status</a></h2>
</c:if>

<c:if test="${sessionScope.user.role =='publisher'}" var="isPublisher">
    <h2><a href="Controller?command=goToDownloadJournalFiles">Download journal files</a></h2>
</c:if>
</body>
</html>