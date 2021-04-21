/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.order;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mirekgab.springbootcrud.model.Order;
import pl.mirekgab.springbootcrud.service.OrderRepository;

/**
 *
 * @author mirek
 */
@RestController
@RequestMapping("/order")
public class OrderRestController {
    
    private OrderRepository orderRepository;
    
    @Autowired
    public OrderRestController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @GetMapping(value="/list")
    public Iterable<Order> list() {
        return orderRepository.findAll();
    }
    
    @GetMapping("/get/{orderId}")
    public Order getOrder(@PathVariable(name = "orderId") Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.get();
    }
    
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteOrder(@PathVariable(name = "productId") Long productId) {
        if (orderRepository.existsById(productId)) {
            orderRepository.deleteById(productId);
            return ResponseEntity.status(HttpStatus.OK).body("deleted product by id=" + productId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product id=" + productId + " not found");
        }

    }

    @PostMapping(value = "/save_product", consumes = "application/json")
    public Order saveOrder(@RequestBody Order order) {
        Order c = orderRepository.save(order);
        return c;
    }    
}
