/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasi.inventaris;

/**
 *
 * @author BIMAZZNXT
 */
public class Item {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    private String name;
    private double price;
    
    @Override
    public String toString(){
        return "Item{name='" + name + "', Price =" + price + "}";
    }
}
