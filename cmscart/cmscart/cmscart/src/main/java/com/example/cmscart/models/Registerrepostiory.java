package com.example.cmscart.models;

import com.example.cmscart.models.data.Register;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Registerrepostiory extends JpaRepository<Register,Integer> {
    
}
