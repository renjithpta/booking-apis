package com.ibs.iairport.commerce.repository;

import com.ibs.iairport.commerce.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    long deleteByTitle(String title);
}

