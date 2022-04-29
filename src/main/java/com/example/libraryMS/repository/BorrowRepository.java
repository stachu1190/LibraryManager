package com.example.libraryMS.repository;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    public List<Borrow> findAllByBookAndDateReturnedIsNull(Book book);
}
