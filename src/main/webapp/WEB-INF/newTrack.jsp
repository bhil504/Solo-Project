<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Add A New Track</title>
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
    <h1>Add A Song</h1>
    <form action="/upload-track" method="POST" enctype="multipart/form-data">
        <!-- CSRF token for security -->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        
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
            <textarea id="lyrics" modelAttribute="lyrics"  name="lyrics">${track.lyrics}</textarea>
        </div>
        <div>
            <label for="file">Upload File:</label>
            <input type="file" name="file" accept="audio/*"  />
        </div>
        
        <!-- Submit button -->
        <div>
            <button type="submit">Submit</button>
        </div>

        <!-- Error message display -->
        <c:if test="${not empty errorMessage}">
            <div style="color:red;">
                <p>${errorMessage}</p>
            </div>
        </c:if>
    </form>
    <a href="/welcome">Cancel</a>
</body>
</html>