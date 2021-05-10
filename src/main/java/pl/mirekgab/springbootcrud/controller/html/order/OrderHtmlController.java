/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.html.order;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
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
import pl.mirekgab.springbootcrud.model.Client;
import pl.mirekgab.springbootcrud.model.Order;
import pl.mirekgab.springbootcrud.model.Product;

/**
 *
 * @author mirek
 */
@Controller
@RequestMapping("/orderhtml")
public class OrderHtmlController {

    @Autowired
    private MirekgabUriBuilder mirekgabUriBuilder;

    @GetMapping("/list")
    public String ordersList(Model model) {
        RestTemplate rt = new RestTemplate();
        rt.setMessageConverters(
                Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON));

        ResponseEntity<Order[]> re = rt.getForEntity("http://localhost:8080/order/list", Order[].class);

        model.addAttribute("orders", re.getBody());
        return "orders";
    }

    @GetMapping(value = {"/edit", "/edit/{orderId}"})
    public String getOrder(@PathVariable(required = false, value = "orderId") Long orderId, Model model) {
        if (orderId != null) {
            RestTemplate restTemplate = new RestTemplate();

            //"http://localhost:8080/product/get/{productId}"
            Map<String, Long> uriParameters = new HashMap<>();
            uriParameters.put("orderId", orderId);
            String restPath = "/order/get/{orderId}";
            URI uri = mirekgabUriBuilder.buildUri(restPath, uriParameters);

            ResponseEntity<Order> responseEntity = restTemplate.getForEntity(uri, Order.class);
            model.addAttribute("order", responseEntity.getBody());
            
            //get all clients
            Map<String,Long> uriParametersClients = new HashMap<>();
            String clientRestPath = "/client/list";
            URI clientUri = mirekgabUriBuilder.buildUri(clientRestPath);
            ResponseEntity<Client[]> clientResponseEntity = restTemplate.getForEntity(clientUri, Client[].class);
            model.addAttribute("clients", clientResponseEntity.getBody());
            
        } else {
            RestTemplate restTemplate = new RestTemplate();
            model.addAttribute("order", new Order());
            //get all clients
            Map<String,Long> uriParametersClients = new HashMap<>();
            String clientRestPath = "/client/list";
            URI clientUri = mirekgabUriBuilder.buildUri(clientRestPath);
            ResponseEntity<Client[]> clientResponseEntity = restTemplate.getForEntity(clientUri, Client[].class);
            model.addAttribute("clients", clientResponseEntity.getBody());
        }
        return "edit_order";
    }

    @PostMapping(value = "/save_order")
    public String saveOrder(Order order, HttpServletResponse response) {
        RestTemplate rt = new RestTemplate();

        //"http://localhost:8080/client/save_client";
        String restPath = "/order/save_order";
        URI uri = mirekgabUriBuilder.buildUri(restPath);

        ResponseEntity<Order> re = rt.postForEntity(uri, order, Order.class);
        return "redirect:/orderhtml/list";
    }    
    
    @GetMapping(value = "delete/{productId}")
    public String deleteOrder(@PathVariable("productId") Long productId) {

        RestTemplate rt = new RestTemplate();
        
        //http://localhost:8080/client/delete/{clientId}
        String restPath = "/product/delete/{productId}";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("productId", productId);
        URI uri = mirekgabUriBuilder.buildUri(restPath, parameters);
        
        rt.delete(uri);

        return "redirect:/producthtml/list";
    }    
}
