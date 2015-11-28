<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="springtabinclude.jsp"%>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>

<welcome:header />
<a href="/ConfigAsService">Login Page</a>

<h3 align="center">Registration Page</h3>
<h4>${message}</h4>
<script>

function validateForm() {
    var emailid = document.forms["registerForm"]["emailid"].value;
	var password = document.forms["registerForm"]["password"].value;
	var username = document.forms["registerForm"]["username"].value;
	var confirmpassword = document.forms["registerForm"]["confirmpassword"].value;
    if (emailid == null || emailid == "" || password == null || password == "" || username == null || username == "" || confirmpassword == null || confirmpassword == "") {
        alert("Please fill all mandatory fields.");
        return false;
    }
    
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    if(!re.test(emailid))  {
       alert("Please add correct email address.");
       return false;
    }
    if(password != confirmpassword) {
    	alert("Password and Confirm Password donot match.");
    	return false;
    }
    if(password.length > 30 || emailid.length > 30 || username.length > 30) {
    	alert("Some of the fields are too long");
    	return false;
    }
}

</script>
<form:form id="registerForm" action="performregister" onsubmit="return validateForm()" method="POST"
	modelAttribute="user">
	<ul class="fieldlist">

		<li><label for="emailid">Email ID*</label> <form:input
				id="emailid" type="text" class="k-textbox" style="width: 100%;"
				path="emailId" /></li>
		<li><label for="username">Username*</label> <form:input
				id="username" type="text" class="k-textbox" style="width: 100%;"
				path="username" /></li>
		<li><label for="password">Password*</label> <form:input
				id="password" type="password" class="k-textbox" style="width: 100%;"
				path="password" /></li>
		<li><label for="confirmpassword">Confirm Password*</label> <input
			id="confirmpassword" type="password" class="k-textbox"
			style="width: 100%;" /></li>
		<li><input type="submit" class="k-button k-primary"
			value="Register" /></li>
	</ul>
</form:form>

<welcome:footer />