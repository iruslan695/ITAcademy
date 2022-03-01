<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>logination Page</title>
</head>
<body>
<h1>logination</h1>
<form action=Controller method="get">
    <input type="hidden" name="command" value="loginationPage">
    <div style="margin-left: 27px">
        <p>
            User login: <input type="text" name="login"/>
        </p>
    </div>
    <p>
        User password: <input type="password" name="password"/>
    </p>
    <input type="submit" value="Enter"/>
</form>
<p>
    <a href="Controller?command=goToHelloPage">To the main page</a>
</p>

</body>
</html>