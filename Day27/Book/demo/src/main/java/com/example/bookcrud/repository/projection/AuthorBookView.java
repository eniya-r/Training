package com.example.bookcrud.repository.projection;

import java.math.BigDecimal;

public interface AuthorBookView {
    Integer getId();
    String getTitle();
    BigDecimal getPrice();
}
