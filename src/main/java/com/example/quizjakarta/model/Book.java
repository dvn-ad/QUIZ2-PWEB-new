package com.example.quizjakarta.model;

public class Book {
    private int id;
    private String title;
    private int authorId;
    private double price;

    public Book() {}

    public Book(int id, String title, int authorId, double price) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
