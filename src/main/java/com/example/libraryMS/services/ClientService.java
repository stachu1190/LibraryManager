package com.example.libraryMS.services;

import com.example.libraryMS.domain.Client;
import com.example.libraryMS.exceptions.BadRequestException;
import com.example.libraryMS.exceptions.NotFoundException;
import com.example.libraryMS.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Optional;

@ApplicationScope
@Component("ClientService")
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long clientId){
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent())
            throw new NotFoundException("Resource not found");
        else {
            try {
                return client.get();
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public Client updateClient(Client client, Long clientId){
        if (!clientRepository.existsById(clientId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                client.setId(clientId);
                return clientRepository.save(client);
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public void deleteClient(Long clientId){
        if (!clientRepository.existsById(clientId))
            throw new NotFoundException("Resource not found");
        else {
            try {
                clientRepository.deleteById(clientId);
            } catch (Exception e) {
                throw new BadRequestException("Invalid request");
            }
        }
    }

    public Client addClient(Client client){
        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }
}
