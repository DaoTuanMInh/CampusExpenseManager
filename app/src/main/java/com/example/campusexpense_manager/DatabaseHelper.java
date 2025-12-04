package com.example.campusexpense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

    import androidx.annotation.Nullable;

    import java.util.ArrayList;

    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "APDP_Expense";
        private static final int DATABASE_VERSION = 1;


    //Table User
    private static String TABLE_USER = "users";
    private static final String TABLE_USER_COLUM_ID = "id";
    private static final String TABLE_USER_COLUM_USERNAME = "username";
    private static final String TABLE_USER_COLUM_FULLNAME = "fullname";
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

//    //Table Notifications
//    private static String TABLE_NOTIFICATIONS = "notifications";
//    private static final String TABLE_NOTIFICATION_COLUM_ID = "id";
//    private static final String TABLE_NOTIFICATION_COLUM_MESSAGE = "message";
//    private static final String TABLE_NOTIFICATION_COLUM_DATE = "date";
//    private static final String TABLE_NOTIFICATION_COLUM_USER_ID = "user_id";

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

        // New Table: recurring_expenses
        private static String TABLE_RECURRING_EXPENSES = "recurring_expenses";
        private static final String TABLE_RECURRING_COLUM_ID = "id";
        private static final String TABLE_RECURRING_COLUM_CATEGORY = "category";
        private static final String TABLE_RECURRING_COLUM_AMOUNT = "amount";
        private static final String TABLE_RECURRING_COLUM_START_MONTH = "start_month";
        private static final String TABLE_RECURRING_COLUM_END_MONTH = "end_month";
        private static final String TABLE_RECURRING_COLUM_USER_ID = "user_id";
        private static final String TABLE_RECURRING_COLUM_CATEGORY_ID = "category_id";

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
                + TABLE_USER_COLUM_FULLNAME + " TEXT, "
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

//            String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
//                    + TABLE_NOTIFICATION_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + TABLE_NOTIFICATION_COLUM_MESSAGE + " TEXT, "
//                    + TABLE_NOTIFICATION_COLUM_DATE + " TEXT, "
//                    + TABLE_NOTIFICATION_COLUM_USER_ID + " INTEGER)";
//            db.execSQL(CREATE_TABLE_NOTIFICATIONS);

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

        String CREATE_TABLE_RECURRING_EXPENSES = "CREATE TABLE IF NOT EXISTS " + TABLE_RECURRING_EXPENSES + " (" +
                TABLE_RECURRING_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_RECURRING_COLUM_CATEGORY + " TEXT, " +
                TABLE_RECURRING_COLUM_CATEGORY_ID + " INTEGER, " +
                TABLE_RECURRING_COLUM_AMOUNT + " INTEGER, " +
                TABLE_RECURRING_COLUM_START_MONTH + " TEXT, " +
                TABLE_RECURRING_COLUM_END_MONTH + " TEXT, " +
                TABLE_RECURRING_COLUM_USER_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_RECURRING_EXPENSES);
        }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERVIEWOFEXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSEREPORTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSETRACKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGETSETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECURRING_EXPENSES);
        onCreate(db);
    }
