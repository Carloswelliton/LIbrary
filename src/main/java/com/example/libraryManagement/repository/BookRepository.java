package com.example.libraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraryManagement.models.Books;

public interface BookRepository extends JpaRepository<Books, Long>{
    
}
