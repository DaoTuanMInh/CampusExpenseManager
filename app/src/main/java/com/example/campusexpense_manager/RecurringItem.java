// New RecurringItem.java
package com.example.campusexpense_manager;

public class RecurringItem {
    private int id;
    private String category;
    private int amount;
    private String startMonth;
    private String endMonth;

    public RecurringItem(int id, String category, int amount, String startMonth, String endMonth) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }

    public int getId() { return id; }
    public String getCategory() { return category; }
    public int getAmount() { return amount; }
    public String getStartMonth() { return startMonth; }
    public String getEndMonth() { return endMonth; }

    public void setCategory(String category) { this.category = category; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setStartMonth(String startMonth) { this.startMonth = startMonth; }
    public void setEndMonth(String endMonth) { this.endMonth = endMonth; }
}