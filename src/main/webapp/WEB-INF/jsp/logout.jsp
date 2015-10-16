<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <h3>${username} has successfully logged out of the application.</h3> <br/>
  <h4>Return to <spring:url value="/login" var="loginURL" htmlEscape="true"/><a href="${loginURL}">Login</a>page </h4>
</body>
</html>