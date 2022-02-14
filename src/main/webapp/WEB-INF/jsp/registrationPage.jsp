<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Registration</title>
</head>
<body>
<h1>Registration</h1>
<form action=Controller method="get">
    <input type="hidden" name="command" value="registrationPage">
    <p>
        Login: <input type="text" name="login"/> <br>
        Password: <input type="password" name="password"/> <br>
        Repeat password: <input type="password" name="repeatPassword"/> <br>
        Name: <input type="text" name="name"/> <br>
        Surname: <input type="text"
                        name="surname"/> <br>
        Age: <input type="text" name="age"/> <br>
        Male:<select name="male">
        <option value="male">male</option>
        <option value="female">female</option>
    </select> <br>
        Choose your interests:
        <input type="checkbox" name="interests" value="cars"/>Cars <br>
        <input type="checkbox" name="interests" value="games"/>Games <br>
        <input type="checkbox" name="interests" value="sports"/>Sports <br>
        <input type="checkbox" name="interests" value="engineering"/>Engineering <br>
        <input type="checkbox" name="interests" value="programming"/>Programming <br>
        <input type="checkbox" name="interests" value="politics"/>Politics <br>
        <input type="checkbox" name="interests" value="showBusiness"/>Show Business <br>
        <input type="checkbox" name="interests" value="weapon"/>Weapon <br>
        <input type="checkbox" name="interests" value="music"/>Music <br>
        <input type="checkbox" name="interests" value="science"/>Science <br>
        <input type="checkbox" name="interests" value="movies"/>Movies <br>
        <input type="checkbox" name="interests" value="comics"/>Comics <br>
        <input type="checkbox" name="interests" value="DIY"/>DIY <br>
        <input type="checkbox" name="interests" value="arts"/>Arts <br>
    </p>
    <input type="submit" value="Enter"/>
</form>
<p>
    <a href="Controller?command=goToHelloPage">To the main page</a>
</p>

</body>
</html>