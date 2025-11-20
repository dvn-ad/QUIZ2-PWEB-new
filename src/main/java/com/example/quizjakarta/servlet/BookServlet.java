package com.example.quizjakarta.servlet;

import com.example.quizjakarta.model.Author;
import com.example.quizjakarta.model.Book;
import com.example.quizjakarta.repository.DataStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "insert":
                insertBook(request, response);
                break;
            case "update":
                updateBook(request, response);
                break;
            default:
                listBooks(request, response);
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
                deleteBook(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> listBooks = DataStore.getAllBooks();
        request.setAttribute("listBooks", listBooks);
        request.setAttribute("authors", DataStore.getAllAuthors());
        request.getRequestDispatcher("book-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Author> listAuthors = DataStore.getAllAuthors();
        request.setAttribute("listAuthors", listAuthors);
        request.getRequestDispatcher("book-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = DataStore.getBookById(id);
        List<Author> listAuthors = DataStore.getAllAuthors();
        
        request.setAttribute("book", existingBook);
        request.setAttribute("listAuthors", listAuthors);
        request.getRequestDispatcher("book-form.jsp").forward(request, response);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        int authorId = Integer.parseInt(request.getParameter("authorId"));
        double price = Double.parseDouble(request.getParameter("price"));

        Book newBook = new Book(0, title, authorId, price);
        DataStore.addBook(newBook);
        response.sendRedirect("books");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        int authorId = Integer.parseInt(request.getParameter("authorId"));
        double price = Double.parseDouble(request.getParameter("price"));

        Book book = new Book(id, title, authorId, price);
        DataStore.updateBook(book);
        response.sendRedirect("books");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        DataStore.deleteBook(id);
        response.sendRedirect("books");
    }
}
