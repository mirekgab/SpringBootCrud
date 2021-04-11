/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.mirekgab.springbootcrud.model.Client;

/**
 *
 * @author mirek
 */
@RequestMapping("/clienthtml")
@Controller
public class ClientHtmlController {

    @GetMapping("/list")
    public String clientList(Model model) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Client[]> re = rt.getForEntity("http://localhost:8080/client/list", Client[].class);        
        model.addAttribute("clients", re.getBody());
        return "clients";
    }
    
    @GetMapping("/edit/{clientId}")
    public String getClient(@PathVariable("clientId") Long clientId, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Client> responseEntity = restTemplate.getForEntity("http://localhost:8080/client/get/"+clientId, Client.class);
        model.addAttribute("client", responseEntity.getBody());
        return "edit_client";
    }
    
    @PostMapping("/save_client") 
    @ResponseBody
    public Client saveClient(@ModelAttribute Client client) {
        return client;
    }
}
