package com.example.bookcrud.dto;

import com.example.bookcrud.validation.TitleCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "title is required")
    @Size(max = 120, message = "title must be at most 120 characters")
    @TitleCase
    private String title;

    @NotBlank(message = "author is required")
    @Size(max = 80, message = "author must be at most 80 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "author name must contain only letters")
    private String author;

    @Positive(message = "price must be > 0")
    private double price;
}