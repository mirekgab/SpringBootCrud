/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.controller.product;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.mirekgab.springbootcrud.MirekgabUriBuilder;
import pl.mirekgab.springbootcrud.model.Client;
import pl.mirekgab.springbootcrud.model.Product;
import pl.mirekgab.springbootcrud.service.ProductRepository;

/**
 *
 * @author mirek
 */
@Controller
@RequestMapping("/producthtml")
public class ProductHtmlController {

    @Autowired
    private MirekgabUriBuilder mirekgabUriBuilder;

    @GetMapping("/list")
    public String productsList(Model model) {
        RestTemplate rt = new RestTemplate();
        rt.setMessageConverters(
                Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON));

        ResponseEntity<Product[]> re = rt.getForEntity("http://localhost:8080/product/list", Product[].class);

        model.addAttribute("products", re.getBody());
        return "products";
    }

    @GetMapping(value = {"/edit", "/edit/{productId}"})
    public String getProduct(@PathVariable(required = false, value = "productId") Long productId, Model model) {
        if (productId != null) {
            RestTemplate restTemplate = new RestTemplate();

            //"http://localhost:8080/product/get/{productId}"
            Map<String, Long> uriParameters = new HashMap<>();
            uriParameters.put("productId", productId);
            String restPath = "/product/get/{productId}";
            URI uri = mirekgabUriBuilder.buildUri(restPath, uriParameters);

            ResponseEntity<Product> responseEntity = restTemplate.getForEntity(uri, Product.class);
            model.addAttribute("product", responseEntity.getBody());
        } else {
            model.addAttribute("product", new Product());
        }
        return "edit_product";
    }

    @PostMapping(value = "/save_product")
    public String saveClient(Product product, HttpServletResponse response) {
        RestTemplate rt = new RestTemplate();

        //"http://localhost:8080/client/save_client";
        String restPath = "/product/save_product";
        URI uri = mirekgabUriBuilder.buildUri(restPath);

        ResponseEntity<Product> re = rt.postForEntity(uri, product, Product.class);
        return "redirect:/producthtml/list";
    }    
    
    @GetMapping(value = "delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {

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
