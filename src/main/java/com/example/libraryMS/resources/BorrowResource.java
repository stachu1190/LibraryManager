package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Borrow;
import com.example.libraryMS.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BorrowResource {
    @Autowired
    BorrowService borrowService;

    @GetMapping("")
    public ResponseEntity<List<Borrow>> getAllBorrow() {
        return new ResponseEntity<>(borrowService.getAllBorrow(), HttpStatus.FOUND);
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable("borrowId") Long borrowId) {
        return new ResponseEntity<>(borrowService.getBorrow(borrowId), HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<Borrow> addBorrow(@RequestBody Borrow borrow) {
        return new ResponseEntity<>(borrowService.addBorrow(borrow), HttpStatus.CREATED);
    }

    @PutMapping("/{borrowId}")
    public ResponseEntity<Borrow> updateBorrow(@RequestParam("dateReturned") String dateString,
                                               @PathVariable("borrowId") Long borrowId) {
        return new ResponseEntity<>(borrowService.updateBorrow(dateString, borrowId), HttpStatus.OK);
    }

    @DeleteMapping("/{borrowId}")
    public ResponseEntity<Borrow> deleteBorrow(@PathVariable("borrowId") Long borrowId) {
        borrowService.deleteBorrow(borrowId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
