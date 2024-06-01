package com.example.sagrocery.Model;

public class SalesItem {
    private int itemCode;
    private String customerName;
    private String customerEmail;
    private int quantitySold;
    private String dateOfSale;

    public  SalesItem(){

    }

    public SalesItem(int itemCode, String customerName, String customerEmail, int quantitySold, String dateOfSale) {
        this.itemCode = itemCode;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.quantitySold = quantitySold;
        this.dateOfSale = dateOfSale;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }
}
