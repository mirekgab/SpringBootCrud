/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mirekgab.springbootcrud.model.Client;
import pl.mirekgab.springbootcrud.service.ClientRepository;

/**
 *
 * @author mirek
 */
@RestController
@RequestMapping("/client")
public class ClientRestController {

    private ClientRepository clientRepository;

    @Autowired
    public ClientRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/list")
    public Iterable<Client> clientList() {
        return clientRepository.findAll();
    }
    
    @GetMapping("/get/{clientId}")
    public Client getClient(@PathVariable("clientId") Long clientId) {
        Optional<Client> findClient = clientRepository.findById(clientId);
        
        return findClient.get();
    }



    @GetMapping("/delete/{clientId}")
    public String deleteClient(@PathVariable(name = "clientId") Long clientId) {
        clientRepository.deleteById(clientId);
        return "deleted client by id=" + clientId;
    }

    @PostMapping("/save_client") 
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
