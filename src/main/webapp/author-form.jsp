<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Author Form</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Author Management</h1>
            <nav>
                <a href="authors" class="btn btn-secondary">Back to List</a>
            </nav>
        </header>

        <div class="card" style="max-width: 600px; margin: 0 auto;">
            <h2>
                <c:if test="${author != null}">Edit Author</c:if>
                <c:if test="${author == null}">Add New Author</c:if>
            </h2>
            
            <c:if test="${author != null}">
                <form action="authors?action=update" method="post">
                    <input type="hidden" name="id" value="<c:out value='${author.id}' />" />
            </c:if>
            <c:if test="${author == null}">
                <form action="authors?action=insert" method="post">
            </c:if>
            
                <label>Name:</label>
                <input type="text" name="name" value="<c:out value='${author.name}' />" required />
                
                <label>Email:</label>
                <input type="email" name="email" value="<c:out value='${author.email}' />" required />
                
                <div class="form-actions">
                    <input type="submit" value="Save Author" class="btn" />
                </div>
            </form>
        </div>
    </div>
</body>
</html>
