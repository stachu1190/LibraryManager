package com.example.libraryMS.repository;

import com.example.libraryMS.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
