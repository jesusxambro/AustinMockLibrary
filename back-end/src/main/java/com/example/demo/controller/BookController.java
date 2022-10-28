package com.example.demo.controller;


import com.example.demo.dao.BookRepository;
import com.example.demo.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


//Create	POST	/books	"create" route	Creates a book
//Read	GET	/books/{id}	"show" route	Responds with a single book
//Update	PATCH	/books/{id}	"update" route	Updates attributes of the book
//Delete	DELETE	/books/{id}	"delete" route	Deletes the book
//List	GET	/books	"index" or "list" route	Responds with a list of books
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository repository;


    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Book> getAllBooks(){
        return this.repository.findAll();
    }
    @PostMapping("")
    public Book addBook(@RequestBody Book bookToAdd){
        return this.repository.save(bookToAdd);
    }
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id){
        return this.repository.getReferenceById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        this.repository.deleteById(id);
        return ResponseEntity.noContent().build();//returns a 204
    }
    @PatchMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedInfo){
        Book inDb = this.repository.getReferenceById(id);
        Long originalId = inDb.getId();
        inDb = updatedInfo;
        inDb.setId(originalId);
        return this.repository.save(inDb);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleElementNotFound(Exception e) {
        return e.getMessage();
    }



}
