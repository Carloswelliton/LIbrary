package com.example.libraryManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.libraryManagement.models.Books;
import com.example.libraryManagement.service.BookService;


@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String home(){
        return "index";
    }

    @GetMapping("/registros")
    public String listarLivros(Model model){
        model.addAttribute("book", bookService.getAllBooks());
        model.addAttribute("novoLivro", new Books());
        return "books/book";
    }
    
    
    
}
