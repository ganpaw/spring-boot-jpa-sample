package com.spring.jpa.springbootjpasample.service;

import com.spring.jpa.springbootjpasample.dao.ProductRepository;
import com.spring.jpa.springbootjpasample.exception.ResourceNotFoundException;
import com.spring.jpa.springbootjpasample.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // We can either use below constructor (@Autowired not needed after Spring 5) Or use Lomboc's @RequiredArgsConstructor at class level
    ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // CREATE
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    // GET
    public Product getProduct(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for id:" + id));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Product not found for name:" + name));
    }

    // UPDATE
    public Product updateProduct(Product product) {
        Product updateProduct = getProduct(product.getId());
        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        return productRepository.save(updateProduct);
    }

    // DELETE
    public Integer deleteProduct(int id) {
        Product deleteProduct = getProduct(id);
        productRepository.deleteById(deleteProduct.getId());
        return id;
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
