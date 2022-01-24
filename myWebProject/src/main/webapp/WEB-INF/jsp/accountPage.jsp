<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="urf-8">
<title>AccountPage</title>
</head>
<body>
	<h1>Welcome Name Surname</h1>
	<p>Login
	<p>
	<p>Status: reader</p>
	<p>Balance :</p>

	<c:if test="${not empty param.regInfo}" var="regSuccess">
		<c:out value="${param.login}" />
		<c:out value="${param.name}" />
		<c:out value="${param.surname}" />
	</c:if>
</body>
</html>