<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Catalog</title>
</head>
<body>
<h2> Choose subject</h2>
<c:forEach items="${requestScope.subjectMap}" var="entry">
    <p>
        <a href="${entry.value}">${entry.key}</a>
    </p>
</c:forEach>
<a href="Controller?command=accountPage">To account page</a>
</body>
</html>