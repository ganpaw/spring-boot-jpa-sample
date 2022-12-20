package com.spring.jpa.springbootjpasample.controller;

import com.spring.jpa.springbootjpasample.exception.ResourceNotFoundException;
import com.spring.jpa.springbootjpasample.model.Product;
import com.spring.jpa.springbootjpasample.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

// Integration testing - testing web controller layer only by mocking service layer
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    private static final String PRODUCT_JSON = "{\"id\": 1, \"name\":\"Macbook Pro\", \"quantity\": 5, \"price\": 999.99}";
    private static final String ERROR_PRODUCT_JSON = "{\"id\": 1, \"name\":\"Macbook Pro\", \"quantity\": 5 \"price\": 999.99}";
    private static final String PRODUCTS_JSON = "[{\"id\": 1, \"name\":\"Macbook Pro\", \"quantity\": 5, \"price\": 999.99}, {\"id\": 2, \"name\":\"Microsoft XBox\", \"quantity\": 10, \"price\": 399.99}]";
    private static final String UPDATE_PRODUCT_JSON = "{\"id\": 1, \"name\":\"Macbook Pro\", \"quantity\": 50, \"price\": 1999.99}";
    private static final String UPDATE_PRODUCT_NOT_FOUND_JSON = "{\"id\": 3, \"name\":\"Macbook Pro\", \"quantity\": 100, \"price\": 1599.99}";

    @BeforeEach
    public void setup(){
        Product product1 = new Product(1, "Macbook Pro", 5, 999.99);
        Product product2 = new Product(2, "Microsoft XBox", 10, 399.99);
        List<Product> products = Stream.of(product1, product2).collect(Collectors.toList());

        when(productService.saveProduct(product1)).thenReturn(product1);
        when(productService.saveProducts(products)).thenReturn(products);

        when(productService.getProducts()).thenReturn(products);

        when(productService.getProduct(1)).thenReturn(product1);
        when(productService.getProduct(2)).thenReturn(product2);
        when(productService.getProduct(3)).thenThrow(new ResourceNotFoundException("product not found"));

        when(productService.getProductByName("Macbook Pro")).thenReturn(product1);
        when(productService.getProductByName("Microsoft XBox")).thenReturn(product2);
        when(productService.getProductByName("unknown")).thenThrow(new ResourceNotFoundException("product not found"));

        Product productToUpdate = new Product(1, "Macbook Pro", 50, 1999.99);
        when(productService.updateProduct(productToUpdate)).thenReturn(productToUpdate);
        Product productToUpdateNotFound = new Product(3, "Macbook Pro", 100, 1599.99); // make sure matched with UPDATE_PRODUCT_NOT_FOUND_JSON
        when(productService.updateProduct(productToUpdateNotFound)).thenThrow(new ResourceNotFoundException("product to update not found"));

        when(productService.deleteProduct(1)).thenReturn(1);
        when(productService.deleteProduct(3)).thenThrow(new ResourceNotFoundException("product to delete not found"));

        doNothing().when(productService).deleteAllProducts();
    }

    @Test
    public void addProductTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/jpa/crud/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PRODUCT_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Macbook Pro")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(999.99))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void addProductBadRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/jpa/crud/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ERROR_PRODUCT_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addProductsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/jpa/crud/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PRODUCTS_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Macbook Pro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(999.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Microsoft XBox"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(399.99))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProductsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/crud/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Macbook Pro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(999.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Microsoft XBox"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(399.99))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProductTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/crud/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Macbook Pro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(999.99))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void getProductNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/crud/product/3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorDetail").value("Resource was not found on server side"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developerMessage").value("product not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProductByNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/crud/product").queryParam("name", "Microsoft XBox"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Microsoft XBox"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(399.99))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProductByNameNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/crud/product").queryParam("name", "unknown"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorDetail").value("Resource was not found on server side"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developerMessage").value("product not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProductTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/jpa/crud/product")
                .contentType(MediaType.APPLICATION_JSON).content(UPDATE_PRODUCT_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Macbook Pro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1999.99))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProductNotFoundTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/jpa/crud/product")
                        .contentType(MediaType.APPLICATION_JSON).content(UPDATE_PRODUCT_NOT_FOUND_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorDetail").value("Resource was not found on server side"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developerMessage").value("product to update not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProductTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/jpa/crud/product/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProductNotFoundTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/jpa/crud/product/3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorDetail").value("Resource was not found on server side"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developerMessage").value("product to delete not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteAllProductTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/jpa/crud/products"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
