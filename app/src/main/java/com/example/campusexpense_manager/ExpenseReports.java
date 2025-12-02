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

public class ExpenseReports extends AppCompatActivity {

    ImageButton ibtHome, ibtInfor;
    TextView tvTotalRemaining, tvLastMonth, tvThisMonth;
    TableLayout tbReportDetails;
    int userId;
    SharedPreferences sharedPreferences;
    DatabaseHelper dbHelper;
    int currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);

        userId = (int) sharedPreferences.getLong("user_id", -1);

        if (!sharedPreferences.getBoolean("isLogin", false)) {
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

        tvTotalRemaining = findViewById(R.id.tvTotalRemaining);
        tvLastMonth = findViewById(R.id.tvLastMonth);
        tvThisMonth = findViewById(R.id.tvThisMonth);
        tbReportDetails = findViewById(R.id.tbReportDetails);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);

        dbHelper = new DatabaseHelper(this);

        // Lấy tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH) + 1; // tháng từ 1-12

        dbHelper.applyRecurringToExpenseTracking(userId);
        loadMonth(currentMonth);
        // Hiển thị mặc định tháng này
        loadMonth(currentMonth);

        // Click sự kiện cho LastMonth
        tvLastMonth.setOnClickListener(v -> {
            int lastMonth = currentMonth - 1;
            if (lastMonth <= 0) lastMonth = 12;
            loadMonth(lastMonth);
        });

        // Click sự kiện cho ThisMonth
        tvThisMonth.setOnClickListener(v -> loadMonth(currentMonth));

        ibtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseReports.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ibtInfor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseReports.this, Infor.class);
                startActivity(intent);
            }
        });
    }
    private void loadMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH) + 1;

        // Nếu tháng xem < tháng hiện tại, giảm 1 năm
        if (month > currMonth) {
            year--;  // Ví dụ xem tháng 12 hiện tại là tháng 1, thì giảm năm
        }

        String yearMonth = year + "-" + String.format("%02d", month);

        // Lấy tổng Remaining Budget
        int remaining = dbHelper.getTotalRemaining(userId, yearMonth);
        tvTotalRemaining.setText("Remaining Budget: " + remaining);

        tbReportDetails.removeViews(1, tbReportDetails.getChildCount() - 1);

        Cursor cursor = dbHelper.getExpenseDetailsByYearMonth(userId, yearMonth);

        if (cursor == null || cursor.getCount() == 0) {
            // Không có dữ liệu -> hiển thị row thông báo
            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.expense_row, tbReportDetails, false);
            TextView tvCategory = row.findViewById(R.id.tvCategoryRow);
            TextView tvAmount = row.findViewById(R.id.tvAmountRow);
            tvCategory.setText("No data");
            tvAmount.setText("-");
            tbReportDetails.addView(row);
        } else if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                int amountUsed = cursor.getInt(cursor.getColumnIndexOrThrow("amount_used"));

                TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.expense_row, tbReportDetails, false);
                TextView tvCategory = row.findViewById(R.id.tvCategoryRow);
                TextView tvAmount = row.findViewById(R.id.tvAmountRow);

                tvCategory.setText(category);
                tvAmount.setText(String.valueOf(amountUsed));

                tbReportDetails.addView(row);
            } while (cursor.moveToNext());
        }
        if (cursor != null) cursor.close();
    }


}