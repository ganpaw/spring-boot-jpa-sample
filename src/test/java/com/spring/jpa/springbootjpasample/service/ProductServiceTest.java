package com.spring.jpa.springbootjpasample.service;

import com.spring.jpa.springbootjpasample.dao.ProductRepository;
import com.spring.jpa.springbootjpasample.exception.ResourceNotFoundException;
import com.spring.jpa.springbootjpasample.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * Unit test - no use of spring. Just Junit, Mockito and Assertions. Requires ProductService constructor injection/autowiring instead of field autowiring
 * https://reflectoring.io/unit-testing-spring-boot/
 */
public class ProductServiceTest {
    private ProductService productService;

    private Product product1;
    private Product product2;
    private List<Product> products;
    private Product productToUpdate;
    private Product productToUpdateNotFound;

    @BeforeEach
    public void setup() {
        product1 = new Product(1, "Macbook Pro", 5, 999.99);
        product2 = new Product(2, "Microsoft XBox", 10, 399.99);
        products = Stream.of(product1, product2).collect(Collectors.toList());
        productToUpdate = new Product(1, "Macbook Pro", 50, 1999.99);
        productToUpdateNotFound = new Product(3, "Macbook Pro", 100, 1599.99);

        ProductRepository productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        when(productRepository.saveAll(products)).thenReturn(products);
        when(productRepository.save(product1)).thenReturn(product1);
        when(productRepository.save(product2)).thenReturn(product2);

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        when(productRepository.findById(3)).thenThrow(new ResourceNotFoundException("product not found"));

        when(productRepository.findByName("Macbook Pro")).thenReturn(Optional.ofNullable(product1));
        when(productRepository.findByName("unknown")).thenThrow(new ResourceNotFoundException("product not found"));

        when(productRepository.save(productToUpdate)).thenReturn(productToUpdate);
        when(productRepository.save(productToUpdateNotFound)).thenThrow(new ResourceNotFoundException("product to update not found"));

        doNothing().when(productRepository).deleteById(1);
        doThrow(new ResourceNotFoundException("product to delete not found")).when(productRepository).deleteById(3);
        doNothing().when(productRepository).deleteAll();
    }

    @Test
    public void saveProductTest() {
        Product savedProduct = productService.saveProduct(product1);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(product1.getId(), savedProduct.getId());
        Assertions.assertEquals(product1.getName(), savedProduct.getName());
        Assertions.assertEquals(product1.getQuantity(), savedProduct.getQuantity());
        Assertions.assertEquals(product1.getPrice(), savedProduct.getPrice());
    }

    @Test
    public void saveProductsTest() {
        List<Product> savedProducts = productService.saveProducts(products);
        Assertions.assertNotNull(savedProducts);
        Assertions.assertEquals(2, savedProducts.size());
    }

    @Test
    public void getProductTest() {
        Product foundProduct = productService.getProduct(1);
        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product1.getId(), foundProduct.getId());
        Assertions.assertEquals(product1.getName(), foundProduct.getName());
        Assertions.assertEquals(product1.getQuantity(), foundProduct.getQuantity());
        Assertions.assertEquals(product1.getPrice(), foundProduct.getPrice());
    }

    @Test
    public void getProductNotFoundTest() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(3));
    }

    @Test
    public void getProductByNameTest() {
        Product foundProduct = productService.getProductByName("Macbook Pro");
        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product1.getId(), foundProduct.getId());
        Assertions.assertEquals(product1.getName(), foundProduct.getName());
        Assertions.assertEquals(product1.getQuantity(), foundProduct.getQuantity());
        Assertions.assertEquals(product1.getPrice(), foundProduct.getPrice());
    }

    @Test
    public void getProductByNameNotFoundTest() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProductByName("unknown"));
    }

    @Test
    public void updateProductTest() {
        Product updatedProduct = productService.updateProduct(productToUpdate);
        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals(productToUpdate.getId(), updatedProduct.getId());
        Assertions.assertEquals(productToUpdate.getName(), updatedProduct.getName());
        Assertions.assertEquals(productToUpdate.getQuantity(), updatedProduct.getQuantity());
        Assertions.assertEquals(productToUpdate.getPrice(), updatedProduct.getPrice());
    }

    @Test
    public void updateProductNotFoundTest() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productToUpdateNotFound));
    }

    @Test
    public void deleteProductTest() {
        Integer id = productService.deleteProduct(1);
        Assertions.assertEquals(1, id);
    }

    @Test
    public void deleteProductNotFoundTest() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(3));
    }
}
