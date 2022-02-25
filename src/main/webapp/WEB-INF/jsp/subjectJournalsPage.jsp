<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 19.02.2022
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2> Choose journal</h2>
<c:forEach items="${requestScope.journalSet}" var="entry">
    <p>
        <a href="${entry.value}">${entry.key}</a>
    </p>
</c:forEach>
</body>
</html>
