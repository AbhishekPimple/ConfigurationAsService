<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="springtabinclude.jsp"%>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>

<welcome:header />
<h2>${message}</h2>
<script>

function validateForm() {
    var emailid = document.forms["loginForm"]["emailid"].value;
	var password = document.forms["loginForm"]["password"].value;
    if (emailid == null || emailid == "" || password == null || password == "") {
        alert("Please fill all mandatory fields.");
        return false;
    }
    
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    if(!re.test(emailid))  {
       alert("Please add correct email address.");
       return false;
    }
    if(password.length > 30 || emailid.length > 30) {
    	alert("Some of the fields are too long");
    	return false;
    }
}

</script>
<form:form id="loginForm" action="performlogin" onsubmit="return validateForm()" method="POST"
	modelAttribute="user">
	<ul class="fieldlist">

		<li><label for="emailid">Email ID*</label> <form:input
				id="emailid" type="text" class="k-textbox" style="width: 100%;"
				path="emailId" /></li>
		<li><label for="password">Password*</label> <form:input
				id="password" type="password" class="k-textbox" style="width: 100%;"
				path="password" /></li>
		<li><a href="register"> New User?</a></li>
		<li><input type="submit" class="k-button k-primary"
			value="Log In" /></li>
	</ul>
</form:form>
<welcome:footer />