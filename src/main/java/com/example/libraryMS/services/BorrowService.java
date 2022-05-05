package com.example.libraryMS.services;

import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.BookRepository;
import com.example.libraryMS.repository.BorrowRepository;
import com.example.libraryMS.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScope
@Component("BorrowService")
public class BorrowService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BorrowRepository borrowRepository;

    public Borrow getBorrow (Long borrowId){
        Optional<Borrow> borrow = borrowRepository.findById(borrowId);
        if(borrow.isEmpty())
            throw new NotFoundException("Resource not found");
        else {
            try{
                return borrow.get();
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public List<Borrow> getAllBorrow() {
        return borrowRepository.findAll();
    }

    public Borrow addBorrow(Borrow borrow) {
        checkBorrow(borrow);
        try {
            return borrowRepository.save(borrow);
        }
        catch (Exception e) {
            throw new BadRequestException("invalid request");
        }
    }

    public Borrow updateBorrow(String dateReturnedString, Long borrowId) {
        try {
            Date dateReturned =Date.valueOf(dateReturnedString);
        } catch(Exception e) {
            throw new BadRequestException("Incorrect date format");
        }
        Date dateReturned =Date.valueOf(dateReturnedString); // for setDateReturned to not show problem
        if(!borrowRepository.existsById(borrowId))
            throw new NotFoundException("Resource not found");
        Borrow borrow = borrowRepository.getOne(borrowId);
        if(borrow.getDateReturned() != null)
            if(borrow.getDateBorrowed().after(borrow.getDateReturned()))
                throw new BadRequestException("date returned cannot be earlier than date borrowed");
        try {
            borrow.setDateReturned(dateReturned);
            return borrowRepository.save(borrow);
        }
        catch(Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    public void deleteBorrow(Long borrowId) {
        if(!borrowRepository.existsById(borrowId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                borrowRepository.deleteById(borrowId);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }

        }
    }

    private void checkBorrow(Borrow borrow) {
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
