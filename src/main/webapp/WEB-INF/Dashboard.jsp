<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div>
		<h1>Welcome <c:out value="${user.username }"></c:out></h1>
		<a href="/logout">LogOut</a>
	</div>
	<table class="table">    
		<thead class="thead-dark">
			<tr>
				<th>Name:</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="track" items="${tracks}">
				<tr>
					<td>
					<div>
					<a href="/tracks/${track.id}"><c:out value="${track.title}"/></a>
					</div>
					</td>
					<td>Genre:<c:out value="${track.genre}"/></td>
					 			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<a href="/tracks/new">Create A New Track</a>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>