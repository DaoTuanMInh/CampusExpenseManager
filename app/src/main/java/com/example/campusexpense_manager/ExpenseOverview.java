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
    TextView tvTotalRemaining, tvLastMonth, tvThisMonth;
    TableLayout tbOverviewDetails;
    int userId;
    SharedPreferences sharedPreferences;
    DatabaseHelper dbHelper;
    int currentMonth;

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
        tvTotalRemaining = findViewById(R.id.tvTotalRemaining);
        tvLastMonth = findViewById(R.id.tvLastMonth);
        tvThisMonth = findViewById(R.id.tvThisMonth);
        tbOverviewDetails = findViewById(R.id.tbOverviewDetails);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);

        dbHelper = new DatabaseHelper(this);

        // Lấy tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH) + 1; // tháng từ 1-12

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
                Intent intent = new Intent(ExpenseOverview.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ibtInfor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseOverview.this, Infor.class);
                startActivity(intent);
            }
        });
    }
    private void loadMonth(int month) {
        int remaining = dbHelper.getTotalRemaining(userId, month);
        tvTotalRemaining.setText("Remaining Budget: " + remaining);

        tbOverviewDetails.removeViews(1, tbOverviewDetails.getChildCount() - 1);

        Cursor cursor = dbHelper.getExpenseDetailsByMonth(userId, month);
        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            int amountUsed = cursor.getInt(cursor.getColumnIndexOrThrow("amount_used"));

            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.expense_row, tbOverviewDetails, false);

            TextView tvCategory = row.findViewById(R.id.tvCategoryRow);
            TextView tvAmount = row.findViewById(R.id.tvAmountRow);

            tvCategory.setText(category);
            tvAmount.setText(String.valueOf(amountUsed));

            tbOverviewDetails.addView(row);
        }
        cursor.close();
    }

}