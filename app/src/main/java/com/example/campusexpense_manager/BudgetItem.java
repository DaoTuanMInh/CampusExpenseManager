package com.example.campusexpense_manager;

public class BudgetItem {
    private int id;
    private String category;
    private long limitAmount;
    private String month;

    public BudgetItem(int id, String category, int limitAmount, String month) {
        this.id = id;
        this.category = category;
        this.limitAmount = limitAmount;
        this.month = month;
    }

    public int getId() { return id; }
    public String getCategory() { return category; }
    public long getLimitAmount() { return limitAmount; }
    public String getMonth() { return month; }

    public void setCategory(String category) { this.category = category; }
    public void setLimitAmount(int limitAmount) { this.limitAmount = limitAmount; }
    public void setMonth(String month) { this.month = month; }
}
