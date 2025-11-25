package com.example.campusexpense_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExpenseReports extends AppCompatActivity {

    ImageButton ibtHome, ibtInfor;
    TextView tvRemainingBudget;
    TableLayout tbExpenseReports;
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
        setContentView(R.layout.activity_expense_reports);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userId = (int) sharedPreferences.getLong("user_id", -1);
        tvRemainingBudget = findViewById(R.id.tvRemainingBudget);
        tbExpenseReports = findViewById(R.id.tbExpenseReports);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        dbHelper = new DatabaseHelper(this);
        loadExpenses(userId);

        ibtHome.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseReports.this, MainActivity.class);
            startActivity(intent);
        });

        ibtInfor.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseReports.this, Infor.class);
            startActivity(intent);
        });
    }
    private void loadExpenses(int userId) {
        Cursor cursor = dbHelper.getAllExpenseReports(userId);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                int amount = cursor.getInt(cursor.getColumnIndexOrThrow("total_amount"));
                int remaining = cursor.getInt(cursor.getColumnIndexOrThrow("remaining_budget"));

                // tạo row
                TableRow row = (TableRow) getLayoutInflater()
                        .inflate(R.layout.table_row_expense, null, false);

                TextView tvCategory = row.findViewById(R.id.tvCategoryRow);
                TextView tvAmount = row.findViewById(R.id.tvAmountRow);

                tvCategory.setText(category);
                tvAmount.setText(String.valueOf(amount));

                // Thêm click listener cho row
                int finalRemaining = remaining; // để sử dụng trong lambda
                row.setOnClickListener(v -> {
                    tvRemainingBudget.setText("Remaining Budget: " + finalRemaining);
                });

                tbExpenseReports.addView(row);

            } while (cursor.moveToNext());

            cursor.close();
        }
    }



}
