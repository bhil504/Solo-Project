<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Update Page</title>
    <style>
        * {
            justify-content: space-evenly;
            margin: 5px;
            padding: 5px;
        }
        form {
            justify-content: space-evenly;
            border-style: solid;
            padding: 10px;
        }
    </style>
</head>
<body>
<h1>Edit Track: <c:out value="${tracks.title}"></c:out></h1>

<!-- Main form for updating track -->
<form action="/tracks/${tracks.id}" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="hidden" name="_method" value="PUT">
    <div>
        <label for="title">Title:</label>
        <input type="text" name="title" value="${track.title}" />
    </div>
    <div>
        <label for="genre">Genre:</label>
        <input type="text" name="genre" value="${track.genre}" />
    </div>
    <div>
        <label for="lyrics">Lyrics:</label>
        <textarea name="lyrics">${track.lyrics}</textarea>
    </div>
    <div>
        <label for="file">Track File:</label>
        <input type="file" name="file" />
    </div>
    <button type="submit">Submit</button>
</form>

<c:if test="${tracks.user.id == userId}">
    <form action="/tracks/delete/${tracks.id}" method="post" onsubmit="return confirm('Are you sure you want to delete this track?');">
        <input type="hidden" name="_method" value="delete">
        <input type="submit" value="Delete"/>
    </form>
</c:if>

<a href="/welcome">Cancel</a>
</body>
</html>