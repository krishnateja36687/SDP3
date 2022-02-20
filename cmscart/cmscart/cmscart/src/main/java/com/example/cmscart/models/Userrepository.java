package com.example.cmscart.models;

import com.example.cmscart.models.data.Apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Userrepository extends JpaRepository<Apply,Integer>{



    @Query("SELECT p FROM Apply p where p.email = :email")
    Apply findByemail(String email);
    Apply findById(int id);
    
}
