package com.example.libraryManagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.libraryManagement.models.Books;
import com.example.libraryManagement.service.BookService;
import com.example.libraryManagement.service.LoanService;
import com.example.libraryManagement.service.UserService;


@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private BookService bookService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Model model){
        Long totalDeLivros = bookService.bookCount();
        Long totalDeEmprestimos = loanService.loanCount();
        Long totalDeUsuarios = userService.userCount();

        model.addAttribute("booksTotal", totalDeLivros);
        model.addAttribute("emprestimosTotal", totalDeEmprestimos);
        model.addAttribute("usuariosTotal", totalDeUsuarios);

        return "index";
    }

    @GetMapping("/cadastrarlivros")
    public String cadastrarLivros(Model model){
        model.addAttribute("novoLivro", new Books());
        return "books/cadastrarLivros";
    }

    @PostMapping("/cadastrarlivros/salvar")
    public String salvarLivro(@ModelAttribute("novoLivro") Books books){
        bookService.saveBook(books);
        return "redirect:/library";
    }

    @GetMapping("/listarlivros")
    public String listarLivros(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "books/listarlivros";
    }

    @GetMapping("/editarlivros/{id}")
    public String editarLivros(@PathVariable Long id, Model model){
        Books livro = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        model.addAttribute("livro", livro);
        return "books/editarlivros";
    }

    @PostMapping("/editarlivros/{id}")
    public String atualizarLivro(@PathVariable Long id, @ModelAttribute("livro") Books book){
        bookService.updateBook(book, id);
        return "redirect:/library/listarlivros";
    }

    @GetMapping("/deletarlivro/{id}")
    public String deletarLivro(@PathVariable Long id){
        bookService.deleteBook(id);
        return "redirect:/library/listarlivros";
    }

    
    
    
}
