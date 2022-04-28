package com.example.libraryMS.repository;

import com.example.libraryMS.databse.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
