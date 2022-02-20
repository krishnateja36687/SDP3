package com.example.cmscart.models;

import com.example.cmscart.models.data.Manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface Managerrepository extends JpaRepository<Manager,Integer>{

    @Query("SELECT accessmanager FROM Manager p where p.email = :email")
    Manager findByaccess(String email);
    
}
