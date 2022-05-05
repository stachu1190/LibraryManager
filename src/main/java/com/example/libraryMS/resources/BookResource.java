package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
import com.example.libraryMS.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookResource {
    @Autowired
    BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBook() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.FOUND);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") Long bookId) {
        return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.FOUND);
    }

    @GetMapping("/left/{bookId}")
    public ResponseEntity<Integer> getBookLeftById(@PathVariable("bookId") Long bookId) {
        return new ResponseEntity<>(bookService.getBookLeft(bookId), HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book,
                                           @PathVariable("bookId") Long bookId) {
        return new ResponseEntity<>(bookService.updateBook(book, bookId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{bookId}/{quantity}")
    public ResponseEntity<Book> reduceQuantity(@PathVariable("bookId") Long bookId,
                                               @PathVariable("quantity") int quantity) {
        return new ResponseEntity<>(bookService.deleteQuantity(bookId, quantity), HttpStatus.OK);
    }
}
