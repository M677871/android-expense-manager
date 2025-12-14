package com.example.towtables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBhandler extends SQLiteOpenHelper {
private static final int DATABASE_VERSION = 1;
private static final String DATABASE_NAME = "towTables.db";


    public DBhandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategories = "CREATE TABLE categories (c_id INTEGER PRIMARY KEY AUTOINCREMENT , c_name TEXT)";
        String createExpenses = "CREATE TABLE expenses (e_id INTEGER PRIMARY KEY AUTOINCREMENT , amount REAL, date TEXT, description TEXT , cat_id INTEGER NOT NULL , FOREIGN KEY (cat_id) REFERENCES categories(c_id))";
        db.execSQL(createCategories);
        db.execSQL(createExpenses);
        String insert = "INSERT INTO categories (c_name) VALUES ('general') , ('food') , ('transport')";
        db.execSQL(insert);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS expenses");
        onCreate(db);
    }
    public ArrayList<CategoryModel> readAllCategories()
    {
        String query = "SELECT * FROM categories";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<CategoryModel> category = new ArrayList<CategoryModel>();
        if (cursor.moveToFirst())
        {
            do
            {
                category.add(new CategoryModel(cursor.getInt(0),
                        cursor.getString(1)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return category;
    }

    public void addNewExpense(double amount , String description, String date , int cat_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount" , amount);
        values.put("description", description);
        values.put("date", date);
        values.put("cat_id" , cat_id);
        db.insert("expenses", null, values);
        db.close();
    }
    public ArrayList<ExpenseModel> readAllExpenses() {

        String query =
                "SELECT e.e_id, c.c_name, e.amount, e.description, e.date " +
                        "FROM expenses e " +
                        "JOIN categories c ON e.cat_id = c.c_id " +
                        "ORDER BY e.e_id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<ExpenseModel> expense = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int eid = cursor.getInt(0);
                String cname = cursor.getString(1);
                double amount = cursor.getDouble(2);
                String desc = cursor.getString(3);
                String date = cursor.getString(4);

                ExpenseModel model = new ExpenseModel(amount, desc, date, cname);
                model.setE_id(eid);
                expense.add(model);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expense;
    }


    public void updateExpense (int eid , String newDescription , double amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("description", newDescription);
        values.put("amount", amount);
        db.update("expenses", values, "e_id = ?", new String[]{String.valueOf(eid)});
        db.close();
    }
    public void deleteExpenses (int eid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("expenses", "e_id = ?", new String[]{String.valueOf(eid)});
        db.close();
    }
}



