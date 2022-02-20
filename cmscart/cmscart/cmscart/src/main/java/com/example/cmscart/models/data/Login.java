package com.example.cmscart.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="login")
@Data
public class Login {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    private String email;
    private String password;
    private String email1;
    
}
