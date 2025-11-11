package com.example.libraryManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.libraryManagement.models.Books;
import com.example.libraryManagement.models.Loan;
import com.example.libraryManagement.models.Users;
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
        return "redirect:/library/listarlivros";
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

    @GetMapping("/cadastrarusuarios")
    public String salvarUsuario(Model model){
        model.addAttribute("novoUsuario", new Users());
        return "users/cadastrarusuarios";
    }

    @PostMapping("/cadastrarusuarios/salvar")
    public String salvarUsuario(@ModelAttribute("novoUsuario") Users user){
        userService.saveUser(user);
        return "redirect:/library/listarusuarios";
    }

    @GetMapping("/listarusuarios")
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", userService.getAllUsers());
        return "users/listarusuarios";
    }

    @GetMapping("/editarusuarios/{id}")
    public String editarUsuario(@PathVariable Long id, Model model){
        Users user = userService.getUserById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        model.addAttribute("usuario", user);
        return "users/editarusuarios";
    }

    @PostMapping("/editarusuarios/{id}")
    public String atualizaUsuario(@PathVariable Long id, @ModelAttribute("usuario") Users user) {
        userService.updateUser(user, id);
        return "redirect:/library/listarusuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/listarusuarios";
    }

    @GetMapping("/usuarios/{id}/novoemprestimo")
    public String novoEmpresitmo(@PathVariable Long id, Model model){
        Users usuario = userService.getUserById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        Loan emprestarLivro = new Loan();
        emprestarLivro.setUser(usuario);
        model.addAttribute("emprestimo", emprestarLivro);
        return "loans/cadastraremprestimo";
    }

    @PostMapping("/usuarios/{id}/novoemprestimo")
    public String salvarEmprestimo(@PathVariable Long id, @ModelAttribute("emprestimo") Loan emprestimo){
        Users usuario = userService.getUserById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        emprestimo.setUser(usuario);
        loanService.saveLoan(emprestimo);
        return "redirect:/library/usuarios/"+id+"/novoemprestimo";
    }

    @GetMapping("/usuarios/emprestimos")
    public String listarEmprestimos(@PathVariable Long id, Model model){
        Users usuario = userService.getUserById(id).orElseThrow(()-> new RuntimeException("usuario não encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("emprestimos", usuario.getLoans());
        return "loans/listaremprestimos";
    }

    
    
    
}
