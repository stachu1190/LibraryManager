package com.example.libraryMS.repository;

import com.example.libraryMS.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
