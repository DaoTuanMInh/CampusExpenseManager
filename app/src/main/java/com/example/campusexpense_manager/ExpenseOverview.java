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

        userId = (int) sharedPreferences.getLong("user_id", -1);

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


        tvRemainingBudget = findViewById(R.id.tvRemainingBudget);
        tbExpenseOverview = findViewById(R.id.tbExpenseOverview);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        dbHelper = new DatabaseHelper(this);

        dbHelper.applyRecurringToExpenseTracking(userId);

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
                TextView tvLimit = row.findViewById(R.id.tvLimitRow);
                TextView tvRemaining = row.findViewById(R.id.tvRemainingRow);

                tvCategory.setText(category);
                tvAmount.setText(String.valueOf(amountUsed));
                tvLimit.setText(String.valueOf(budgetLimit));
                tvRemaining.setText(String.valueOf(remaining));

                // Khi click vào row mới cập nhật Remaining Budget
                row.setOnClickListener(v ->
                        tvRemainingBudget.setText("Remaining for " + category + ": " + remaining)
                );

                tbExpenseOverview.addView(row);

            } while (cursor.moveToNext());

            cursor.close();
        }
    }

}