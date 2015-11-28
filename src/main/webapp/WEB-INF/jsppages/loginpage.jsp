<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="springtabinclude.jsp"%>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>

<welcome:loginheader />
		<h2>${message}</h2>
		
		<form:form id="loginForm" action="performlogin" method="POST" modelAttribute="user">
						<ul class="fieldlist">
							
							<li>
								<label for="emailid">Email ID</label> 
								<form:input id="emailid" type="text" class="k-textbox" style="width: 100%;" path="emailId"/>
							</li>
							<li>
								<label for="password">Password</label> 
								<form:input id="password" type="password" class="k-textbox" style="width: 100%;" path="password"/>
							</li>
							<li>
								<a href="register" > New User?</a>
							</li>
							<li>
								<input type="submit" class="k-button k-primary" value="Log In" />
							</li>
						</ul>
		</form:form>
<welcome:footer />