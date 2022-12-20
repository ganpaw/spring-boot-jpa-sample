package com.spring.jpa.springbootjpasample.dao;

import com.spring.jpa.springbootjpasample.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepostiry extends CrusRepository and provides paging and sorting functions.
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

}
