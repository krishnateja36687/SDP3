package com.example.cmscart.models;

import com.example.cmscart.models.data.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Pagerepository extends JpaRepository<Page,Integer>
{

    Page findBySlug(String slug);

    @Query("SELECT p FROM Page p where p.id != :id and p.slug= :slug")
    Page findBySlug(int id,String slug);

    
}