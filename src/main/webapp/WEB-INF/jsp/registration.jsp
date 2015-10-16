<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Registration</title>
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
		<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" href="css/calendar.css"> 
	</head>
	<script>
		$(document).ready(function() {
		    $(function() {
		        $("#dateOfBirth").datepicker({changeMonth: true,changeYear: true,yearRange: '-100:+0',dateFormat: 'dd/mm/yy'});
		    });
		});
	</script>
	<body topmargin="60px;">

		<form:form id="registrationForm" method="post" action="userRegister" modelAttribute="registrationBean">
		
		<div style="border: 10px;border-color:#00F;" align="center">
			<font color="red" >${errorMessage}</font>		
			<center><h3 class="txt-bluedk"><strong>User Registration</strong></h3></center>
			<center><hr color="#1874CD" width="50%"/></center>
			<center><h5 class="txt-bluedk"><strong>Please enter your details to register</strong></h5></center>
			<center><hr color="#1874CD" width="50%"/></center>
			
			 <table width="30%" align="center" cellspacing="0" cellpadding="0" style="padding-top: 10px; padding-right:1%">
				 
			  <tr >
				<td width="40%" align="left" style="padding-bottom:1%"><strong>Email</strong></td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:input id="emailID" name="emailID" path="emailID"  maxlength="32"/></td>
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:errors cssStyle="color:red;font-size:13px;" name="emailID" path="emailID" /></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr>	
			    <td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.password" text="Password:"/></strong></td>
				<td width="5%" align="left" style="padding-right: 10%;padding-bottom:1%"><form:password id="password" name="password" path="password"  maxlength="32"/></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:errors cssStyle="color:red;font-size:13px;" name="password" path="password" /></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.firstName" text="First Name"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="firstName" name="firstName" path="firstName"   maxlength="32"/></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<!-- td width="20%">&nbsp;</td-->
			  </tr>
			  
			  <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:errors cssStyle="color:red;font-size:13px;" name="firstName" path="firstName" /></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.lastName" text="Last Name"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="lastName" name="lastName" path="lastName"  maxlength="32" /></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<!-- td width="20%">&nbsp;</td-->
			  </tr>
			  <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:errors cssStyle="color:red;font-size:13px;" name="lastName" path="lastName" /></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.dateOfBirth" text="Date of Birth"/></strong></td>
				<td width="60%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="dateOfBirth" name="dateOfBirth" path="dateOfBirth" readonly="true" maxlength="10"/></td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:errors cssStyle="color:red;font-size:13px;" name="dateOfBirth" path="dateOfBirth" /></td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr>
				<td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.addressLine1" text="Address Line 1"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="addressLine1" name="addressLine1" path="addressLine1"  maxlength="32" /></td>
				<td width="55%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr>
			    <td width="25%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.addressLine2" text="Address Line 2"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="addressLine2" name="addressLine2" path="addressLine2"  maxlength="32" /></td>
				<td width="55%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr >
				<td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.street" text="Street"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="street" name="street" path="street"  maxlength="32" /></td>
				<td width="55%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr>
			    <td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.zip" text="Zip"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="zip" name="zip" path="zip"  maxlength="32" /></td>
				<td width="55%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr >
				<td width="40%" align="left" style="padding-right: 10%;padding-bottom:1%"><strong><spring:message code="label.city" text="City"/></strong></td>
				<td width="5%" align="left" style="padding-right: 5%;padding-bottom:1%"><form:input id="city" name="city" path="city"  maxlength="32" /></td>
				<td width="55%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <tr>
			    <td width="40%" align="left" style="padding-bottom:1%"><strong><spring:message code="label.country" text="Country"/></strong></td>
				<td width="5%" align="left" style="padding-bottom:1%"><form:select id="country" name="country" path="country" items="${countryList}" itemLabel="countryName" itemValue="countryCode"/></td>
				<td width="55%">&nbsp;</td>
			  </tr>
			  
			   <tr >
				<td width="40%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="5%" align="left" style="padding-bottom:1%">&nbsp;</td>
				<td width="55%" align="left" style="padding-bottom:1%">&nbsp;</td>
			  </tr>
			  
			  <spring:url value="/login" var="loginUrl"/>
			  <tr>
			  	<td width="40%" align="left" style="padding-right: 20%;padding-top:5%"><input type="button" value="Back to Login" class="button1" onclick="location.href='${loginUrl}'"/></td>
			  	<td width="55%" align="left" style="padding-right: 20%;padding-top:5%"><input type="submit" value="Register" class="button1" /></td>
			  	<td width="5%">&nbsp;</td>
			    
			  </tr>
			 </table>	 
		</div>
		</form:form>
	</body>
</html>