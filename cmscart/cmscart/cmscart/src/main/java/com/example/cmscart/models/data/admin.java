package com.example.cmscart.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="admin")
@Data
public class admin {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    private String email;
    private String password;
    
}
