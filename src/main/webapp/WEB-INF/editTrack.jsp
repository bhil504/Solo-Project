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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1>Edit Track: <c:out value="${tracks.title}"></c:out></h1>

<!-- Main form for updating track -->
<form action="/upload-track" method="post" enctype="multipart/form-data" class="mt-4">
    <div class="mb-3">
        <label for="title" class="form-label">Track Title</label>
        <input type="text" class="form-control" id="title" name="title" required>
    </div>
    <div class="mb-3">
        <label for="genre" class="form-label">Genre</label>
        <input type="text" class="form-control" id="genre" name="genre" required>
    </div>
    <div class="mb-3">
        <label for="lyrics" class="form-label">Lyrics</label>
        <textarea class="form-control" id="lyrics" name="lyrics" rows="3"></textarea>
    </div>
    <div class="mb-3">
        <label for="file" class="form-label">Upload Track</label>
        <input class="form-control" type="file" id="file" name="file">
    </div>
    <button type="submit" class="btn btn-primary">Upload</button>
</form>

<c:if test="${tracks.user.id == userId}">
    <form action="/tracks/delete/${tracks.id}" method="post" onsubmit="return confirm('Are you sure you want to delete this track?');">
        <input type="hidden" name="_method" value="delete">
        <input type="submit" value="Delete"/>
    </form>
</c:if>

<a href="/welcome">Cancel</a>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>