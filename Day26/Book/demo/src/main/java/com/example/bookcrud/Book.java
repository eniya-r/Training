package com.example.bookcrud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 120)
	private String title;

	@Column(nullable = false, length = 80)
	private String author;

	// Matches PostgreSQL NUMERIC
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
}