package com.example.libraryMS.repository;

import com.example.libraryMS.databse.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
