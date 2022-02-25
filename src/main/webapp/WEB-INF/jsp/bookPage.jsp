<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BookPage</title>
</head>
<body>
<c:forEach items="${requestScope.subjectMap}" var="entry">
    <p>
        <a href="${entry.value}">${entry.key}</a>
    </p>
</c:forEach>
</body>
</html>