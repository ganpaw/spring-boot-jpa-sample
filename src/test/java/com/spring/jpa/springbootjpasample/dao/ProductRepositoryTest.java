package com.spring.jpa.springbootjpasample.dao;

import com.spring.jpa.springbootjpasample.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Integration tests using inmemory db h2, @DataJpaTest, @TestPropertySource and @Sql
 * https://reflectoring.io/spring-boot-data-jpa-test/
 */
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    private Product product1;
    private Product product2;
    private List<Product> products;

    @BeforeEach
    public void setup() {
        product1 = new Product(1, "Macbook Pro", 5, 999.99);
        product2 = new Product(2, "Microsoft XBox", 10, 399.99);
        products = Stream.of(product1, product2).collect(Collectors.toList());
    }

    @Test
    public void saveProductTest() {
        Product savedProduct = productRepository.save(product1);
        Assertions.assertEquals(product1.getName(), savedProduct.getName());
        Assertions.assertEquals(product1.getQuantity(), savedProduct.getQuantity());
        Assertions.assertEquals(product1.getPrice(), savedProduct.getPrice());
    }

    @Test
    public void saveProductsTest() {
        List<Product> savedProducts = productRepository.saveAll(products);
        Assertions.assertEquals(2, savedProducts.size());
    }

    @Test
    @Sql("createProducts.sql")
    public void findByNameTest() {
        Assertions.assertTrue(productRepository.findByName("Sony 4k TV").isPresent());
    }

    @Test
    @Sql("createProducts.sql")
    public void findByNameCustomQueryTest() {
        Assertions.assertTrue(productRepository.findByNameCustomQuery("Bose Headset").isPresent());
    }

    @Test
    @Sql("createProducts.sql")
    public void findByNameNativeQueryTest() {
        Assertions.assertTrue(productRepository.findByNameNativeQuery("Dell Monitor").isPresent());
    }

    @Test
    @Sql("createProducts.sql")
    public void deleteProductQuery() {
        Assertions.assertTrue(productRepository.findById(2).isPresent());
        productRepository.deleteById(2);
        Assertions.assertFalse(productRepository.findById(2).isPresent());
    }

    @Test
    @Sql("createProducts.sql")
    public void deleteProductsQuery() {
        Assertions.assertEquals(3, productRepository.findAll().size());
        productRepository.deleteAll();
        Assertions.assertEquals(0, productRepository.findAll().size());
    }
}
