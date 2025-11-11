package com.example.libraryManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.libraryManagement.models.Books;
import com.example.libraryManagement.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Cacheable("livros_count")
    public Long bookCount(){return bookRepository.count();}

    @Cacheable("livros")
    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    @Cacheable("livrosporid")
    public Optional<Books> getBookById(Long id){
        if(id == null){ throw new RuntimeException("ID do livro não pode ser nulo"); }
        return bookRepository.findById(id);
    }

    @CacheEvict(value={"livros", "livros_count", "livrosporid"}, allEntries = true)
    public Books saveBook(Books book){
        if(book == null){ throw new RuntimeException("As informações do livro estão vazias");}
        return bookRepository.save(book);
    }

    @CacheEvict(value={"livros", "livros_count", "livrosporid"}, allEntries = true)
    public void deleteBook(Long id){
        if(id == null){ throw new RuntimeException("Forneça o ID do livro a ser deletado"); }
        bookRepository.deleteById(id);
    }

    @CacheEvict(value={"livros", "livros_count", "livrosporid"}, allEntries = true)
    public Books updateBook(Books book, Long id){
        if(id == null){ throw new RuntimeException("Fornaça o ID do livro a ser atualizado");}

        Optional<Books> livroExistente = bookRepository.findById(id); 

        if(!livroExistente.isPresent()){throw new RuntimeException("Livro não encontrado");}

        Books livroAtualizado = livroExistente.get();
        livroAtualizado.setTitulo(book.getTitulo());
        livroAtualizado.setTema(book.getTema());
        livroAtualizado.setAutor(book.getAutor());
        livroAtualizado.setISBN(book.getISBN());
        livroAtualizado.setAnoPublicacao(book.getAnoPublicacao());
        livroAtualizado.setQntDisponivel(book.getQntDisponivel());
        return bookRepository.save(livroAtualizado);
    }


    
}
