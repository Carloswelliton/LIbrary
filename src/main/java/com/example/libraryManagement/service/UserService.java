package com.example.libraryManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libraryManagement.models.Users;
import com.example.libraryManagement.repository.UsersRepository;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    public Long userCount(){return userRepository.count();}

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id){
        if(id == null){ throw new RuntimeException("Usuário não encontrado"); }
        return userRepository.findById(id);
    }

    public Users saveUser(Users user){
        if(user == null){ throw new RuntimeException("Usuário não pode estar vazio"); }
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        if(id == null){ throw new RuntimeException("Usuário não encontrado"); }
        userRepository.deleteById(id);
    }

    public Users updateUser(Users user, Long id){
        if(id == null){ throw new RuntimeException("Usuário não encontrado para atualizar"); }

        Optional<Users> usuarioEncontrado = userRepository.findById(id);

        if(!usuarioEncontrado.isPresent()){ throw new RuntimeException("Usuário não existe"); }

        Users usuarioAtualizado = usuarioEncontrado.get();
        usuarioAtualizado.setNome(user.getNome());
        usuarioAtualizado.setSexo(user.getSexo());
        usuarioAtualizado.setTelefone(user.getTelefone());
        usuarioAtualizado.setEmail(user.getEmail());
        usuarioAtualizado.setLoans(user.getLoans());
        
        return userRepository.save(usuarioAtualizado);

    }
    
}
