<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 27.02.2022
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>${requestScope.journalName}</title>
</head>
<body>

<h1>${requestScope.journalName} №${requestScope.releaseNumber}</h1>
<object><embed src=”${requestScope.journal}” type=”application/pdf” width=”100%” height=”1500px” /></object>
<a href="Controller?command=accountPage">To account page</a>
</body>
</html>
