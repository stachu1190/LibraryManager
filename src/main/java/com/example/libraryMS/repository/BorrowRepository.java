package com.example.libraryMS.repository;

import com.example.libraryMS.domain.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
