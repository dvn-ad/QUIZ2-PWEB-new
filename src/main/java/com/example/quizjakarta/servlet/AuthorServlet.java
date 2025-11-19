package com.example.quizjakarta.servlet;

import com.example.quizjakarta.model.Author;
import com.example.quizjakarta.repository.DataStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "insert":
                insertAuthor(request, response);
                break;
            case "update":
                updateAuthor(request, response);
                break;
            default:
                listAuthors(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteAuthor(request, response);
                break;
            default:
                listAuthors(request, response);
                break;
        }
    }

    private void listAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Author> listAuthors = DataStore.getAllAuthors();
        request.setAttribute("listAuthors", listAuthors);
        request.getRequestDispatcher("author-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("author-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Author existingAuthor = DataStore.getAuthorById(id);
        request.setAttribute("author", existingAuthor);
        request.getRequestDispatcher("author-form.jsp").forward(request, response);
    }

    private void insertAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        Author newAuthor = new Author(0, name, email);
        DataStore.addAuthor(newAuthor);
        response.sendRedirect("authors");
    }

    private void updateAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Author author = new Author(id, name, email);
        DataStore.updateAuthor(author);
        response.sendRedirect("authors");
    }

    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        DataStore.deleteAuthor(id);
        response.sendRedirect("authors");
    }
}
