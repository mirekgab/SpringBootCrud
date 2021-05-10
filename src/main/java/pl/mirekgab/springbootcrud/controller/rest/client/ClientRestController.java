/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.rest.client;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(value="/list", produces="application/json")
    public Iterable<Client> clientList() {
        Iterable<Client> clients = clientRepository.findAll();
        return clients;
    }

    @GetMapping("/get/{clientId}")
    public Client getClient(@PathVariable("clientId") Long clientId) {
        Optional<Client> findClient = clientRepository.findById(clientId);
        return findClient.get();
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteClient(@PathVariable(name = "clientId") Long clientId) {
        if (clientRepository.existsById(clientId)) {
            clientRepository.deleteById(clientId);
            return ResponseEntity.status(HttpStatus.OK).body("deleted client by id=" + clientId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("client id=" + clientId + " not found");
        }

    }

    @PostMapping(value = "/save_client", consumes = "application/json")
    public Client saveClient(@RequestBody Client client) {
        Client c = clientRepository.save(client);
        return c;
    }
}
