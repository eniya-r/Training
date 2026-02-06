package com.example.bookcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    private Integer id;
    private String title;
    private String author;
    private BigDecimal price;

    private Date createdAt;
    private Date updatedAt;
}