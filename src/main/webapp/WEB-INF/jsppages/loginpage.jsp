<%@include file="springtabinclude.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>
	<body>
		<h2>${message}</h2>
		<form:form id="loginForm" method="post" action="performlogin" modelAttribute="user">

			<form:label path="emailId">Email Id</form:label>
			<form:input id="emailId" name="emailId" path="emailId" /><br>
			<form:label path="username">Password</form:label>
			<form:password id="password" name="password" path="password" /><br>
			<br> <a href="register" > New User?</a> <br> 
			<br>
			<input type="submit" value="Log In" />
		</form:form>
	</body>
</html>