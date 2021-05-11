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
import pl.mirekgab.springbootcrud.model.OrderPosition;
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
        //get all clients
        RestTemplate restTemplate = new RestTemplate();
        String clientRestPath = "/client/list";
        URI clientUri = mirekgabUriBuilder.buildUri(clientRestPath);
        ResponseEntity<Client[]> clientResponseEntity = restTemplate.getForEntity(clientUri, Client[].class);
        model.addAttribute("clients", clientResponseEntity.getBody());

        if (orderId != null) {
            ResponseEntity<Order> order = getOrderDetails(orderId);
            model.addAttribute("order", order.getBody());
            model.addAttribute("clients", clientResponseEntity.getBody());

        } else {
            model.addAttribute("order", new Order());
        }
        return "edit_order";
    }

    private ResponseEntity<Order> getOrderDetails(Long orderId) {
        RestTemplate restTemplate = new RestTemplate();

        //"http://localhost:8080/product/get/{productId}"
        Map<String, Long> uriParameters = new HashMap<>();
        uriParameters.put("orderId", orderId);
        String restPath = "/order/get/{orderId}";
        URI uri = mirekgabUriBuilder.buildUri(restPath, uriParameters);

        ResponseEntity<Order> responseEntity = restTemplate.getForEntity(uri, Order.class);
        return responseEntity;
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

    @GetMapping(value = "delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId) {

        RestTemplate rt = new RestTemplate();

        String restPath = "/order/delete/{orderId}";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("orderId", orderId);
        URI uri = mirekgabUriBuilder.buildUri(restPath, parameters);

        rt.delete(uri);

        return "redirect:/orderhtml/list";
    }

    @GetMapping(value = "edit_position/{positionId}")
    public String editPosition(@PathVariable("positionId") Long positionId, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String restPath = "/order_position/get/{orderPositionId}";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("orderPositionId", positionId);
        URI uri = mirekgabUriBuilder.buildUri(restPath, parameters);
        ResponseEntity<OrderPosition> orderPosition = restTemplate.getForEntity(uri, OrderPosition.class);
        model.addAttribute("orderPosition", orderPosition.getBody());

        //load products list
        uri = mirekgabUriBuilder.buildUri("/product/list");
        ResponseEntity<Product[]> responseEntityProductsList = restTemplate.getForEntity(uri, Product[].class);
        Product[] productsList = responseEntityProductsList.getBody();
        model.addAttribute("productsList", productsList);

        Order order = getOrderDetails(positionId).getBody();
        System.out.println(order.getOrderPositions());
        model.addAttribute("order", order);

        return "edit_position";
    }

    @PostMapping(value = "/save_position")
    public String savePosition(OrderPosition orderPosition) {
        RestTemplate rt = new RestTemplate();

        //"http://localhost:8080/client/save_client";
        String restPath = "/order_position/save";
        URI uri = mirekgabUriBuilder.buildUri(restPath);

        ResponseEntity<Order> re = rt.postForEntity(uri, orderPosition, Order.class);
        return "redirect:/orderhtml/list";
    }
    
    @GetMapping(value = "delete_position/{positionId}")
    public String deleteOrderPosition(@PathVariable("positionId") Long positionId) {

        RestTemplate rt = new RestTemplate();

        String restPath = "/order_position/delete/{orderId}";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("orderId", positionId);
        URI uri = mirekgabUriBuilder.buildUri(restPath, parameters);

        rt.delete(uri);

        return "redirect:/orderhtml/list";
    }    
}
