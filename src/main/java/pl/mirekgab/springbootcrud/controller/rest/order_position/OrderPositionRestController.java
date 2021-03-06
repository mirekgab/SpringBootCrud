/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.rest.order_position;

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
import pl.mirekgab.springbootcrud.model.OrderPosition;
import pl.mirekgab.springbootcrud.service.OrderPositionRepository;

/**
 *
 * @author mirek
 */
@RestController
@RequestMapping("/order_position")
public class OrderPositionRestController {
    
    private OrderPositionRepository orderPositionRepository;
    
    @Autowired
    public OrderPositionRestController(OrderPositionRepository orderPositionRepository) {
        this.orderPositionRepository = orderPositionRepository;
    }
    
    @GetMapping("/list")
    public Iterable<OrderPosition> list() {
        return orderPositionRepository.findAll();
    }
    
    @GetMapping("/get/{orderPositionId}")
    public OrderPosition getOrderPosition(@PathVariable("orderPositionId") Long orderPositionId) {
        Optional<OrderPosition> orderPosition = orderPositionRepository.findById(orderPositionId);
        return orderPosition.get();
    }
    
    @PostMapping("/save")
    public OrderPosition saveOrderPosition(@RequestBody OrderPosition orderPosition) {
        return orderPositionRepository.save(orderPosition);
    }
    
    @DeleteMapping("/delete/{positionId}")
    public ResponseEntity deletePosition(@PathVariable("positionId") Long positionId) {
        if (orderPositionRepository.existsById(positionId)) {
            orderPositionRepository.deleteById(positionId);
            return ResponseEntity.status(HttpStatus.OK).body("delete order position by id="+positionId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("position id=" + positionId + " not found");
        }
    }
}
