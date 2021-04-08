package com.example.umte_app.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long cartId;
    public String name;
    public int count;
    public float price;
    public String imagePath;

}
