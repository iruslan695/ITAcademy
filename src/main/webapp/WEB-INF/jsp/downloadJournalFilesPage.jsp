<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 11.02.2022
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Download Journal Files</title>
</head>
<body>
<c:if test="${param.downloadInfo eq 'error'}">
    Something went wrong, please try again <br>
</c:if>

<c:if test="${param.downloadInfo eq 'ok'}">
    Journal successfully loaded <br>
</c:if>
<form action="Controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="downloadJournalFilesPage">
    <p>Please add journal information</p>
    <p>Title name: <input type="text" name="titleName"/></p>
    <p>Title price: <input type="number" name="titlePrice" min="1"/></p>
    <p>Release number: <input type="number" name="releaseNumber" min="1"/></p>
    <p>Enter a file: <input type="file" name="journalFile" accept=".pdf"/></p>
    <p>Enter journal description</p>
    <p><textarea name="journalDescription" col="5" rows="5">description</textarea></p>
    <p>
        Journal subjects: <br>
        <input type="checkbox" name="journalSubject" value="cars"/>Cars <br>
        <input type="checkbox" name="journalSubject" value="games"/>Games <br>
        <input type="checkbox" name="journalSubject" value="sports"/>Sports <br>
        <input type="checkbox" name="journalSubject" value="engineering"/>Engineering <br>
        <input type="checkbox" name="journalSubject" value="programming"/>Programming <br>
        <input type="checkbox" name="journalSubject" value="politics"/>Politics <br>
        <input type="checkbox" name="journalSubject" value="showBusiness"/>Show Business <br>
        <input type="checkbox" name="journalSubject" value="weapon"/>Weapon <br>
        <input type="checkbox" name="journalSubject" value="music"/>Music <br>
        <input type="checkbox" name="journalSubject" value="science"/>Science <br>
        <input type="checkbox" name="journalSubject" value="movies"/>Movies <br>
        <input type="checkbox" name="journalSubject" value="comics"/>Comics <br>
        <input type="checkbox" name="journalSubject" value="DIY"/>DIY <br>
        <input type="checkbox" name="journalSubject" value="arts"/>Arts <br>
    </p>
    <input type="submit" value="Enter"/>
</form>
<a href="Controller?command=accountPage">To account page</a>
</body>
</html>
