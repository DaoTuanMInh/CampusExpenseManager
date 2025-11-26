package com.example.campusexpense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APDP_Expense";
    private static final int DATABASE_VERSION = 1;


    //Table User
    private static String TABLE_USER = "users";
    private static final String TABLE_USER_COLUM_ID = "id";
    private static final String TABLE_USER_COLUM_USERNAME = "username";
    private static final String TABLE_USER_COLUM_PASSWORD = "password";
    private static final String TABLE_USER_COLUM_EMAIL = "email";
    private static final String TABLE_USER_COLUM_PHONENUMBER = "phonenumber";
    private static final String TABLE_USER_COLUM_ROLE_ID = "role_id";
    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Table Categories
    private static String TABLE_CATEGORIES = "categories";
    private static final String TABLE_CATEGORY_COLUM_ID = "id";
    private static final String TABLE_CATEGORY_COLUM_CATEGORYNAME = "categoryname";
    private static final String TABLE_CATEGORY_COLUM_USER_ID = "user_id";

    //Table Settings
    private static String TABLE_SETTINGS = "settings";
    private static final  String TABLE_SETTING_COLUM_ID = "id";
    private static final String TABLE_SETTING_COLUM_THEMMODE = "themmode";
    private static final String TABLE_SETTING_COLUM_USER_ID = "user_id";

    //Table Overview_of_exxpenses
    private static String TABLE_OVERVIEWOFEXPENSES = "overview_of_expenses";
    private static final String TABLE_OVERVIEWOFEXPENSE_COLUM_ID = "id";
    private static final String TABLE_OVERVIEWOFEXPENSE_COLUM_TOTALEXPENSE = "total";
    private static final String TABLE_OVERVIEWOFEXPENSE_COLUM_REMAININGBUDGET = "remainingbudget";
    private static final String TABLE_OVERVIEWOFEXPENSE_COLUM_MONTH = "month";
    private static final String TABLE_OVERVIEWOFEXPENSE_COLUM_USER_ID = "user_id";

    //Table Expense_reports
    private static String TABLE_EXPENSEREPORTS = "expense_reports";
    private static final String TABLE_EXPENSEREPORT_COLUM_ID = "id";
    private static final String TABLE_EXPENSEREPORT_COLUM_STARTDATE = "startdate";
    private static final String TABLE_EXPENSEREPORT_COLUM_ENDDATE = "enddate";
    private static final String TABLE_EXPENSEREPORT_COLUM_TOTALEXPENSE = "totalexpens";
    private static final String TABLE_EXPENSEREPORT_COLUM_USER_ID = "user_id";

    //Table Notifications
    private static String TABLE_NOTIFICATIONS = "notifications";
    private static final String TABLE_NOTIFICATION_COLUM_ID = "id";
    private static final String TABLE_NOTIFICATION_COLUM_MESSAGE = "message";
    private static final String TABLE_NOTIFICATION_COLUM_DATE = "date";
    private static final String TABLE_NOTIFICATION_COLUM_USER_ID = "user_id";

    //Table view_overrall_report
    private static String TABLE_VIEWOVERALLREPORT = "view_overall_report";
    private static final String TABLE_VIEWOVERALLREPORT_COLUM_ID = "id";
    private static final String TABLE_VIEWOVERALLREPORT_TOTALUSERS = "totalusers";
    private static final String TABLE_VIEWOVERALLREPORT_TOTALEXPENSE = "totalexpense";
    private static final String TABLE_VIEWOVERALLREPORT_USER_ID = "user_id";

    //Table expense_tracking
    private static String TABLE_EXPENSETRACKING = "expense_tracking";
    private static final String TABLE_EXPENSETRACKING_COLUM_ID = "id";
    private static final String TABLE_EXPENSETRACKING_COLUM_CATEGORY = "category";
    private static final String TABLE_EXPENSETRACKING_COLUM_AMOUNT = "amount";
    private static final String TABLE_EXPENSETRACKING_COLUM_DATE = "date";
    private static final String TABLE_EXPENSETRACKING_COLUM_DESCRIPTION = "description";
    private static final String TABLE_EXPENSETRACKING_COLUM_USER_ID = "user_id";
    private static final String TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID = "category_id";

    //Table budget_setting
    private static String TABLE_BUDGETSETTING = "budget_setting";
    private static final String TABLE_BUDGETSETTING_COLUM_ID = "id";
    private static final String TABLE_BUDGETSETTING_COLUM_CATEGORY = "category";
    private static final String TABLE_BUDGETSETTING_COLUM_LIMITAMOUNT = "limitamount";
    private static final String TABLE_BUDGETSETTING_COLUM_MONTH = "month";
    private static final String TABLE_BUDGETSETTING_COLUM_USER_ID = "user_id";
    private static final String TABLE_BUDGETSETTING_COLUM_CATEGORY_ID = "category_id";

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
                + TABLE_USER_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_USER_COLUM_USERNAME + " TEXT, "
                + TABLE_USER_COLUM_PASSWORD + " TEXT, "
                + TABLE_USER_COLUM_EMAIL + " TEXT, "
                + TABLE_USER_COLUM_PHONENUMBER + " TEXT) ";

        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + TABLE_CATEGORY_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_CATEGORY_COLUM_CATEGORYNAME + " TEXT, "
                + TABLE_CATEGORY_COLUM_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_CATEGORIES);

        String CREATE_TABLE_SETTINGS = "CREATE TABLE " + TABLE_SETTINGS + "("
                + TABLE_SETTING_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_SETTING_COLUM_THEMMODE + " TEXT, "
                + TABLE_SETTING_COLUM_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_SETTINGS);

        String CREATE_TABLE_OVERVIEWOFEXPENSES = "CREATE TABLE " + TABLE_OVERVIEWOFEXPENSES + "("
                + TABLE_OVERVIEWOFEXPENSE_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_TOTALEXPENSE + " INTEGER, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_REMAININGBUDGET + " INTEGER, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_MONTH + " DATETIME, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_OVERVIEWOFEXPENSES);

        String CREATE_TABLE_EXPENSEREPORTS = "CREATE TABLE " + TABLE_EXPENSEREPORTS + "("
                + TABLE_EXPENSEREPORT_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_EXPENSEREPORT_COLUM_STARTDATE + " DATETIME, "
                + TABLE_EXPENSEREPORT_COLUM_ENDDATE + " DATETIME, "
                + TABLE_EXPENSEREPORT_COLUM_TOTALEXPENSE + " INTEGER, "
                + TABLE_EXPENSEREPORT_COLUM_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_EXPENSEREPORTS);

        String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + TABLE_NOTIFICATION_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_NOTIFICATION_COLUM_MESSAGE + " TEXT, "
                + TABLE_NOTIFICATION_COLUM_DATE + " DATETIME, "
                + TABLE_NOTIFICATION_COLUM_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);

        String CREATE_TABLE_VIEWOVERALLREPORT = "CREATE TABLE " + TABLE_VIEWOVERALLREPORT + "("
                + TABLE_VIEWOVERALLREPORT_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_VIEWOVERALLREPORT_TOTALUSERS + " INTEGER, "
                + TABLE_VIEWOVERALLREPORT_TOTALEXPENSE + " INTEGER, "
                + TABLE_VIEWOVERALLREPORT_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_VIEWOVERALLREPORT);

        String CREATE_TABLE_EXPENSETRACKING = "CREATE TABLE " + TABLE_EXPENSETRACKING + "("
                + TABLE_EXPENSETRACKING_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_EXPENSETRACKING_COLUM_CATEGORY + " TEXT, "
                + TABLE_EXPENSETRACKING_COLUM_AMOUNT + " INTEGER, "
                + TABLE_EXPENSETRACKING_COLUM_DATE + " DATETIME, "
                + TABLE_EXPENSETRACKING_COLUM_DESCRIPTION + " TEXT, "
                + TABLE_EXPENSETRACKING_COLUM_USER_ID + " INTEGER, "
                + TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_EXPENSETRACKING);

        String  CREATE_TABLE_BUDGETSETTING= "CREATE TABLE " + TABLE_BUDGETSETTING + "("
                + TABLE_BUDGETSETTING_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_BUDGETSETTING_COLUM_CATEGORY + " TEXT, "
                + TABLE_BUDGETSETTING_COLUM_LIMITAMOUNT + " INTEGER, "
                + TABLE_BUDGETSETTING_COLUM_MONTH + " DATETIME, "
                + TABLE_BUDGETSETTING_COLUM_USER_ID + " INTEGER, "
                + TABLE_BUDGETSETTING_COLUM_CATEGORY_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_BUDGETSETTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERVIEWOFEXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSEREPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIEWOVERALLREPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSETRACKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGETSETTING);
        onCreate(db);
    }

    public long addUser(User user){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_COLUM_USERNAME, user.getUsername());
        values.put(TABLE_USER_COLUM_PASSWORD, user.getPassword());
        values.put(TABLE_USER_COLUM_EMAIL, user.getEmail());
        values.put(TABLE_USER_COLUM_PHONENUMBER, user.getPhonenumber());
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        return id;
    }

    public User getUserByUsernameAndPasword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{TABLE_USER_COLUM_ID, TABLE_USER_COLUM_USERNAME, TABLE_USER_COLUM_PASSWORD, TABLE_USER_COLUM_EMAIL, TABLE_USER_COLUM_PHONENUMBER},
                TABLE_USER_COLUM_USERNAME + " = ? AND " + TABLE_USER_COLUM_PASSWORD + " = ?",
                new String[]{username, password},
                null, null, null );
        User user = null;
        if(cursor.moveToFirst()){
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }
        cursor.close();
        db.close();
        return user;
    }
    public Cursor getAllExpenseReports(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT bs.category AS category, " +
                        "SUM(et.amount) AS total_amount, " +
                        "bs.limitamount - SUM(et.amount) AS remaining_budget " +
                        "FROM budget_setting bs " +
                        "LEFT JOIN expense_tracking et " +
                        "ON bs.category_id = et.category_id AND bs.user_id = et.user_id " +
                        "WHERE bs.user_id = ? " +
                        "GROUP BY bs.category, bs.limitamount";

        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }
    public int getTotalRemaining(int userId, int month) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalRemaining = 0;
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);

        // Tổng budget
        Cursor cursorBudget = db.rawQuery(
                "SELECT IFNULL(SUM(limitamount), 0) AS total_budget FROM budget_setting " +
                        "WHERE user_id = ? AND strftime('%m', month) = ?",
                new String[]{String.valueOf(userId), monthStr});
        int totalBudget = 0;
        if(cursorBudget.moveToFirst()){
            totalBudget = cursorBudget.getInt(cursorBudget.getColumnIndexOrThrow("total_budget"));
        }
        cursorBudget.close();

        // Tổng spent
        Cursor cursorSpent = db.rawQuery(
                "SELECT SUM(amount) AS total_spent FROM expense_tracking " +
                        "WHERE user_id = ? AND strftime('%m', date) = ?",
                new String[]{String.valueOf(userId), monthStr});
        int totalSpent = 0;
        if(cursorSpent.moveToFirst()){
            totalSpent = cursorSpent.getInt(cursorSpent.getColumnIndexOrThrow("total_spent"));
        }
        cursorSpent.close();

        totalRemaining = totalBudget - totalSpent;
        return totalRemaining;
    }


    public Cursor getExpenseDetailsByMonth(int userId, int month) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT bs.category AS category, " +
                "IFNULL(SUM(et.amount),0) AS amount_used " +
                "FROM budget_setting bs " +
                "LEFT JOIN expense_tracking et " +
                "ON bs.category = et.category AND bs.user_id = et.user_id " +
                "AND strftime('%m', et.date) = ? " +
                "WHERE bs.user_id = ? AND strftime('%m', bs.month) = ? " +
                "GROUP BY bs.category";

        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
        return db.rawQuery(query, new String[]{monthStr, String.valueOf(userId), monthStr});
    }
    public long addBudgetSetting(String category, int limitAmount, String date, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_BUDGETSETTING_COLUM_CATEGORY, category);
        values.put(TABLE_BUDGETSETTING_COLUM_LIMITAMOUNT, limitAmount);
        values.put(TABLE_BUDGETSETTING_COLUM_MONTH, date);
        values.put(TABLE_BUDGETSETTING_COLUM_USER_ID, userId);

        long id = db.insert(TABLE_BUDGETSETTING, null, values);
        db.close();
        return id;
    }

    public int updateBudgetSetting(int id, String category, int limitAmount, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_BUDGETSETTING_COLUM_CATEGORY, category);
        values.put(TABLE_BUDGETSETTING_COLUM_LIMITAMOUNT, limitAmount);
        values.put(TABLE_BUDGETSETTING_COLUM_MONTH, date);

        return db.update(TABLE_BUDGETSETTING, values, "id=?", new String[]{String.valueOf(id)});
    }

    public int deleteBudgetSetting(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BUDGETSETTING, "id=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<BudgetItem> getAllBudgetSettings(int userId) {
        ArrayList<BudgetItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, category, limitamount, month FROM budget_setting WHERE user_id=?",
                new String[]{String.valueOf(userId)}
        );

        if(cursor.moveToFirst()){
            do{
                list.add(new BudgetItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                ));
            }while(cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

}
