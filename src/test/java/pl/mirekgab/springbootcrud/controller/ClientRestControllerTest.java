/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.mirekgab.springbootcrud.SpringBootCrudApplication;
import pl.mirekgab.springbootcrud.model.Client;
import pl.mirekgab.springbootcrud.service.ClientRepository;

/**
 *
 * @author mirek
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT ,classes = SpringBootCrudApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository clientRepository;
    

    public ClientRestControllerTest() {
        
    }

    @Test
    public void givenClient_whenGetClient_thenStatus200()
            throws Exception {

        clientRepository.save(new Client(1L, "client1"));

        ResultActions ra = mvc.perform(get("/client/get/1")
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult andReturn = ra.andReturn();
        System.out.println("wynik testu");
        System.out.println(andReturn.getResponse().getStatus());
        
        String results = andReturn.getResponse().getContentAsString();
        Client client = new Client(1L,"client1");
        ObjectMapper objectMapper = new ObjectMapper();
        String r1;
        String c1 = objectMapper.writeValueAsString(client);
        
        assertEquals(results, c1);
        
        ra.andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("clientName", is("client1")))
                .andExpect(jsonPath("clientId", is(1)));
    }
    
    @Test
    public void getClient() throws Exception {
        Client c = new Client(2L, "client2");
        clientRepository.save(c);
        
        MvcResult mvcResult = mvc.perform(get("/client/get/2").contentType(MediaType.APPLICATION_JSON)).andReturn();
        String stringResult = mvcResult.getResponse().getContentAsString();
        
        ObjectMapper objectMapper = new ObjectMapper();
        String s1 = objectMapper.writeValueAsString(c);
        assertEquals(stringResult, s1);
        
    }

}
