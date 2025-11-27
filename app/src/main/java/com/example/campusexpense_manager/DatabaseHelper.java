    package com.example.campusexpense_manager;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import java.util.Calendar;


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
                    + TABLE_OVERVIEWOFEXPENSE_COLUM_MONTH + " TEXT, "
                    + TABLE_OVERVIEWOFEXPENSE_COLUM_USER_ID + " INTEGER)";
            db.execSQL(CREATE_TABLE_OVERVIEWOFEXPENSES);

            String CREATE_TABLE_EXPENSEREPORTS = "CREATE TABLE " + TABLE_EXPENSEREPORTS + "("
                    + TABLE_EXPENSEREPORT_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLE_EXPENSEREPORT_COLUM_STARTDATE + " TEXT, "
                    + TABLE_EXPENSEREPORT_COLUM_ENDDATE + " TEXT, "
                    + TABLE_EXPENSEREPORT_COLUM_TOTALEXPENSE + " INTEGER, "
                    + TABLE_EXPENSEREPORT_COLUM_USER_ID + " INTEGER)";
            db.execSQL(CREATE_TABLE_EXPENSEREPORTS);

            String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                    + TABLE_NOTIFICATION_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLE_NOTIFICATION_COLUM_MESSAGE + " TEXT, "
                    + TABLE_NOTIFICATION_COLUM_DATE + " TEXT, "
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
                    + TABLE_EXPENSETRACKING_COLUM_DATE + " TEXT, "
                    + TABLE_EXPENSETRACKING_COLUM_DESCRIPTION + " TEXT, "
                    + TABLE_EXPENSETRACKING_COLUM_USER_ID + " INTEGER, "
                    + TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID + " INTEGER)";
            db.execSQL(CREATE_TABLE_EXPENSETRACKING);

            String  CREATE_TABLE_BUDGETSETTING= "CREATE TABLE " + TABLE_BUDGETSETTING + "("
                    + TABLE_BUDGETSETTING_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLE_BUDGETSETTING_COLUM_CATEGORY + " TEXT, "
                    + TABLE_BUDGETSETTING_COLUM_LIMITAMOUNT + " INTEGER, "
                    + TABLE_BUDGETSETTING_COLUM_MONTH + " TEXT, "
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
        public Cursor getAllExpenseOverview(int userId, int month) {
            SQLiteDatabase db = this.getReadableDatabase();

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            String yearMonth = String.format("%d-%02d", year, month);

            String query =
                    "SELECT c.categoryname AS category, " +
                            "IFNULL(SUM(et.amount),0) AS total_amount, " +
                            "(bs.limitamount - IFNULL(SUM(et.amount),0)) AS remaining_budget " +
                            "FROM budget_setting bs " +
                            "JOIN categories c ON bs.category_id = c.id " +
                            "LEFT JOIN expense_tracking et " +
                            "ON bs.category_id = et.category_id " +
                            "AND bs.user_id = et.user_id " +
                            "AND strftime('%Y-%m', et.date) = ? " +
                            "WHERE bs.user_id = ? " +
                            "AND strftime('%Y-%m', bs.month) = ? " +
                            "GROUP BY bs.category_id, bs.limitamount, c.categoryname";

            return db.rawQuery(query, new String[]{yearMonth, String.valueOf(userId), yearMonth});
        }

        public int getTotalRemaining(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();


            int totalBudget = 0;
            int totalSpent = 0;

            // 1. Lấy tổng limitamount từ bảng budget_setting theo user_id và tháng (yyyy-MM)
            String sqlBudget = "SELECT IFNULL(SUM(limitamount), 0) AS total_budget " +
                    "FROM budget_setting " +
                    "WHERE user_id = ? AND month = ?";

            try (Cursor cursorBudget = db.rawQuery(sqlBudget, new String[]{String.valueOf(userId), yearMonth})) {
                if (cursorBudget.moveToFirst()) {
                    totalBudget = cursorBudget.getInt(cursorBudget.getColumnIndexOrThrow("total_budget"));
                }
            } // Cursor tự đóng nhờ try-with-resources

            // 2. Lấy tổng chi tiêu thực tế từ expense_tracking theo user_id và tháng
            String sqlSpent = "SELECT IFNULL(SUM(amount), 0) AS total_spent " +
                    "FROM expense_tracking " +
                    "WHERE user_id = ? AND strftime('%Y-%m', date) = ?";

            try (Cursor cursorSpent = db.rawQuery(sqlSpent, new String[]{String.valueOf(userId), yearMonth})) {
                if (cursorSpent.moveToFirst()) {
                    totalSpent = cursorSpent.getInt(cursorSpent.getColumnIndexOrThrow("total_spent"));
                }
            }

            return totalBudget - totalSpent;
        }
        public Cursor getExpenseDetailsByYearMonth(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT c.categoryname AS category, " +
                    "IFNULL(SUM(et.amount), 0) AS amount_used " +
                    "FROM categories c " +
                    "JOIN budget_setting bs ON bs.category_id = c.id AND bs.user_id = ? AND bs.month = ? " +
                    "LEFT JOIN expense_tracking et ON et.category_id = c.id " +
                    "AND et.user_id = ? " +
                    "AND strftime('%Y-%m', et.date) = ? " +
                    "WHERE c.user_id = ? " +
                    "GROUP BY c.id, c.categoryname " +
                    "ORDER BY amount_used DESC";

            return db.rawQuery(query, new String[]{
                    String.valueOf(userId),
                    yearMonth,
                    String.valueOf(userId),
                    yearMonth,
                    String.valueOf(userId)
            });
        }
        public Cursor getExpenseOverviewByYearMonth(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();

            String sql ="SELECT c.categoryname AS category,\n" +
                    "            IFNULL(SUM(et.amount), 0) AS amount_used,\n" +
                    "            IFNULL(bs.limitamount, 0) AS budget_limit,\n" +
                    "            (IFNULL(bs.limitamount, 0) - IFNULL(SUM(et.amount), 0)) AS remaining\n" +
                    "        FROM categories c\n" +
                    "        LEFT JOIN budget_setting bs \n" +
                    "            ON bs.category = c.categoryname \n" +
                    "            AND bs.user_id = ? \n" +
                    "            AND bs.month = ?\n" +
                    "        LEFT JOIN expense_tracking et \n" +
                    "            ON et.category_id = c.id \n" +
                    "            AND et.user_id = ?\n" +
                    "            AND strftime('%Y-%m', et.date) = ?\n" +
                    "        WHERE c.user_id = ?\n" +
                    "        GROUP BY c.id, c.categoryname\n" +
                    "        ORDER BY remaining ASC";

            return db.rawQuery(sql, new String[]{
                    String.valueOf(userId), yearMonth,
                    String.valueOf(userId), yearMonth,
                    String.valueOf(userId)
            });
        }

        //
        public long addBudgetSetting(int userId, String category, int categoryId, int limitAmount, String month) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("category", category);
            values.put("category_id", categoryId);
            values.put("limitamount", limitAmount);
            values.put("month", month);  // Now "yyyy-MM"

            return db.insert("budget_setting", null, values);
        }
        public int updateBudgetSetting(int id, String category, int categoryId, int limitAmount, String month) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("category", category);
            values.put("category_id", categoryId);
            values.put("limitamount", limitAmount);
            values.put("month", month);  // Now "yyyy-MM"

            return db.update("budget_setting", values, "id=?", new String[]{String.valueOf(id)});
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

            if (cursor.moveToFirst()) {
                do {
                    list.add(new BudgetItem(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3)
                    ));
                } while (cursor.moveToNext());
            }

            cursor.close();
            return list;
        }
        // THÊM VÀO DatabaseHelper.java
        public int addCategoryAndReturnId(int userId, String categoryName) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("categoryname", categoryName.trim());
            cv.put("user_id", userId);

            long insertedId = db.insert("categories", null, cv);
            db.close();

            return insertedId > 0 ? (int) insertedId : -1;
        }
        public int getCategoryIdByName(String categoryName, int userId) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(
                    TABLE_CATEGORIES,
                    new String[]{TABLE_CATEGORY_COLUM_ID},
                    TABLE_CATEGORY_COLUM_CATEGORYNAME + " = ? AND " + TABLE_CATEGORY_COLUM_USER_ID + " = ?",
                    new String[]{categoryName, String.valueOf(userId)},
                    null, null, null
            );

            int categoryId = -1;
            if (cursor.moveToFirst()) {
                categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_CATEGORY_COLUM_ID));
            }
            cursor.close();
            return categoryId;
        }


    }
