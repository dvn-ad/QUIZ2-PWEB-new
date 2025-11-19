<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Book Management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Book Management</h1>
            <nav>
                <a href="index.jsp" class="btn btn-secondary">Home</a>
                <a href="authors" class="btn btn-secondary">Authors</a>
            </nav>
        </header>

        <div class="card">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
                <h2>List of Books</h2>
                <a href="books?action=new" class="btn">Add New Book</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author ID</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${listBooks}">
                        <tr>
                            <td><c:out value="${book.id}" /></td>
                            <td><c:out value="${book.title}" /></td>
                            <td><c:out value="${book.authorId}" /></td>
                            <td>$<c:out value="${book.price}" /></td>
                            <td class="actions">
                                <a href="books?action=edit&id=<c:out value='${book.id}' />">Edit</a>
                                <a href="books?action=delete&id=<c:out value='${book.id}' />" onclick="return confirm('Are you sure?');" style="color: #ef4444;">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
