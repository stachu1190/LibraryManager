package com.example.libraryMS.services;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Optional;


@ApplicationScope
@Component("BookService")
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowRepository borrowRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try{
                return book.get();
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public int getBookLeft(Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try{
                List<Borrow> borrows = borrowRepository.findAllByBookAndDateReturnedIsNull(book.get());
                System.out.println(book.get().getQuantity() - borrows.size());
                return book.get().getQuantity() - borrows.size();
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public Book addBook(Book book){
        if(book.getQuantity()<=0)
            throw new BadRequestException("quantity value must be bigger than 0");
        try {
            if(!bookRepository.existsBookByAuthorAndTitle(book.getAuthor(), book.getTitle()))
                return bookRepository.save(book);
            else {
                Book updateBook = bookRepository.findBookByAuthorAndTitle(book.getAuthor(), book.getTitle());
                updateBook.setQuantity(updateBook.getQuantity() + book.getQuantity());
                return bookRepository.save(updateBook);
            }
        }
        catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    public Book updateBook(Book book, Long bookId){
        if(!bookRepository.existsById(bookId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                book.setId(bookId);
                return bookRepository.save(book);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public void deleteBook(Long bookId){
        if(!bookRepository.existsById(bookId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                bookRepository.deleteById(bookId);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }

        }
    }

    public Book deleteQuantity(Long bookId, int quantity){
        if(!bookRepository.existsById(bookId))
            throw new NotFoundException("Resource not found");
        else {
            if(bookRepository.getOne(bookId).getQuantity() - quantity < 0)
                throw new BadRequestException("book quantity cannot be negative");
            try {
                Book updateBook = bookRepository.getOne(bookId);
                updateBook.setQuantity(updateBook.getQuantity() - quantity);
                return bookRepository.save(updateBook);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }


}
