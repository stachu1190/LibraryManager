package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.BookRepository;
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
    BookRepository bookRepository;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBook() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try{
                return new ResponseEntity<>(book.get(), HttpStatus.FOUND);
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
        }
        catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book,
                                           @PathVariable("bookId") Long bookId) {
        if(!bookRepository.existsById(bookId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                book.setId(bookId);
                return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable("bookId") Long bookId) {
        if(!bookRepository.existsById(bookId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                bookRepository.deleteById(bookId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }

        }
    }


}
