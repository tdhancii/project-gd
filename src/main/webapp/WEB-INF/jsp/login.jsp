<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>
	<body topmargin="80px;">
		
		<form:form id="loginForm" method="post" action="login" modelAttribute="loginBean">
		
		<div style="border: 1px;border-color:#00F;" align="center">
			<font color="red" ><core:out value="${loginMessage}"/></font>
			
			<center><h3 class="txt-bluedk"><strong>Welcome to the Currency Conversion Centre</strong></h3></center>
			<center><hr color="#1874CD" width="50%"/></center>
			<center><h5 class="txt-bluedk"><strong>Please enter your user name and password to access the application</strong></h5></center>
			<center><hr color="#1874CD" width="50%"/></center>
			
			 <table width="40%" align="center" cellspacing="0" cellpadding="0" style="padding-top: 10px; padding-right:5%">
				 
			  <tr align="center" >
				<td width="60%" align="right" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.email" text="Email"/></strong></td>
				<td width="20%"  align="center" style="padding-bottom:1%"><strong><spring:message code="label.password" text="Password"/></strong></td>
				<td width="20%">&nbsp;</td>
			  </tr>
			  <tr class="spacer"><td></td></tr>
			  <tr >
			    <td width="60%" align="right" style="padding-right: 5%;padding-bottom:1%"><form:input id="email" name="email" path="email"/></td>
				<td width="20%" align="left" style="padding-bottom:1%"><form:password id="password" name="password" path="password" /></td>
				<td width="20%" align="right"><input type="submit" value="Sign In" class="button1"/></td>
			  </tr>
			  <tr >
			    <td width="60%" align="right" style="padding-right: 5%;padding-bottom:1%"><form:errors path="email" cssStyle="color:red;font-size:13px;"/></td>
				<td width="20%" align="left" style="padding-bottom:1%"><form:errors path="password" cssStyle="color:red;font-size:13px;"/></td>
				<td width="20%" align="right">&nbsp;</td>
			  </tr>
			 <tr class="spacer"><td></td></tr> 
			  <tr 	>
				<td  width="60%" align="center">&nbsp;</td>
				<spring:url value="/userRegister" var="newUserUrl"/>
				<td  width="20%" align="right"><a href="${newUserUrl}">New User</a>  
				<td  width="20%">&nbsp;</td>
			  </tr>
			  
			 </table>	 
		</div>
		
		</form:form>
	</body>
</html>