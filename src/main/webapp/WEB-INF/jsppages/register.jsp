<%@include file="springtabinclude.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Register</title>
	</head>
	<body>
		<h2>${message}</h2>
		<form:form id="registerForm" modelAttribute="newuser" method="post" action="performregister">
			<form:label path="emailId">Email ID</form:label>
			<form:input id="emailID" name="emailId" path="emailId" /><br>
			<form:label path="username">Username</form:label>
			<form:input id="username" name="username" path="username" /><br>
			<form:label path="username">Password</form:label>
			<form:password id="password" name="password" path="password" /><br>
			<form:label path="confirmPassword">Confirm Password</form:label>
			<form:password id="confirmPassword" name="confirmPassword" path="confirmPassword" /><br>
			<input type="submit" value="Register" />
		</form:form>
	</body>
</html>