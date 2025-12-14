package com.example.towtables;

public class ExpenseModel {

    private int e_id;
    private double amount ;
    private String desc;
    private String date;
    private int c_id;
    private String c_name;

    public ExpenseModel(double amount, String desc, String date,  String c_name) {
        this.amount = amount;
        this.desc = desc;
        this.date = date;
        this.c_name = c_name;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
