package com.example.libraryManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libraryManagement.models.Loan;
import com.example.libraryManagement.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id){
        if(id == null){ throw new RuntimeException("Id do empréstimo não encontrado"); }
        return loanRepository.findById(id);
    }

    public Loan saveLoan(Loan loan){
        if(loan == null){ throw new RuntimeException("O empréstimo não pode estar vazio"); }
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id){
        if(id == null){ throw new RuntimeException("O id do empréstimo não pode estar vazio"); }
        loanRepository.deleteById(id);
    }

    public Loan updateLoan(Loan loan, Long id){
        if(id == null){ throw new RuntimeException("Você deve fornecer o ID do empréstimo"); }

        Optional<Loan> emprestimoEncontrado = loanRepository.findById(id);

        if(!emprestimoEncontrado.isPresent()){ throw new RuntimeException("Empréstimo não encontrado"); }

        Loan emprestimoAtualizado = emprestimoEncontrado.get();
        emprestimoAtualizado.setUser(loan.getUser());
        emprestimoAtualizado.setDataEmprestimo(loan.getDataEmprestimo());
        emprestimoAtualizado.setDevolucaoPrevista(loan.getDevolucaoPrevista());
        emprestimoAtualizado.setDataDevolucao(loan.getDataDevolucao());

        return loanRepository.save(emprestimoAtualizado);
    }

}
