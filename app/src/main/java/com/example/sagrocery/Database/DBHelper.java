package com.example.sagrocery.Database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import androidx.dynamicanimation.animation.SpringAnimation;

import com.example.sagrocery.Model.AddStock;
import com.example.sagrocery.R;
import com.example.sagrocery.Utils.CustomAlert;
import com.example.sagrocery.Utils.UserManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final String dbName = "SAGrocery.db";
    private static final int version = 1;
    private static final String TABLE_USER = "User";
    private static final String user_col1 = "id";
    private static final String user_col2 = "userName";
    private static final String user_col3 = "email";
    private static final String user_col4 = "password";

    private static final String Create_User_Table = "CREATE TABLE " + TABLE_USER + "(" + user_col1 +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + user_col2 + " TEXT NOT NULL, " +
            user_col3 + " TEXT NOT NULL, " + user_col4 + " TEXT NOT NULL)";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    //Stock table
    private static final String TABLE_STOCK = "Stock";
    private static final String stock_col1 = "itemCode";
    private static final String stock_col2 = "itemName";
    private static final String stock_col3 = "qtyStock";
    private static final String stock_col4 = "price";
    private static final String stock_col5 = "taxable";

//table stock
    private static final String Create_Stock_Table = "CREATE TABLE " + TABLE_STOCK + "(" +
            stock_col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            stock_col2 + " TEXT NOT NULL, " +
            stock_col3 + " INTEGER NOT NULL, " +
            stock_col4 + " REAL NOT NULL, " +
            stock_col5 + " INTEGER NOT NULL)";
    private static final String DROP_TABLE_STOCK = "DROP TABLE IF EXISTS " + TABLE_STOCK;


    //"Sales" table
    private static final String TABLE_STOCK_SALE = "Sales";
    private static final String sale_col1 = "id";
    private static final String sale_col2 = "itemCode";
    private static final String sale_col3 = "customerName";
    private static final String sale_col4 = "customerEmail";
    private static final String sale_col5 = "qtySold";
    private static final String sale_col6 = "dateOfSales";


    private static final String Create_Sale_Table = "CREATE TABLE " + TABLE_STOCK_SALE + "(" +
            sale_col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            sale_col2 + " INTEGER NOT NULL, " +
            sale_col3 + " TEXT NOT NULL, " +
            sale_col4 + " TEXT NOT NULL, " +
            sale_col5 + " INTEGER NOT NULL," +
            sale_col6 + " DATE NOT NULL)";
    private static final String DROP_TABLE_SALE = "DROP TABLE IF EXISTS " + TABLE_STOCK_SALE;


    //"Purchase" table
    private static final String TABLE_STOCK_Purchase = "Purchase";
    private static final String purchase_col1 = "id";
    private static final String purchase_col2 = "itemCode";
    private static final String purchase_col3 = "qtyPurchased";
    private static final String purchase_col4 = "dateOfPurchase";


    private static final String Create_Purchase_Table = "CREATE TABLE " + TABLE_STOCK_Purchase + "(" +
            purchase_col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            purchase_col2 + " INTEGER NOT NULL, " +
            purchase_col3 + " INTEGER NOT NULL," +
            purchase_col4 + " DATE NOT NULL)";
    private static final String DROP_TABLE_PURCHASE = "DROP TABLE IF EXISTS " + TABLE_STOCK_Purchase;


    public DBHelper(Context context) {
        super(context, dbName, null, version);

    }


    // Method called when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_User_Table);
        db.execSQL(Create_Stock_Table);
        db.execSQL(Create_Sale_Table);
        db.execSQL(Create_Purchase_Table);
    }

    // Method called when the database version is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE_STOCK);
        db.execSQL(DROP_TABLE_SALE);
        db.execSQL(DROP_TABLE_PURCHASE);
        onCreate(db);
    }

    // Method to register a new user
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(user_col2, username);
        cv.put(user_col3, email);
        cv.put(user_col4, password);

        long result = db.insert(TABLE_USER, null, cv);
        return result != -1;
    }

    // Method to log in a user
    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {user_col1};
        String selection = user_col2 + " = ?" + " AND " + user_col4 + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int count = cursor.getCount();

        return count > 0;
    }

    // Method to insert stock into the database
    public boolean insertStock(String itemName, int qtyInStock, double price, boolean isTaxable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(stock_col2, itemName);
        values.put(stock_col3, qtyInStock);
        values.put(stock_col4, price);
        values.put(stock_col5, isTaxable ? 1 : 0); // Convert boolean to 1 or 0 for SQLite

        long result = db.insert(TABLE_STOCK, null, values);
        return result != -1; // If result is -1, insertion failed, otherwise successful
    }

    // Method to retrieve all items from the stock table
    public List<AddStock> getAllItemsFromStock() {
        List<AddStock> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STOCK, null);

        if (cursor != null && cursor.moveToFirst()) {
            int itemIdIndex = cursor.getColumnIndex(stock_col1);
            int itemNameIndex = cursor.getColumnIndex(stock_col2);
            int qtyStockIndex = cursor.getColumnIndex(stock_col3);
            int priceIndex = cursor.getColumnIndex(stock_col4);
            int isTaxableIndex = cursor.getColumnIndex(stock_col5);

            do {

                String itemName = cursor.getString(itemNameIndex);

                int qtyStock = cursor.getInt(qtyStockIndex);
                System.out.println(qtyStock);
                double price = cursor.getDouble(priceIndex);
                int isTaxable = cursor.getInt(isTaxableIndex);
                int itemCode = cursor.getInt(itemIdIndex);

                boolean taxable = isTaxable == 1;
                AddStock objAddStock = new AddStock();
                objAddStock.setItemName(itemName);
                objAddStock.setQuantity(qtyStock);
                objAddStock.setPrice(price);
                objAddStock.setTaxable(taxable);
                objAddStock.setItemCode(itemCode);

                itemList.add(objAddStock);
            } while (cursor.moveToNext());
        }
        return itemList;

    }

    //insert sale item
    public boolean insertSale(Context context,int itemCode, String customerName, String customerEmail, int qtySold, String dateOfSale) {
        boolean itemExists = checkItemExists(itemCode);
        if (itemExists) {
            SQLiteDatabase db = this.getWritableDatabase();
            boolean updatedStock = updateStockQuantity(context,itemCode, qtySold);

            if (updatedStock) {
                ContentValues values = new ContentValues();
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = inputFormat.parse(dateOfSale);
                    String formattedDate = outputFormat.format(date);
                    values.put(sale_col6, formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                values.put(sale_col2, itemCode);
                values.put(sale_col3, customerName);
                values.put(sale_col4, customerEmail);
                values.put(sale_col5, qtySold);

                long result = db.insert(TABLE_STOCK_SALE, null, values);
                return result != -1; // If result is -1, insertion failed, otherwise successful
            } else {
                return false;
            }
        } else {
            AlertDialog customDialog = CustomAlert.createCustomDialog(
                    context,
                    "Error",
                    "Item not found in stock!!",
                    R.drawable.error
            );
            customDialog.show();
            return false;
        }
    }

    // Method to check if the itemCode exists in the stock table
    private boolean checkItemExists(int itemCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STOCK + " WHERE " + stock_col1 + " = " + itemCode;
        Cursor cursor = db.rawQuery(query, null);

        boolean itemExists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        return itemExists;
    }

    // Method to update the stock table quantity after a successful sale
    private boolean updateStockQuantity(Context context,int itemCode, int qtySold) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Fetch current quantity of the item
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STOCK + " WHERE " + stock_col1 + " = ?", new String[]{String.valueOf(itemCode)});

        if (cursor != null && cursor.moveToFirst()) {
            int qtyIndex = cursor.getColumnIndex(stock_col3);
            int currentQty = -1; // Default value

            if (qtyIndex != -1) {
                currentQty = cursor.getInt(qtyIndex);
            }
            cursor.close();

            if (currentQty >= qtySold &&qtySold>0) {
                int updatedQty = currentQty - qtySold;
                values.put(stock_col3, updatedQty);

                int rowsAffected = db.update(TABLE_STOCK, values, stock_col1 + " = ?", new String[]{String.valueOf(itemCode)});
                return rowsAffected > 0; // Return true if the update was successful
            }
            else{
                AlertDialog customDialog = CustomAlert.createCustomDialog(
                        context,
                        "Error",
                        "Quantity is not available!!",
                        R.drawable.error
                );
                customDialog.show();
                return false;
            }
        }
        return false;
    }



    //insert purchase stock
    public boolean insertPurchase(Context context,int itemCode,int qtyPurchase, String dateOfPurchase) {
        boolean itemExists = checkItemExists(itemCode);
        if (itemExists) {
            SQLiteDatabase db = this.getWritableDatabase();

            // Update the stock table by reducing the quantity sold
            boolean updatedStock = updateStockQuantityPurchase(context,itemCode, qtyPurchase);

            if (updatedStock) {
                ContentValues values = new ContentValues();
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                values.put(purchase_col2, itemCode);
                values.put(purchase_col3, qtyPurchase);
                try {
                    Date date = inputFormat.parse(dateOfPurchase);
                    String formattedDate = outputFormat.format(date);
                    values.put(purchase_col4, formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long result = db.insert(TABLE_STOCK_Purchase, null, values);
                return result != -1; // If result is -1, insertion failed, otherwise successful
            } else {
                return false;
            }
        } else {
            AlertDialog customDialog = CustomAlert.createCustomDialog(
                    context,
                    "Error",
                    "Item not found in stock!!",
                    R.drawable.error
            );
            customDialog.show();
            return false;
        }
    }

    // Method to update the stock table quantity after a successful sale
    private boolean updateStockQuantityPurchase(Context context,int itemCode, int qtyPurchase) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Fetch current quantity of the item
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STOCK + " WHERE " + stock_col1 + " = ?", new String[]{String.valueOf(itemCode)});

        if (cursor != null && cursor.moveToFirst()) {
            int qtyIndex = cursor.getColumnIndex(stock_col3);
            int currentQty = -1; // Default value

            if (qtyIndex != -1) {
                currentQty = cursor.getInt(qtyIndex);
            }
            cursor.close();

                int updatedQty = currentQty + qtyPurchase;
                values.put(stock_col3, updatedQty);

                int rowsAffected = db.update(TABLE_STOCK, values, stock_col1 + " = ?", new String[]{String.valueOf(itemCode)});
                return rowsAffected > 0; // Return true if the update was successful
            }
        return false;
    }


        // Method to search for an item by item code in the stock table
        public AddStock searchItemByCode(int itemCode) {
            SQLiteDatabase db = this.getReadableDatabase();
            AddStock foundItem =new AddStock();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STOCK + " WHERE " + stock_col1 + " = ?", new String[]{String.valueOf(itemCode)});
            if (cursor != null && cursor.moveToFirst()) {
                int codeIndex = cursor.getColumnIndex(stock_col1);
                int itemNameIndex = cursor.getColumnIndex(stock_col2);
                int quantityIndex = cursor.getColumnIndex(stock_col3);
                int priceIndex = cursor.getColumnIndex(stock_col4);
                int isTaxableIndex = cursor.getColumnIndex(stock_col5);

                if (codeIndex != -1 && itemNameIndex != -1 && quantityIndex != -1 && priceIndex != -1 && isTaxableIndex != -1) {
                    int code = cursor.getInt(codeIndex);
                    String itemName = cursor.getString(itemNameIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    double price = cursor.getDouble(priceIndex);
                    boolean isTaxable = cursor.getInt(isTaxableIndex) == 1;
                    foundItem.setItemName(itemName);
                    foundItem.setQuantity(quantity);
                    foundItem.setPrice(price);
                    foundItem.setTaxable(isTaxable);
                    foundItem.setItemCode(code);

                } else {
                    return null;
                }
                cursor.close();
            }

            return foundItem;
        }

    public int getStockTableRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STOCK , null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
        }

        return count;
    }


    public int getPurchaseTableRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STOCK_Purchase , null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
        }

        return count;
    }

    //get  sales table row count
    public int getSaleTableRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STOCK_SALE , null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
        }

        return count;
    }

}