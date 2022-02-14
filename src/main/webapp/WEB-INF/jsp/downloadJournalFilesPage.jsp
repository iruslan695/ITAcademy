<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 11.02.2022
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download Journal Files</title>
</head>
<body>
<form action="Controller" method="post">
    <input type="hidden" name="command" value="downloadJournalFilesPage">
    <p>Please add journal information</p>
    <p>Title name: <input type="text" name="titleName"/></p>
    <p>Title price: <input type="number" name="titlePrice" min="1"/></p>
    <p>Release number: <input type="number" name="releaseNumber" min="1"/></p>
    <p>File URL: <input type="text" name="fileUrl"/></p>
    <input type="submit" value="Enter"/>
</form>
<a href="Controller?command=accountPage">To account page</a>
</body>
</html>
