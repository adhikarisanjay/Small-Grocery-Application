package com.example.sagrocery.Model;

public class AddStock {
    private String itemName;
    private int quantity;
    private double price;
    private boolean isTaxable;
    private int itemCode;

    public AddStock() {
        // Default constructor
    }
    public AddStock(String itemName, int qtyStock, double price, boolean taxable,int itemCode) {
    }



    // Getters and setters for the fields

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsTaxable() {
        return isTaxable;
    }

    public void setTaxable(boolean taxable) {
        isTaxable = taxable;
    }


    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }
}
