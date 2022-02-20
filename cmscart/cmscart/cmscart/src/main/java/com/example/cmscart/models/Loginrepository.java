package com.example.cmscart.models;

import com.example.cmscart.models.data.Login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Loginrepository extends JpaRepository<Login,Integer> {
    
}
