package com.example.umte_app.models.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Cart {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String storeName;
    public Date shopDate;
    public Date creationDate;

    public boolean isFinished;
}
