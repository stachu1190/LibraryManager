package com.example.libraryMS.resources;

import com.example.libraryMS.domain.Book;
import com.example.libraryMS.domain.Client;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.ClientRepository;
import com.example.libraryMS.services.ClientService;
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
    ClientService clientService;

    @GetMapping("")
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.FOUND);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable("clientId") Long clientId) {
        return new ResponseEntity<>(clientService.getClientById(clientId), HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.CREATED);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@RequestBody Client client,
                                               @PathVariable("clientId") Long clientId) {
        return new ResponseEntity<>(clientService.updateClient(client, clientId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Book> deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
