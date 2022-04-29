package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
import com.example.libraryMS.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrows")
public class BorrowResource {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BorrowRepository borrowRepository;

    @GetMapping("")
    public ResponseEntity<List<Borrow>> getAllBorrow() {
        return new ResponseEntity<>(borrowRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable("borrowId") Long borrowId) {
        Optional<Borrow> borrow = borrowRepository.findById(borrowId);
        if(!borrow.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try{
                return new ResponseEntity<>(borrow.get(), HttpStatus.FOUND);
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    @PostMapping("")
    public ResponseEntity<Borrow> addBorrow(@RequestBody Borrow borrow) {
        checkBorrow(borrow);
        try {
            return new ResponseEntity<>(borrowRepository.save(borrow), HttpStatus.CREATED);
        }
        catch (Exception e) {
            throw new BadRequestException("invalid request");
        }
    }

    @PutMapping("/{borrowId}/{dateReturned}")
    public ResponseEntity<Borrow> updateBorrow(@PathVariable("dateReturned") @DateTimeFormat(pattern="dd-MM-yyyy") Date date,
                                           @PathVariable("borrowId") Long borrowId) {
        if(!borrowRepository.existsById(borrowId))
            throw new NotFoundException("Resource not found");
        Borrow borrow = borrowRepository.getOne(borrowId);
        if(borrow.getDateReturned() != null)
            if(borrow.getDateBorrowed().after(borrow.getDateReturned()))
                throw new BadRequestException("date returned cannot be earlier than date borrowed");
        try {
            borrow.setDateReturned(date);
            return new ResponseEntity<>(borrowRepository.save(borrow), HttpStatus.CREATED);
        }
        catch(Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @DeleteMapping("/{borrowId}")
    public ResponseEntity<Borrow> deleteBorrow(@PathVariable("borrowId") Long borrowId) {
        if(!borrowRepository.existsById(borrowId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                borrowRepository.deleteById(borrowId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }

        }
    }

    public void checkBorrow(Borrow borrow) {
        if(!bookRepository.existsById(borrow.getBook().getId()))
            throw new BadRequestException("book with given id does not exist");
        if(!clientRepository.existsById(borrow.getClient().getId()))
            throw new BadRequestException("client does not exist");
        if(borrow.getDateReturned() != null)
            if(borrow.getDateBorrowed().after(borrow.getDateReturned()))
                throw new BadRequestException("date returned cannot be earlier than date borrowed");
        List<Borrow> borrows = borrowRepository.findAllByBookAndDateReturnedIsNull(borrow.getBook());
        if(borrows.size() >= bookRepository.getOne(borrow.getBook().getId()).getQuantity())
            throw new BadRequestException("all books are already borrowed");
    }
}
