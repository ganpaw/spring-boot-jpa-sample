package com.spring.jpa.springbootjpasample.dao;

import com.spring.jpa.springbootjpasample.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByComment(String comment);

    List<Review> findByBookId(Long id);

    @Transactional
    void deleteByBookId(Long Id);

}
