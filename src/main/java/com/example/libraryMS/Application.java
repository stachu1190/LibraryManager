package com.example.libraryMS;

import com.example.libraryMS.databse.Book;
import com.example.libraryMS.databse.Borrow;
import com.example.libraryMS.databse.Client;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
import com.example.libraryMS.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository, ClientRepository clientRepository, BorrowRepository borrowRepository) {
        return args -> {
            Book book = new Book(
                    "Crime and Punishment",
                    "Fyodor Dostoevsky",
                    10
            );
            bookRepository.save(book);
            Client client = new Client(
                    "Mikolaj",
                    "Zurawski",
                    "zuretto@zurek.pl",
                    "997"
            );
            clientRepository.save(client);
            Date date = new Date();
            Borrow borrow = new Borrow(
                    book,
                    client,
                    date
            );
            borrowRepository.save(borrow);
        };
    }

}
