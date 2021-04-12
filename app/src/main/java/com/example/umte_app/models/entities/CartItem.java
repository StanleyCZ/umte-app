package com.example.umte_app.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ForeignKey(entity = Cart.class, parentColumns = "id", childColumns = "cartId", onDelete = CASCADE)
    public long cartId;
    public String name;
    public int count;
    public float price;
    public String imagePath;

    public CartItem(String name, int count){
        this.name = name;
        this.count = count;
    }

}
