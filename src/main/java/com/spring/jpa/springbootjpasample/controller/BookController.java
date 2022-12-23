package com.spring.jpa.springbootjpasample.controller;

import com.spring.jpa.springbootjpasample.dao.BookRepository;
import com.spring.jpa.springbootjpasample.exception.ResourceNotFoundException;
import com.spring.jpa.springbootjpasample.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    //C
    //Add new book
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    //R
    //Get all books or books containing title if passed a request parameter (optional)
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String title){
        List<Book> books = new ArrayList<>();

        if (title != null){
            bookRepository.findByTitleContaining(title).forEach(books::add);
        }else{
            bookRepository.findAll().forEach(books::add);
        }

        if (books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    //Get book by id
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No book found with id :"+id));
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    //U
    //Update book by id
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book){
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No book found with id :"+id));
        bookToUpdate.setTitle(book.getTitle());
        Book updatedBook = bookRepository.save(bookToUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedBook);
    }

    //D
    //Delete book (and its reviews) by :id
    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id){
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Delete all books
    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
