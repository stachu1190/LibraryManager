package com.example.libraryMS.repository;

import com.example.libraryMS.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Boolean existsBookByAuthorAndTitle(String author, String title);
    public Book findBookByAuthorAndTitle(String author, String title);
}
