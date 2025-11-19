package com.example.quizjakarta.repository;

import com.example.quizjakarta.model.Author;
import com.example.quizjakarta.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DataStore {
    private static final List<Author> authors = new ArrayList<>();
    private static final List<Book> books = new ArrayList<>();
    private static final AtomicInteger authorIdCounter = new AtomicInteger(1);
    private static final AtomicInteger bookIdCounter = new AtomicInteger(1);

    static {
        // Seed data
        addAuthor(new Author(0, "J.K. Rowling", "jk@example.com"));
        addAuthor(new Author(0, "George R.R. Martin", "grrm@example.com"));
        
        addBook(new Book(0, "Harry Potter", 1, 20.0));
        addBook(new Book(0, "Game of Thrones", 2, 25.0));
    }

    // Author methods
    public static List<Author> getAllAuthors() {
        return new ArrayList<>(authors);
    }

    public static Author getAuthorById(int id) {
        return authors.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    public static void addAuthor(Author author) {
        author.setId(authorIdCounter.getAndIncrement());
        authors.add(author);
    }

    public static void updateAuthor(Author author) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId() == author.getId()) {
                authors.set(i, author);
                return;
            }
        }
    }

    public static void deleteAuthor(int id) {
        authors.removeIf(a -> a.getId() == id);
        // Also delete books by this author? Or keep them? Let's cascade delete for simplicity
        books.removeIf(b -> b.getAuthorId() == id);
    }

    // Book methods
    public static List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public static Book getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public static void addBook(Book book) {
        book.setId(bookIdCounter.getAndIncrement());
        books.add(book);
    }

    public static void updateBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return;
            }
        }
    }

    public static void deleteBook(int id) {
        books.removeIf(b -> b.getId() == id);
    }
}
