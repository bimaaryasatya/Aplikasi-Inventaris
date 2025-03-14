/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasi.inventaris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author bimazznxt
 */
// Generic class for Inventory Management
class Inventory<T> {
    private List<T> items = new ArrayList<>();
    
    public void addItem(T item) {
        items.add(item);
    }
    
    public List<T> getItems() {
        return items;
    }
    
    public void displayItems() {
        for (T item : items) {
            System.out.println(item.toString());
        }
    }
    
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (T item : items) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadFromFile(String filename) {
        items.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", Quantity: ");
                if (parts.length == 2) {
                    String name = parts[0].replace("Item: ", "");
                    int quantity = Integer.parseInt(parts[1]);
                    items.add((T) new Item(name, quantity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Item {
    private String name;
    private int quantity;
    
    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "Item: " + name + ", Quantity: " + quantity;
    }
}

// GUI class
public class InventoryApp {
    private JFrame frame;
    private JTextField itemNameField;
    private JTextField itemQuantityField;
    private JTextField searchField;
    private JTextArea displayArea;
    private Inventory<Item> inventory;
    
    public InventoryApp() {
        inventory = new Inventory<>();
        frame = new JFrame("Inventory Management");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        
        JLabel nameLabel = new JLabel("Item Name:");
        itemNameField = new JTextField(15);
        
        JLabel quantityLabel = new JLabel("Quantity:");
        itemQuantityField = new JTextField(5);
        
        JButton addButton = new JButton("Add Item");
        JButton saveButton = new JButton("Save to File");
        JButton loadButton = new JButton("Load from File");
        
        JLabel searchLabel = new JLabel("Search Item:");
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = itemNameField.getText();
                int quantity = Integer.parseInt(itemQuantityField.getText());
                
                inventory.addItem(new Item(name, quantity));
                updateDisplay();
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.saveToFile("inventory.txt");
            }
        });
        
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.loadFromFile("inventory.txt");
                updateDisplay();
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                StringBuilder sb = new StringBuilder();
                for (Item item : inventory.getItems()) {
                    if (item.getName().toLowerCase().contains(searchText)) {
                        sb.append(item.toString()).append("\n");
                    }
                }
                displayArea.setText(sb.toString());
            }
        });
        
        frame.add(nameLabel);
        frame.add(itemNameField);
        frame.add(quantityLabel);
        frame.add(itemQuantityField);
        frame.add(addButton);
        frame.add(saveButton);
        frame.add(loadButton);
        frame.add(searchLabel);
        frame.add(searchField);
        frame.add(searchButton);
        frame.add(new JScrollPane(displayArea));
        
        frame.setVisible(true);
    }
    
    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Item item : inventory.getItems()) {
            sb.append(item.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }
    
    public static void main(String[] args) {
        new InventoryApp();
    }
}