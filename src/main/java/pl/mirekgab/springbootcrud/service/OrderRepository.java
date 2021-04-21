/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.service;

import org.springframework.data.repository.CrudRepository;
import pl.mirekgab.springbootcrud.model.Order;

/**
 *
 * @author mirek
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
    
}
