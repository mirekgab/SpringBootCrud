/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.service;

import org.springframework.data.repository.CrudRepository;
import pl.mirekgab.springbootcrud.model.Product;

/**
 *
 * @author mirek
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    
}
