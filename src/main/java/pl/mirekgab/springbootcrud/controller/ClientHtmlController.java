/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.mirekgab.springbootcrud.MirekgabUriBuilder;
import pl.mirekgab.springbootcrud.MyProperties;
import pl.mirekgab.springbootcrud.model.Client;

/**
 *
 * @author mirek
 */
@RequestMapping("/clienthtml")
@Controller
public class ClientHtmlController {

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private MirekgabUriBuilder mirekgabUriBuilder;

    @GetMapping("/list")
    public String clientList(Model model) {
        RestTemplate rt = new RestTemplate();
        rt.setMessageConverters(
                Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON));

        ResponseEntity<Client[]> re = rt.getForEntity("http://localhost:8080/client/list", Client[].class);

        model.addAttribute("clients", re.getBody());
        return "clients";
    }

    @GetMapping(value = {"/edit", "/edit/{clientId}"})
    public String getClient(@PathVariable(required = false, value = "clientId") Long clientId, Model model) {
        if (clientId != null) {
            RestTemplate restTemplate = new RestTemplate();

            //"http://localhost:8080/client/get/{clientId}"
            Map<String, Long> uriParameters = new HashMap<>();
            uriParameters.put("clientId", clientId);
            String restPath = "/get/{clientId}";
            URI uri = mirekgabUriBuilder.buildUri(restPath, uriParameters);

            ResponseEntity<Client> responseEntity = restTemplate.getForEntity(uri, Client.class);
            model.addAttribute("client", responseEntity.getBody());
        } else {
            model.addAttribute("client", new Client());
        }
        return "edit_client";
    }

    @PostMapping(value = "/save_client")
    public String saveClient(Client client, HttpServletResponse response) {
        RestTemplate rt = new RestTemplate();

        //"http://localhost:8080/client/save_client";
        String restPath = "/save_client";
        URI uri = mirekgabUriBuilder.buildUri(restPath);

        ResponseEntity<Client> re = rt.postForEntity(uri, client, Client.class);
        return "redirect:/clienthtml/list";
    }

    @GetMapping(value = "delete/{clientId}")
    public String deleteClient(@PathVariable("clientId") Long clientId) {

        RestTemplate rt = new RestTemplate();
        
        //http://localhost:8080/client/delete/{clientId}
        String restPath = "/delete/{clientId}";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("clientId", clientId);
        URI uri = mirekgabUriBuilder.buildUri(restPath, parameters);
        
        rt.delete(uri);

        return "redirect:/clienthtml/list";
    }

}
