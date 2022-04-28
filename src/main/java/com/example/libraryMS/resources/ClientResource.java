package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Client;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientResource {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("")
    public ResponseEntity<List<Client>> getAllBook() {
        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getBookById(@PathVariable("clientId") Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if(!client.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try{
                return new ResponseEntity<>(client.get(), HttpStatus.FOUND);
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    @PostMapping("")
    public ResponseEntity<Client> addBook(@RequestBody Client client) {
        try {
            return new ResponseEntity<>(clientRepository.save(client), HttpStatus.CREATED);
        }
        catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateBook(@RequestBody Client client,
                                           @PathVariable("clientId") Long clientId) {
        if(!clientRepository.existsById(clientId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                client.setId(clientId);
                return new ResponseEntity<>(clientRepository.save(client), HttpStatus.CREATED);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Book> deleteBook(@PathVariable("clientId") Long clientId) {
        if(!clientRepository.existsById(clientId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                clientRepository.deleteById(clientId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            catch(Exception e) {
                throw new BadRequestException("Invalid request");
            }

        }
    }


}
