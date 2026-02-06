package com.example.bookcrud.dto;

import com.example.bookcrud.validation.TitleCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

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
    private String author;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "price must be > 0")
    @Digits(integer = 10, fraction = 2, message = "price must have up to 10 digits and 2 decimals")
    private BigDecimal price;
}