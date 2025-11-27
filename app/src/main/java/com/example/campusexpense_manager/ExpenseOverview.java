package com.example.campusexpense_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;


public class ExpenseOverview extends AppCompatActivity {
    ImageButton ibtHome, ibtInfor;
    TextView tvRemainingBudget;
    TableLayout tbExpenseOverview;
    DatabaseHelper dbHelper;
    int userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("isLogin",false)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userId = (int) sharedPreferences.getLong("user_id", -1);
        tvRemainingBudget = findViewById(R.id.tvRemainingBudget);
        tbExpenseOverview = findViewById(R.id.tbExpenseOverview);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        dbHelper = new DatabaseHelper(this);

        // Tùy chọn: Xóa chi tiêu cũ nếu test
        // dbHelper.clearAllExpenses(); // Bỏ comment nếu muốn reset

        // Load tháng hiện tại
        Calendar cal = Calendar.getInstance();
        String yearMonth = cal.get(Calendar.YEAR) + "-" + String.format("%02d", cal.get(Calendar.MONTH) + 1);
        loadExpenses(userId, yearMonth);

        ibtHome.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseOverview.this, MainActivity.class);
            startActivity(intent);
        });

        ibtInfor.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseOverview.this, Infor.class);
            startActivity(intent);
        });
    }

    private void loadExpenses(int userId, String yearMonth) {
        // Cập nhật tổng Remaining Budget ban đầu
        int totalRemaining = dbHelper.getTotalRemaining(userId, yearMonth);
        tvRemainingBudget.setText("Remaining Budget: " + totalRemaining);

        // Xóa dữ liệu cũ (giữ header nếu có)
        while (tbExpenseOverview.getChildCount() > 1) {
            tbExpenseOverview.removeViewAt(1);
        }

        Cursor cursor = dbHelper.getExpenseOverviewByYearMonth(userId, yearMonth);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                int amountUsed = cursor.getInt(cursor.getColumnIndexOrThrow("amount_used"));
                int budgetLimit = cursor.getInt(cursor.getColumnIndexOrThrow("budget_limit"));
                int remaining = cursor.getInt(cursor.getColumnIndexOrThrow("remaining"));

                TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.table_row_expense, null, false);

                TextView tvCategory = row.findViewById(R.id.tvCategoryRow);
                TextView tvAmount = row.findViewById(R.id.tvAmountRow);
                TextView tvLimit = row.findViewById(R.id.tvLimitRow);      // Mới thêm
                TextView tvRemaining = row.findViewById(R.id.tvRemainingRow); // Mới thêm

                tvCategory.setText(category);
                tvAmount.setText(String.valueOf(amountUsed));
                tvLimit.setText(String.valueOf(budgetLimit));
                tvRemaining.setText(String.valueOf(remaining));

                // Click row để update tvRemainingBudget với remaining của category
                row.setOnClickListener(v -> tvRemainingBudget.setText("Remaining for " + category + ": " + remaining));

                tbExpenseOverview.addView(row);

            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}