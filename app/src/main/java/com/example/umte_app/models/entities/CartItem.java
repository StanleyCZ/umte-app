package com.example.umte_app.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = {@ForeignKey(onDelete = CASCADE,entity = Cart.class,parentColumns = "id",childColumns = "cartId")})
public class CartItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long cartId;
    public String name;
    public int count;
    public float price;
    public String imagePath;
    public boolean isPurchased;

    public CartItem(String name, int count){
        this.name = name;
        this.count = count;
    }
    public CartItem(){

    }

}
