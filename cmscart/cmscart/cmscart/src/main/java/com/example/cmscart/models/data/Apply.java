package com.example.cmscart.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import lombok.Data;

@Entity
@Table(name="applications")
@Data
public class Apply {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private int phoneno;
    private String email;
    private int loanamount;
    private int income;
    private String roles="Pending";
    private String type;
    @Column(nullable = true, length = 64)
    private String photos;
    
    @Transient
    public String getPhotosImagePath() {
        if (photos == null) return null;
         
        return "/user-photos/" + id + "/" + photos;
    }
    
}