//Long
    public long addUser(User user){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_COLUM_USERNAME, user.getUsername());
        values.put(TABLE_USER_COLUM_FULLNAME, user.getFullname());
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
                new String[]{TABLE_USER_COLUM_ID, TABLE_USER_COLUM_USERNAME, TABLE_USER_COLUM_FULLNAME, TABLE_USER_COLUM_PASSWORD, TABLE_USER_COLUM_EMAIL, TABLE_USER_COLUM_PHONENUMBER},
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
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }
        cursor.close();
        db.close();
        return user;
    }
    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                new String[]{"id", "username", "fullname", "password", "email", "phonenumber"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }
        cursor.close();
        return user;
    }
        public boolean isPhoneExists(String phone) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT 1 FROM users WHERE phonenumber = ?", new String[]{phone});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }
        public boolean isEmailExists(String email) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT 1 FROM users WHERE email = ?", new String[]{email});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }
    //Minh
        public int getTotalRemaining(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT COALESCE(SUM(b.limitamount),0) - COALESCE(SUM(e.amount),0)\n" +
                    "        FROM categories c\n" +
                    "        LEFT JOIN budget_setting b ON c.id = b.category_id AND b.user_id = ? AND substr(b.month,1,7) = ?\n" +
                    "        LEFT JOIN expense_tracking e ON c.id = e.category_id AND e.user_id = ? AND substr(e.date,1,7) = ?\n" +
                    "        WHERE c.user_id = ?";

            Cursor c = db.rawQuery(sql, new String[]{
                    String.valueOf(userId), yearMonth, String.valueOf(userId), yearMonth, String.valueOf(userId)
            });
            int result = c.moveToFirst() ? c.getInt(0) : 0;
            c.close();
            return result;
        }
        public Cursor getExpenseDetailsByYearMonth(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();

            String sql = "SELECT c.categoryname AS category, COALESCE(SUM(e.amount),0) AS amount_used\n" +
                    "        FROM categories c\n" +
                    "        LEFT JOIN expense_tracking e ON c.id = e.category_id AND e.user_id = ? AND substr(e.date,1,7) = ?\n" +
                    "        WHERE c.user_id = ?\n" +
                    "        GROUP BY c.id HAVING amount_used > 0\n" +
                    "        ORDER BY amount_used DESC";

            return db.rawQuery(sql, new String[]{String.valueOf(userId), yearMonth, String.valueOf(userId)});
        }
        public Cursor getExpenseOverviewByYearMonth(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();

            String sql = "SELECT c.categoryname AS category,\n" +
                    "            COALESCE(SUM(e.amount), 0) AS amount_used,\n" +
                    "            COALESCE(b.limitamount, 0) AS budget_limit,\n" +
                    "            COALESCE(b.limitamount, 0) - COALESCE(SUM(e.amount), 0) AS remaining\n" +
                    "        FROM categories c\n" +
                    "        LEFT JOIN expense_tracking e ON c.id = e.category_id AND e.user_id = ? AND substr(e.date,1,7) = ?\n" +
                    "        LEFT JOIN budget_setting b ON c.id = b.category_id AND b.user_id = ? AND substr(b.month,1,7) = ?\n" +
                    "        WHERE c.user_id = ?\n" +
                    "        GROUP BY c.id, c.categoryname\n" +
                    "        HAVING amount_used > 0 OR budget_limit > 0\n" +
                    "        ORDER BY c.categoryname";
            return db.rawQuery(sql, new String[]{
                    String.valueOf(userId), yearMonth,
                    String.valueOf(userId), yearMonth,
                    String.valueOf(userId)
            });
        }
