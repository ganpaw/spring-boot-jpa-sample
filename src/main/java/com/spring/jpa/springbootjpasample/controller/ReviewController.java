package com.spring.jpa.springbootjpasample.controller;

import com.spring.jpa.springbootjpasample.dao.BookRepository;
import com.spring.jpa.springbootjpasample.dao.ReviewRepository;
import com.spring.jpa.springbootjpasample.exception.ResourceNotFoundException;
import com.spring.jpa.springbootjpasample.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    //C
    // Add new review by book id - 1. retrieve book by id, 2. review.setBook(book) 3. save review
    @PostMapping("/books/{id}/reviews")
    public ResponseEntity<Review> postReviewsForBook(@PathVariable("id") Long bookId, @RequestBody Review review){
        Review savedReview = bookRepository.findById(bookId).map(book -> {
            review.setBook(book);
            return reviewRepository.save(review);
        }).orElseThrow(() -> new ResourceNotFoundException("No book found with id :"+bookId));
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    //R
    //Get all reviews of a book - 1. if book doesn't exist throw error 2. get review by book id.
    @GetMapping("/books/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsOfBook(@PathVariable("id") Long bookId){
        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("No book found with id :"+bookId);
        }
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        if (reviews.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Get review by review id
    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> getReviewsByReviewId(@PathVariable("id") Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No review found with id :"+id));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    //U
    //Update review by review id
    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReviewsByReviewId(@PathVariable("id") Long reviewId, @RequestBody Review review){
        Review reviewToUpdate = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("No review found with id :"+reviewId));
        reviewToUpdate.setComment(review.getComment());
        Review updatedReview = reviewRepository.save(reviewToUpdate);
        return new ResponseEntity<>(updatedReview, HttpStatus.NO_CONTENT);
    }

    //D
    //Delete review by review id
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Review> deleteReviewByReviewId(@PathVariable("id") Long reviewId){
        reviewRepository.deleteById(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Delete all reviews of a book
    @DeleteMapping("/books/{id}/reviews")
    public ResponseEntity<Review> deleteAllReviesOfBook(@PathVariable("id") Long bookId){
        if (!bookRepository.existsById(bookId)){
            throw new ResourceNotFoundException("No book found with id :"+bookId);
        }
        reviewRepository.deleteByBookId(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
