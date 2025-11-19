<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header style="text-align: center;">
            <h1>Library Management System</h1>
            <p style="color: #64748b;">Manage your books and authors efficiently</p>
            <div style="margin-top: 10px;">
                <span>Welcome, ${sessionScope.user.username}!</span>
                <a href="logout" style="color: red; margin-left: 10px;">Logout</a>
            </div>
        </header>
        
        <div class="hero">
            <div class="card" style="text-align: center; width: 200px;">
                <h2>Authors</h2>
                <p>Manage book authors</p>
                <a href="authors" class="btn">Go to Authors</a>
            </div>
            
            <div class="card" style="text-align: center; width: 200px;">
                <h2>Books</h2>
                <p>Manage library books</p>
                <a href="books" class="btn">Go to Books</a>
            </div>
        </div>
    </div>
</body>
</html>