<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Author Management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Author Management</h1>
            <nav>
                <a href="index.jsp" class="btn btn-secondary">Home</a>
                <a href="books" class="btn btn-secondary">Books</a>
            </nav>
        </header>

        <div class="card">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
                <h2>List of Authors</h2>
                <a href="authors?action=new" class="btn">Add New Author</a>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="author" items="${listAuthors}">
                        <tr>
                            <td><c:out value="${author.id}" /></td>
                            <td><c:out value="${author.name}" /></td>
                            <td><c:out value="${author.email}" /></td>
                            <td class="actions">
                                <a href="authors?action=edit&id=<c:out value='${author.id}' />">Edit</a>
                                <a href="authors?action=delete&id=<c:out value='${author.id}' />" onclick="return confirm('Are you sure?');" style="color: #ef4444;">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
