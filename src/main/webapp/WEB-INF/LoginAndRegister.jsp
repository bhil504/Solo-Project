<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login And Registration Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div>
	<H1>Register</H1>
	<form:form action="/register" method="post" modelAttribute="newUser">
		<p>
			<form:label path="username">Username:</form:label>
			<form:errors path="username"/>
			<form:input path="username"/>
		</p>
		<p>
			<form:label path="email">Email:</form:label>
			<form:errors path="email"/>
			<form:input path="email"/>
		</p>
		<p>
			<form:label path="password">Password:</form:label>
			<form:errors path="password"/>
			<form:input path="password" type="password"/>
		</p>
		<p>
			<form:label path="confirm">Confirm Password:</form:label>
			<form:errors path="confirm"/>
			<form:input path="confirm" type="password"/>
		</p>
			<input type="submit" value="Submit"/>
		</form:form>
	</div>
	<div>
	<H2>Login</H2>
	<form:form action="/login" method="post" modelAttribute="newLogin">
		<p>
			<form:label path="email">Email:</form:label>
			<form:errors path="email"/>
			<form:input type="email" path="email" cssClass="form-control" id="firstname" />
		</p>
		<p>
			<form:label path="password">Password:</form:label>
			<form:errors path="password"/>
			<form:input path="password" type="password"/>
		</p>
			<input type="submit" value="Submit"/>
		</form:form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>