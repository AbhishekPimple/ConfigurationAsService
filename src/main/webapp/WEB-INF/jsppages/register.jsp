<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>


<welcome:loginheader />
<a style="color: #3f51b5; font-size:medium;" href="/ConfigAsService">Login Page</a>

<center>
<h3 style="color: red">${message}</h3>
</center>
<script>
	function validateForm() {
		var emailid = document.forms["registerForm"]["emailid"].value;
		var password = document.forms["registerForm"]["password"].value;
		var username = document.forms["registerForm"]["username"].value;
		var confirmpassword = document.forms["registerForm"]["confirmpassword"].value;
		if (emailid == null || emailid == "" || password == null
				|| password == "" || username == null || username == ""
				|| confirmpassword == null || confirmpassword == "") {
			alert("Please fill all mandatory fields.");
			return false;
		}

		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if (!re.test(emailid)) {
			alert("Please add correct email address.");
			return false;
		}
		if (password != confirmpassword) {
			alert("Password and Confirm Password donot match.");
			return false;
		}
		if (password.length > 30 || emailid.length > 30 || username.length > 30) {
			alert("Some of the fields are too long");
			return false;
		}
	}
</script>

<br>
<br>
<br>
<br>
<center>
<form:form id="registerForm" action="performregister"
	onsubmit="return validateForm()" method="POST" modelAttribute="user">
	<ul class="fieldlist">

		<li> <form:input
				id="emailid" type="text" placeholder="Email ID" class="k-textbox" style="width: 30%;"
				path="emailId" /></li>
		<li> <form:input
				id="username" type="text" placeholder="Name" class="k-textbox" style="width: 30%;"
				path="username" /></li>
		<li><form:input
				id="password" type="password" placeholder="Password" class="k-textbox" style="width: 30%;"
				path="password" /></li>
		<li><input
			id="confirmpassword" placeholder="Confirm Password" type="password" class="k-textbox"
			style="width: 30%;" /></li>
		<li><input type="submit" class="k-button k-primary"
			value="Register Me" /></li>
	</ul>
</form:form>
</center>

<welcome:footer />