/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.client;

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
//@RestController
//@RequestMapping("/client")
public class ClientRestHATEOASController {

    private ClientRepository clientRepository;

    @Autowired
    public ClientRestHATEOASController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(value="/list", produces="application/hal+json")
    public CollectionModel<Client> clientList() {
        Iterable <Client> clients = clientRepository.findAll();
        for (final Client c : clients) {
            Link selfLink = linkTo(ClientRestHATEOASController.class).slash("get").slash(c.getClientId()).withSelfRel().withType("get");
            Link deleteLink = linkTo(ClientRestHATEOASController.class).slash("delete").slash(c.getClientId())
                    .withRel("delete").withType("delete");
            Link saveClientLink = linkTo(ClientRestHATEOASController.class)
                    .slash("save_client")
                    .withRel("update client")
                    .withType("post");
                    
//Client class must extends RepresentationModel<Client>
//            c.add(selfLink);
//            c.add(deleteLink);
//            c.add(saveClientLink);
        }
        Link link = linkTo(ClientRestHATEOASController.class).slash("list").withRel("allClients");
        CollectionModel<Client> result = CollectionModel.of(clients, link);

        return result;
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
        System.out.println("rest controller " + client);
        Client c = clientRepository.save(client);
        return c;
    }
}
