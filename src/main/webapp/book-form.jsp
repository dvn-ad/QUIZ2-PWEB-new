<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Book Form</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Book Management</h1>
            <nav>
                <a href="books" class="btn btn-secondary">Back to List</a>
            </nav>
        </header>

        <div class="card" style="max-width: 600px; margin: 0 auto;">
            <h2>
                <c:if test="${book != null}">Edit Book</c:if>
                <c:if test="${book == null}">Add New Book</c:if>
            </h2>

            <c:if test="${book != null}">
                <form action="books?action=update" method="post">
                    <input type="hidden" name="id" value="<c:out value='${book.id}' />" />
            </c:if>
            <c:if test="${book == null}">
                <form action="books?action=insert" method="post">
            </c:if>
            
                <label>Title:</label>
                <input type="text" name="title" value="<c:out value='${book.title}' />" required />
                
                <label>Author:</label>
                <select name="authorId">
                    <c:forEach var="author" items="${listAuthors}">
                        <option value="${author.id}" <c:if test="${book != null && book.authorId == author.id}">selected</c:if>>
                            <c:out value="${author.name}" />
                        </option>
                    </c:forEach>
                </select>
                
                <label>Price:</label>
                <input type="number" step="0.01" name="price" value="<c:out value='${book.price}' />" required />
                
                <div class="form-actions">
                    <input type="submit" value="Save Book" class="btn" />
                </div>
            </form>
        </div>
    </div>
</body>
</html>
