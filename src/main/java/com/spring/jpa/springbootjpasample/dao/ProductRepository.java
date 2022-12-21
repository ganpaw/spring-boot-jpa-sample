package com.spring.jpa.springbootjpasample.dao;

import com.spring.jpa.springbootjpasample.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// JpaRepostiry extends CrusRepository and provides paging and sorting functions.
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    // JPQL - validation is done
    @Query("select p from Product p where p.name = :name")
    Optional<Product> findByNameCustomQuery(@Param("name") String name);

    // Native SQL - no validation
    @Query(value = "select * from product_tbl as p where p.name = :name", nativeQuery = true)
    Optional<Product> findByNameNativeQuery(@Param("name") String name);
}
