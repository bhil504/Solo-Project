<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Add A New Track</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <h1>Add A Song</h1>
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

        <!-- Error message display -->
        <c:if test="${not empty errorMessage}">
            <div style="color:red;">
                <p>${errorMessage}</p>
            </div>
        </c:if>
	</form>
   
    <a href="/welcome">Cancel</a>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>