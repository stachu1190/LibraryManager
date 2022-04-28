package com.example.libraryMS;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.domain.Client;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
import com.example.libraryMS.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(BookRepository bookRepository, ClientRepository clientRepository, BorrowRepository borrowRepository) {
//        return args -> {
//            Book book = new Book(
//                    "The Idiot",
//                    "Fyodor Dostoevsky",
//                    1
//            );
//            bookRepository.save(book);
//            Client client = new Client(
//                    "Stanisław",
//                    "Zielinski",
//                    "stachu@stachu.pl",
//                    "999"
//            );
//            Date date = new Date();
//            Borrow borrow = new Borrow(
//                    book,
//                    client,
//                    date
//            );
//            borrowRepository.save(borrow);
//        };
//    }

}