//        // Áp dụng recurring cho một tháng cụ thể (thêm vào expense_tracking nếu chưa có)
//        public void applyRecurringForMonth(int userId, String yearMonth) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            // Lấy tất cả recurring áp dụng cho tháng này
//            String query = "SELECT id, category, category_id, amount, start_month, end_month " +
//                    "FROM " + TABLE_RECURRING_EXPENSES +
//                    " WHERE user_id = ? AND start_month <= ? " +
//                    "AND (end_month IS NULL OR end_month >= ?)";
//
//            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), yearMonth, yearMonth});
//
//            while (cursor.moveToNext()) {
//                int recurringId = cursor.getInt(0);
//                String category = cursor.getString(1);
//                int categoryId = cursor.getInt(2);
//                int amount = cursor.getInt(3);
//                String startMonth = cursor.getString(4);
//                String endMonth = cursor.getString(5); // có thể null
//
//                // Kiểm tra xem đã áp dụng cho tháng này chưa (tìm expense với description="Recurring [recurringId]", date=yearMonth + "-01")
//                String checkQuery = "SELECT id FROM " + TABLE_EXPENSETRACKING +
//                        " WHERE user_id = ? AND category_id = ? AND amount = ? " +
//                        " AND date LIKE ? AND description = ?";
//
//                String expenseDate = yearMonth + "-01"; // ngày đầu tháng
//                String description = "Recurring #" + recurringId; // unique để tránh trùng
//
//                Cursor checkCursor = db.rawQuery(checkQuery, new String[]{
//                        String.valueOf(userId), String.valueOf(categoryId), String.valueOf(amount),
//                        expenseDate + "%", description
//                });
//
//                if (checkCursor.getCount() == 0) {
//                    // Chưa có → thêm vào expense_tracking
//                    ContentValues cv = new ContentValues();
//                    cv.put(TABLE_EXPENSETRACKING_COLUM_USER_ID, userId);
//                    cv.put(TABLE_EXPENSETRACKING_COLUM_CATEGORY, category);
//                    cv.put(TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID, categoryId);
//                    cv.put(TABLE_EXPENSETRACKING_COLUM_AMOUNT, amount);
//                    cv.put(TABLE_EXPENSETRACKING_COLUM_DATE, expenseDate);
//                    cv.put(TABLE_EXPENSETRACKING_COLUM_DESCRIPTION, description);
//
//                    db.insert(TABLE_EXPENSETRACKING, null, cv);
//                }
//                checkCursor.close();
//            }
//            cursor.close();
//        }
        // Áp dụng tất cả recurring expenses cho tháng hiện tại và tương lai (nếu cần)
