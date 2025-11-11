package com.example.libraryManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.libraryManagement.models.Users;
import com.example.libraryManagement.repository.UsersRepository;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    @Cacheable("usuarios_count")
    public Long userCount(){return userRepository.count();}

    @Cacheable("usuarios")
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    @Cacheable(value="usuariospoid", key="#id")
    public Optional<Users> getUserById(Long id){
        if(id == null){ throw new RuntimeException("Usuário não encontrado"); }
        return userRepository.findById(id);
    }

    @CacheEvict(value={"usuarios", "usuarios_count", "usuariospoid"}, allEntries = true)
    public Users saveUser(Users user){
        if(user == null){ throw new RuntimeException("Usuário não pode estar vazio"); }
        return userRepository.save(user);
    }

    @CacheEvict(value={"usuarios", "usuarios_count", "usuariospoid"}, allEntries = true)
    public void deleteUser(Long id){
        if(id == null){ throw new RuntimeException("Usuário não encontrado"); }
        userRepository.deleteById(id);
    }

    @CacheEvict(value={"usuarios", "usuarios_count", "usuariospoid"}, allEntries = true)
    public Users updateUser(Users user, Long id){
        if(id == null){ throw new RuntimeException("Usuário não encontrado para atualizar"); }

        Optional<Users> usuarioEncontrado = userRepository.findById(id);

        if(!usuarioEncontrado.isPresent()){ throw new RuntimeException("Usuário não existe"); }

        Users usuarioAtualizado = usuarioEncontrado.get();
        usuarioAtualizado.setNome(user.getNome());
        usuarioAtualizado.setSexo(user.getSexo());
        usuarioAtualizado.setTelefone(user.getTelefone());
        usuarioAtualizado.setEmail(user.getEmail());
        
        return userRepository.save(usuarioAtualizado);

    }
    
}
