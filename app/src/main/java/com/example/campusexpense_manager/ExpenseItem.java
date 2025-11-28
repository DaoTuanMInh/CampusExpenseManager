// ExpenseItem.java
package com.example.campusexpense_manager;

public class ExpenseItem {
    private int id;
    private String category;
    private int categoryId;
    private int amount;
    private String date;         // format yyyy-MM-dd
    private String description;
    private int userId;

    public ExpenseItem(int id, String category, int categoryId, int amount,
                       String date, String description, int userId) {
        this.id = id;
        this.category = category;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.userId = userId;
    }

    // getters
    public int getId() { return id; }
    public String getCategory() { return category; }
    public int getCategoryId() { return categoryId; }
    public int getAmount() { return amount; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public int getUserId() { return userId; }
}