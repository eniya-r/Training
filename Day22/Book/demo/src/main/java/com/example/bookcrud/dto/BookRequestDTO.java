package com.example.bookcrud.dto;

import com.example.bookcrud.validation.TitleCase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BookRequestDTO {

    @NotBlank(message = "title is required")
    @Size(max = 120, message = "title must be at most 120 characters")
    @TitleCase
    private String title;

    @NotBlank(message = "author is required")
    @Size(max = 80, message = "author must be at most 80 characters")
    private String author;

    @Positive(message = "price must be > 0")
    private double price;

    public BookRequestDTO() {}

    public BookRequestDTO(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}