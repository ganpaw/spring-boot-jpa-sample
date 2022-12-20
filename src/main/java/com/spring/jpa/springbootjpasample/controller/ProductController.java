package com.spring.jpa.springbootjpasample.controller;

import com.spring.jpa.springbootjpasample.exception.CaughtCustomException;
import com.spring.jpa.springbootjpasample.exception.UnCaughtException;
import com.spring.jpa.springbootjpasample.model.Product;
import com.spring.jpa.springbootjpasample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/jpa/crud")
public class ProductController {

    private static final String PRODUCTS = "products";
    private static final String PRODUCT = "product";

    @Autowired
    private ProductService productService;

    @PostMapping(PRODUCT)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @PostMapping(PRODUCTS)
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
        List<Product> addedProducts = productService.saveProducts(products);
        return new ResponseEntity<>(addedProducts, HttpStatus.CREATED); // another way of creating ResponseEntity
    }

    @GetMapping(PRODUCTS)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(PRODUCT + "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping(PRODUCT)
    public ResponseEntity<Product> getProductByName(@RequestParam(required = true) String name) {
        Product product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }

    @PutMapping(PRODUCT)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws Exception {
        Product updateProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping(PRODUCT + "/{id}")
    public ResponseEntity<HttpStatus> removeProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PRODUCTS)
    public ResponseEntity<HttpStatus> removeAllProducts() {
        productService.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("unavaiable")
    public ResponseEntity<HttpStatus> unavailableTest() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/rse")
    public String withResponseStatusException() {
        try {
            throw new RuntimeException("Error Occurred");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HTTP Status will be NOT FOUND (CODE 404)\n");
        }
    }


    @GetMapping("/caught")
    public String caughtException() {
        throw new CaughtCustomException("Caught Exception Thrown");
    }

    @GetMapping("/uncaught")
    public String unCaughtException() {
        throw new UnCaughtException("The HTTP Status will be BAD REQUEST (CODE 400)\n");
    }
}
