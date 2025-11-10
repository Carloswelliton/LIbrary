package com.example.libraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraryManagement.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    
}
