package com.example.campusexpense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APDP_Expense";
    private static final int DATABASE_VERSION = 1;

    //Table Role
    private  static  String TABLE_ROLE = "role";
    private  static String TABLE_ROLE_COLUM_ID = "id";
    private static String TABLE_ROLE_COLUM_ROLENAME = "rolename";

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
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + "("
                + TABLE_ROLE_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_ROLE_COLUM_ROLENAME + "TEXT)";
        db.execSQL(CREATE_TABLE_ROLE);

        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
                + TABLE_USER_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_USER_COLUM_USERNAME + "TEXT, "
                + TABLE_USER_COLUM_PASSWORD + "TEXT, "
                + TABLE_USER_COLUM_EMAIL + "TEXT, "
                + TABLE_USER_COLUM_PHONENUMBER + "TEXT, "
                + TABLE_USER_COLUM_ROLE_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + TABLE_CATEGORY_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_CATEGORY_COLUM_CATEGORYNAME + "TEXT, "
                + TABLE_CATEGORY_COLUM_USER_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_CATEGORIES);

        String CREATE_TABLE_SETTINGS = "CREATE TABLE " + TABLE_SETTINGS + "("
                + TABLE_SETTING_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_SETTING_COLUM_THEMMODE + "TEXT, "
                + TABLE_SETTING_COLUM_USER_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_SETTINGS);

        String CREATE_TABLE_OVERVIEWOFEXPENSES = "CREATE TABLE " + TABLE_OVERVIEWOFEXPENSES + "("
                + TABLE_OVERVIEWOFEXPENSE_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_TOTALEXPENSE + "INTERGER, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_REMAININGBUDGET + "INTERGER, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_MONTH + "DATETIME, "
                + TABLE_OVERVIEWOFEXPENSE_COLUM_USER_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_OVERVIEWOFEXPENSES);

        String CREATE_TABLE_EXPENSEREPORTS = "CREATE TABLE " + TABLE_EXPENSEREPORTS + "("
                + TABLE_EXPENSEREPORT_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_EXPENSEREPORT_COLUM_STARTDATE + "DATETIME, "
                + TABLE_EXPENSEREPORT_COLUM_ENDDATE + "DATETIME, "
                + TABLE_EXPENSEREPORT_COLUM_TOTALEXPENSE + "INTERGER, "
                + TABLE_EXPENSEREPORT_COLUM_USER_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_EXPENSEREPORTS);

        String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + TABLE_NOTIFICATION_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_NOTIFICATION_COLUM_MESSAGE + "TEXXT, "
                + TABLE_NOTIFICATION_COLUM_DATE + "DATETIME, "
                + TABLE_NOTIFICATION_COLUM_USER_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);

        String CREATE_TABLE_VIEWOVERALLREPORT = "CREATE TABLE " + TABLE_VIEWOVERALLREPORT + "("
                + TABLE_VIEWOVERALLREPORT_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_VIEWOVERALLREPORT_TOTALUSERS + "INTERGER, "
                + TABLE_VIEWOVERALLREPORT_TOTALEXPENSE + "INTERGER, "
                + TABLE_VIEWOVERALLREPORT_USER_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_VIEWOVERALLREPORT);

        String CREATE_TABLE_EXPENSETRACKING = "CREATE TABLE " + TABLE_EXPENSETRACKING + "("
                + TABLE_EXPENSEREPORT_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_EXPENSETRACKING_COLUM_CATEGORY + "TEXT, "
                + TABLE_EXPENSETRACKING_COLUM_AMOUNT + "INTERGER, "
                + TABLE_EXPENSETRACKING_COLUM_DATE + "DATETIME, "
                + TABLE_EXPENSETRACKING_COLUM_DESCRIPTION + "TEXT, "
                + TABLE_EXPENSETRACKING_COLUM_USER_ID + "INTERGER, "
                + TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_EXPENSETRACKING);

        String CREATE_TABLE_BUDGETSETTING = "CREATE TABLE " + TABLE_BUDGETSETTING + "("
                + TABLE_BUDGETSETTING_COLUM_ID + "INTERGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_BUDGETSETTING_COLUM_CATEGORY + "TEXT, "
                + TABLE_BUDGETSETTING_COLUM_LIMITAMOUNT + "INTERGER, "
                + TABLE_BUDGETSETTING_COLUM_MONTH + "DATETIME, "
                + TABLE_BUDGETSETTING_COLUM_USER_ID + "INTERGER, "
                + TABLE_BUDGETSETTING_COLUM_CATEGORY_ID + "INTERGER)";
        db.execSQL(CREATE_TABLE_BUDGETSETTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
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
                    cursor.getInt(4)
            );
        }
        cursor.close();
        db.close();
        return user;
    }
}
