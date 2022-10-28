package com.example.demo.dao;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByTitle(String title);
}
