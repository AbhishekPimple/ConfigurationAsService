<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@include file="springtabinclude.jsp"%>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>

<welcome:loginheader />
		<a href="/ConfigAsService" >Login Page</a>
	
		<h3 align="center">Registration Page</h3>
		<h4>${message}</h4>
		
		<form:form id="registerForm" action="performregister" method="POST" modelAttribute="user">
						<ul class="fieldlist">
							
							<li>
								<label for="emailid">Email ID</label> 
								<form:input id="emailid" type="text" class="k-textbox" style="width: 100%;" path="emailId"/>
							</li>
							<li>
								<label for="username">Username</label> 
								<form:input id="username" type="text" class="k-textbox" style="width: 100%;" path="username"/>
							</li>
							<li>
								<label for="password">Password</label> 
								<form:input id="password" type="password" class="k-textbox" style="width: 100%;" path="password"/>
							</li>
							<li>
								<label for="confirmpassword">Confirm Password</label> 
								<input id="confirmpassword" type="password" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<input type="submit" class="k-button k-primary" value="Register" />
							</li>
						</ul>
		</form:form>

<welcome:footer />