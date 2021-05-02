package com.example.umte_app.models.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Cart implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String storeName;
    public Date shopDate;
    public Date creationDate;

    public boolean isFinished;

    public Cart(String name, String storeName){
        this.name = name;
        this.storeName = storeName;
    }
    public Cart(){}
}