//        public void applyAllRecurringExpenses(int userId) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            Calendar cal = Calendar.getInstance();
//            String currentYearMonth = cal.get(Calendar.YEAR) + "-" + String.format("%02d", cal.get(Calendar.MONTH) + 1);
//
//            String query = "SELECT id, category, category_id, amount, start_month, end_month " +
//                    "FROM recurring_expenses WHERE user_id = ?";
//
//            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
//
//            while (cursor.moveToNext()) {
//                int recId = cursor.getInt(0);
//                String category = cursor.getString(1);
//                int catId = cursor.getInt(2);
//                int amount = cursor.getInt(3);
//                String start = cursor.getString(4);
//                String end = cursor.getString(5); // có thể null
//
//                // Duyệt từng tháng từ start đến hiện tại (và tương lai nếu end null)
//                Calendar tempCal = Calendar.getInstance();
//                String[] startParts = start.split("-");
//                tempCal.set(Integer.parseInt(startParts[0]), Integer.parseInt(startParts[1]) - 1, 1);
//
//                while (!tempCal.after(Calendar.getInstance())) {
//                    String monthStr = tempCal.get(Calendar.YEAR) + "-" + String.format("%02d", tempCal.get(Calendar.MONTH) + 1);
//
//                    // Bỏ qua nếu chưa tới start hoặc đã qua end
//                    if (monthStr.compareTo(start) < 0 || (end != null && monthStr.compareTo(end) > 0)) {
//                        tempCal.add(Calendar.MONTH, 1);
//                        continue;
//                    }
//
//                    // Kiểm tra đã tồn tại expense này chưa
//                    String check = "SELECT id FROM expense_tracking WHERE user_id = ? AND category_id = ? AND amount = ? " +
//                            "AND date LIKE ? AND description LIKE ?";
//                    Cursor exist = db.rawQuery(check, new String[]{
//                            String.valueOf(userId), String.valueOf(catId), String.valueOf(amount),
//                            monthStr + "%", "Recurring #%"
//                    });
//
//                    if (exist.getCount() == 0) {
//                        ContentValues cv = new ContentValues();
//                        cv.put("user_id", userId);
//                        cv.put("category", category);
//                        cv.put("category_id", catId);
//                        cv.put("amount", amount);
//                        cv.put("date", monthStr + "-01");
//                        cv.put("description", "Recurring #" + recId);
//                        db.insert("expense_tracking", null, cv);
//                    }
//                    exist.close();
//
//                    tempCal.add(Calendar.MONTH, 1);
//                }
//            }
//            cursor.close();
//        }
        public void applyRecurringToExpenseTracking(int userId) {
            SQLiteDatabase db = this.getWritableDatabase();

            // XÓA TẤT CẢ ngân sách ĐỊNH KỲ cũ của user này trước (an toàn nhất)
            db.delete("budget_setting",
                    "user_id = ? AND id IN (SELECT bs.id FROM budget_setting bs LEFT JOIN recurring_expenses re ON bs.category_id = re.category_id AND bs.user_id = re.user_id AND substr(bs.month,1,7) >= substr(re.start_month,1,7) WHERE re.user_id = ?)",
                    new String[]{String.valueOf(userId), String.valueOf(userId)});

            String sql = "SELECT id, category, category_id, amount, start_month, end_month " +
                    "FROM " + TABLE_RECURRING_EXPENSES + " WHERE user_id = ?";

            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});
            Calendar cal = Calendar.getInstance();

            while (cursor.moveToNext()) {
                String category = cursor.getString(1);
                int catId = cursor.getInt(2);
                int limit = cursor.getInt(3);
                String start = cursor.getString(4);
                String end = cursor.getString(5);

                // Chuyển start_month thành Calendar
                String[] parts = start.split("-");
                cal.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, 1);

                Calendar today = Calendar.getInstance();
                while (!cal.after(today)) {
                    String monthStr = String.format("%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

                    // Nếu có end_month và đã qua thì bỏ qua
                    if (end != null && monthStr.compareTo(end.substring(0, 7)) > 0) {
                        cal.add(Calendar.MONTH, 1);
                        continue;
                    }

                    // DÙNG UPSERT: Nếu đã tồn tại thì UPDATE, không thì INSERT
                    ContentValues cv = new ContentValues();
                    cv.put("user_id", userId);
                    cv.put("category", category);
                    cv.put("category_id", catId);
                    cv.put("limitamount", limit);
                    cv.put("month", monthStr);

                    int updated = db.update("budget_setting", cv,
                            "user_id = ? AND category_id = ? AND month = ?",
                            new String[]{String.valueOf(userId), String.valueOf(catId), monthStr});

                    if (updated == 0) {
                        db.insert("budget_setting", null, cv);
                    }

                    cal.add(Calendar.MONTH, 1);
                }
            }
            cursor.close();
        }
        //Ánh
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
    // V.anh
    public long addRecurringExpense(int userId, String category, int categoryId, int limitAmount,
                                    String startMonth, String endMonth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_RECURRING_COLUM_USER_ID, userId);
        cv.put(TABLE_RECURRING_COLUM_CATEGORY, category);
        cv.put(TABLE_RECURRING_COLUM_CATEGORY_ID, categoryId);
        cv.put(TABLE_RECURRING_COLUM_AMOUNT, limitAmount);
        cv.put(TABLE_RECURRING_COLUM_START_MONTH, startMonth);
        cv.put(TABLE_RECURRING_COLUM_END_MONTH, endMonth);
        return db.insert(TABLE_RECURRING_EXPENSES, null, cv);
    }

        public int updateRecurringExpense(int id, String category, int categoryId, int limitAmount,
                                          String startMonth, String endMonth) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TABLE_RECURRING_COLUM_CATEGORY, category);
            cv.put(TABLE_RECURRING_COLUM_CATEGORY_ID, categoryId);
            cv.put(TABLE_RECURRING_COLUM_AMOUNT, limitAmount);
            cv.put(TABLE_RECURRING_COLUM_START_MONTH, startMonth);
            cv.put(TABLE_RECURRING_COLUM_END_MONTH, endMonth);
            return db.update(TABLE_RECURRING_EXPENSES, cv, "id=?", new String[]{String.valueOf(id)});
        }

        public int deleteRecurringExpense(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_RECURRING_EXPENSES, "id=?", new String[]{String.valueOf(id)});
        }

        public ArrayList<RecurringItem> getAllRecurringExpenses(int userId) {
            ArrayList<RecurringItem> list = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT id, category, amount, start_month, end_month FROM " + TABLE_RECURRING_EXPENSES +
                            " WHERE user_id = ? ORDER BY start_month DESC",
                    new String[]{String.valueOf(userId)}
            );
            if (cursor.moveToFirst()) {
                do {
                    list.add(new RecurringItem(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }
        public long addExpense(int userId, String category, int categoryId,
                               int amount, String date, String description) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TABLE_EXPENSETRACKING_COLUM_USER_ID, userId);
            cv.put(TABLE_EXPENSETRACKING_COLUM_CATEGORY, category);
            cv.put(TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID, categoryId);
            cv.put(TABLE_EXPENSETRACKING_COLUM_AMOUNT, amount);
            cv.put(TABLE_EXPENSETRACKING_COLUM_DATE, date);           // yyyy-MM-dd
            cv.put(TABLE_EXPENSETRACKING_COLUM_DESCRIPTION, description);

            long id = db.insert(TABLE_EXPENSETRACKING, null, cv);
            db.close();
            return id;
        }

        public int updateExpense(int id, String category, int categoryId,
                                 int amount, String date, String description) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TABLE_EXPENSETRACKING_COLUM_CATEGORY, category);
            cv.put(TABLE_EXPENSETRACKING_COLUM_CATEGORY_ID, categoryId);
            cv.put(TABLE_EXPENSETRACKING_COLUM_AMOUNT, amount);
            cv.put(TABLE_EXPENSETRACKING_COLUM_DATE, date);
            cv.put(TABLE_EXPENSETRACKING_COLUM_DESCRIPTION, description);

            int rows = db.update(TABLE_EXPENSETRACKING, cv,
                    TABLE_EXPENSETRACKING_COLUM_ID + "=?", new String[]{String.valueOf(id)});
            db.close();
            return rows;
        }

        public int deleteExpense(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            int rows = db.delete(TABLE_EXPENSETRACKING,
                    TABLE_EXPENSETRACKING_COLUM_ID + "=?", new String[]{String.valueOf(id)});
            db.close();
            return rows;
        }

        public ArrayList<ExpenseItem> getAllExpenses(int userId) {
            ArrayList<ExpenseItem> list = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT id, category, category_id, amount, date, description " +
                    "FROM " + TABLE_EXPENSETRACKING +
                    " WHERE user_id = ? ORDER BY date DESC";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                do {
                    list.add(new ExpenseItem(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            userId
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }

        public long getTotalBudgetForMonth(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT SUM(limitamount) FROM budget_setting WHERE user_id = ? AND month = ?",
                    new String[]{String.valueOf(userId), yearMonth}
            );
            long total = 0;
            if (cursor.moveToFirst()) total = cursor.getLong(0);
            cursor.close();
            return total;
        }
        public ExpenseItem getExpenseById(int expenseId, int userId) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT id, category, category_id, amount, date, description FROM " + TABLE_EXPENSETRACKING +
                            " WHERE id = ? AND user_id = ?",
                    new String[]{String.valueOf(expenseId), String.valueOf(userId)}
            );

            if (cursor.moveToFirst()) {
                ExpenseItem item = new ExpenseItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        userId
                );
                cursor.close();
                return item;
            }
            cursor.close();
            return null;
        }
        public long getTotalExpenseForMonth(int userId, String yearMonth) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT COALESCE(SUM(amount),0) FROM " + TABLE_EXPENSETRACKING +
                    " WHERE user_id = ? AND substr(date,1,7) = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), yearMonth});
            long total = 0;
            if (cursor.moveToFirst()) total = cursor.getLong(0);
            cursor.close();
            return total;
        }




    }
