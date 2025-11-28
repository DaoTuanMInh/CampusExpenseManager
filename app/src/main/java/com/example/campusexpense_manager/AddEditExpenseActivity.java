package com.example.campusexpense_manager;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddEditExpenseActivity extends AppCompatActivity {

    EditText etDescription, etAmount;
    Spinner spinnerCategory;
    TextView tvDate;
    Button btnSave, btnCancel;
    DatabaseHelper dbHelper;
    int userId;
    ExpenseItem currentExpense = null; // null = add mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expense);

        dbHelper = new DatabaseHelper(this);
        userId = getIntent().getIntExtra("user_id", -1);

        initViews();
        loadCategories();

        // Edit mode?
        if (getIntent().hasExtra("expense_id")) {
            int id = getIntent().getIntExtra("expense_id", -1);
            // You can fetch the expense from DB and fill the fields here
            // For brevity, we just set title
            setTitle("Edit Expense");
        } else {
            setTitle("Add Expense");
        }

        tvDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveExpense());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        etDescription = findViewById(R.id.etDescription);
        etAmount = findViewById(R.id.etAmount);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        tvDate = findViewById(R.id.tvDate);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // default today
        tvDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Calendar.getInstance().getTime()));
    }
    private void loadCategories() {
        // Lấy danh sách danh mục từ CSDL của user hiện tại
        ArrayList<String> categoryList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT categoryname FROM categories WHERE user_id = ? ORDER BY categoryname",
                new String[]{String.valueOf(userId)}
        );

        if (cursor.moveToFirst()) {
            do {
                categoryList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Nếu chưa có danh mục nào → thêm vài gợi ý mặc định
        if (categoryList.isEmpty()) {
            String[] defaults = {"Food", "Transport", "Entertainment", "Shopping", "Rent", "Other"};
            for (String cat : defaults) {
                int catId = dbHelper.addCategoryAndReturnId(userId, cat); // tự động tạo
                if (catId != -1) categoryList.add(cat);
            }
        }

        // Tạo adapter và gán vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            tvDate.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", y, m + 1, d));
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveExpense() {
        String desc = etDescription.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String date = tvDate.getText().toString();

        if (desc.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount = Integer.parseInt(amountStr);

        // For simplicity we use category name only
        // In real app you should get categoryId from DB
        int categoryId = dbHelper.getCategoryIdByName(category, userId);
        if (categoryId == -1) {
            categoryId = dbHelper.addCategoryAndReturnId(userId, category);
        }

        long result;
        if (currentExpense == null) {
            result = dbHelper.addExpense(userId, category, categoryId, amount, date, desc);
        } else {
            result = dbHelper.updateExpense(currentExpense.getId(), category, categoryId, amount, date, desc);
        }

        if (result > 0) {
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Error saving", Toast.LENGTH_SHORT).show();
        }
    }
}