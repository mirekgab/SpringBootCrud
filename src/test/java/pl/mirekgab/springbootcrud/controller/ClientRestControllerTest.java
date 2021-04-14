/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import pl.mirekgab.springbootcrud.model.Client;
import pl.mirekgab.springbootcrud.service.ClientRepository;

/**
 *
 * @author mirek
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class ClientRestControllerTest {

    @Autowired
    ClientRepository clientRepository;

    @LocalServerPort
    private int port;

    static RestTemplate restTemplate;

    public ClientRestControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        restTemplate = new RestTemplate();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
//        clientRepository.deleteAll();
//        clientRepository.save(new Client("client1"));
//        clientRepository.save(new Client("client2"));
//        clientRepository.save(new Client("client3"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clientList method, of class ClientRestController.
     */
    @Test
    public void testClientList() {
        System.out.println("clientList");
        Client[] clients = restTemplate.getForObject("http://localhost:"+port+"/client/list", Client[].class);

        assertEquals(clients.length, 3);
    }

    /**
     * Test of getClient method, of class ClientRestController.
     */
    @Test
    public void testGetClient() {
        System.out.println("getClient");
        Long clientId = 1L;

        Client result = restTemplate.getForObject("http://localhost:"+port+"/client/get/" + clientId, Client.class);
        assertEquals(result.getClientName(), "client1");
    }

    /**
     * Test of deleteClient method, of class ClientRestController.
     */
    @Test
    public void testDeleteClient() {
        System.out.println("deleteClient");
        assertTrue(true);
    }

    /**
     * Test of saveClient method, of class ClientRestController.
     */
    @Test
    public void testSaveClient() {
        System.out.println("saveClient");
        Client client = new Client("testClient");
        ResponseEntity<Client> newClient = restTemplate.postForEntity("http://localhost:"+port+"/client/save_client", client, Client.class);

        assertEquals(newClient.getStatusCode(), HttpStatus.OK);
        long newId = newClient.getBody().getClientId();
        assertEquals(newId, 4L);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
