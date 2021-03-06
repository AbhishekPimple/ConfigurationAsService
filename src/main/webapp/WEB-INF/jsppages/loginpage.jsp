<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>

<welcome:loginheader />
<center>
<h3 style="color: red">${message}</h3>
</center>
<script>


function validateForm() {
    var emailid = document.forms["loginForm"]["emailid"].value;
	var password = document.forms["loginForm"]["password"].value;
    if (emailid == null || emailid == "" || password == null || password == "") {
        alert("Please fill all mandatory fields.");
        return false;
    }
    
    //Ref: W3Schools.com
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
<br> <br> <br> <br> <br>
<center>
<!-- <img src="resources/images/Background.jpg" /> -->
<form:form id="loginForm" action="performlogin"
	onsubmit="return validateForm()" method="POST" modelAttribute="user">
	<ul class="fieldlist">

		<li> <form:input
				id="emailid" type="text" placeholder="Email ID" class="k-textbox" style="width: 30%;"
				path="emailId" /></li>
		<li> <form:input
				id="password" type="password" placeholder="Password" class="k-textbox" style="width: 30%;"
				path="password" /></li>
		<li><a href="register"> New User?</a></li>
		<li><input type="submit" class="k-button k-primary"  value="Log In" /></li>
	</ul>
</form:form>
</center>

<welcome:footer />