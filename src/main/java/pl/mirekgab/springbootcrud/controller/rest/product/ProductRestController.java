/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.rest.product;

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
import pl.mirekgab.springbootcrud.model.Product;
import pl.mirekgab.springbootcrud.service.ProductRepository;

/**
 *
 * @author mirek
 */
@RestController
@RequestMapping("/product")
public class ProductRestController {
    
    private ProductRepository productRepository;
    
    @Autowired
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @GetMapping(value="/list")
    public Iterable<Product> list() {
        return productRepository.findAll();
    }
    
    @GetMapping("/get/{productId}")
    public Product getProduct(@PathVariable(name = "productId") Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.get();
    }
    
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable(name = "productId") Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return ResponseEntity.status(HttpStatus.OK).body("deleted product by id=" + productId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product id=" + productId + " not found");
        }

    }

    @PostMapping(value = "/save_product", consumes = "application/json")
    public Product saveProduct(@RequestBody Product product) {
        Product c = productRepository.save(product);
        return c;
    }    
}
