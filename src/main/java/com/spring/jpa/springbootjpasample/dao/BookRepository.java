package com.spring.jpa.springbootjpasample.dao;

import com.spring.jpa.springbootjpasample.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String title);

}
