package com.example.sagrocery.Model;

public class Purchase {
    private int itemCode;
    private int quantityPurchased;
    private String dateOfPurchase;

    public  Purchase(){

    }

    public Purchase(int itemCode, int quantityPurchased, String dateOfPurchase) {
        this.itemCode = itemCode;
        this.quantityPurchased = quantityPurchased;
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

}
