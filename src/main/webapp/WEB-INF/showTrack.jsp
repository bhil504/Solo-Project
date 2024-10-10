<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Show Track</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1><c:out value="${track.title}"/></h1>

	<table class="table">    
		<thead class="thead-dark">
			<tr>
			<div>
				<th>title: <td><c:out value="${track.title}"/><td></th>
			</div>
			<div>
				<th>Started By: <td><c:out value="${track.user.username}"/><td></th>
			</div>
			<div>
				<th>Genre: <td><c:out value="${track.genre}"/><td></th>
			</div>
			<div>
				<th>Lyrics: <td><c:out value="${track.lyrics}"/><td></th>
			</div>
			</tr>
		</thead>
	</table>
	
	<div> 
	<a href="/welcome">Cancel</a>
	</div>
	
	<a href="/tracks/${track.id}/edit">Edit</a>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>