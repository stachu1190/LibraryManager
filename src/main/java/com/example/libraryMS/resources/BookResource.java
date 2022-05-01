package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
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

    @Autowired
    BorrowRepository borrowRepository;

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

    @GetMapping("/left/{bookId}")
    public ResponseEntity<Integer> getBookLeftById(@PathVariable("bookId") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try{
                List<Borrow> borrows = borrowRepository.findAllByBookAndDateReturnedIsNull(book.get());
                System.out.println(book.get().getQuantity() - borrows.size());
                return new ResponseEntity<>(book.get().getQuantity() - borrows.size(), HttpStatus.FOUND);
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if(book.getQuantity()<=0)
            throw new BadRequestException("quantity value must be bigger than 0");
        try {
            if(!bookRepository.existsBookByAuthorAndTitle(book.getAuthor(), book.getTitle()))
                return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
            else {
                Book updateBook = bookRepository.findBookByAuthorAndTitle(book.getAuthor(), book.getTitle());
                updateBook.setQuantity(updateBook.getQuantity() + book.getQuantity());
                return new ResponseEntity<>(bookRepository.save(updateBook), HttpStatus.OK);
            }
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
    @DeleteMapping("/{bookId}/{quantity}")
    public ResponseEntity<Book> reduceQuantity(@PathVariable("bookId") Long bookId,
                                               @PathVariable("quantity") int quantity) {
        if(!bookRepository.existsById(bookId))
            throw new NotFoundException("Resource not found");
        else {
            if(bookRepository.getOne(bookId).getQuantity() - quantity < 0)
                throw new BadRequestException("book quantity cannot be negative");
            try {
                Book updateBook = bookRepository.getOne(bookId);
                updateBook.setQuantity(updateBook.getQuantity() - quantity);
                return new ResponseEntity<>(bookRepository.save(updateBook), HttpStatus.OK);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }
}
