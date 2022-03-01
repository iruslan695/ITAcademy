<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 20.02.2022
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1><c:out value="${requestScope.journalName}"/></h1>
<h2>Price: <c:out value="${requestScope.journalPrice}"/> $/month</h2>
<h2>Description</h2>
<p>
    <c:out value="${requestScope.description}"/>
</p>
<c:if test="${requestScope.is_activated eq 'y'}">
    <c:forEach items="${requestScope.releases}" var="entry">
        <p>
            <a href="${entry.value}">Release №${entry.key}</a>
        </p>
    </c:forEach>
</c:if>

<c:if test="${requestScope.is_activated eq 'n'}">
    <h2>
        <a href="Controller?command=goToSubscribePage&journalName=${requestScope.journalName}&journalPrice=${requestScope.journalPrice}">Subscribe</a>
    </h2>
</c:if>

</body>
</html>
