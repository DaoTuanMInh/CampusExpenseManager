package com.example.campusexpense_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton ibtHome, ibtInfor, ibtSetting,
            ibtExpenseReports, ibtExpenseOverview,
            ibtExpenseTracking, ibtBudgetSetting, ibtRecurringExpenses;
    TextView txExpense, txBudget;
    SharedPreferences sharedPreferences;
    DatabaseHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        userId = (int) sharedPreferences.getLong("user_id", -1);

        if (!sharedPreferences.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        // EdgeToEdge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        ibtBudgetSetting = findViewById(R.id.ibtBudgetSetting);
        ibtRecurringExpenses = findViewById(R.id.ibtRecurringExpenses);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        ibtSetting = findViewById(R.id.ibtSetting);
        ibtExpenseReports = findViewById(R.id.ibtExpenseReports);
        ibtExpenseOverview = findViewById(R.id.ibtExpenseOverview);
        ibtExpenseTracking = findViewById(R.id.ibtExpenseTracking);
        txExpense = findViewById(R.id.txExpense);
        txBudget = findViewById(R.id.txBudget);

        // Trang Home là trang chính → nút Home mặc định được chọn
        ibtHome.setSelected(true);
        ibtInfor.setSelected(false);

        // Áp dụng ngân sách định kỳ
        dbHelper.applyRecurringToExpenseTracking(userId);

        // Tính và hiển thị tổng chi tiêu và tổng ngân sách cho tháng hiện tại
        Calendar cal = Calendar.getInstance();
        String yearMonth = String.format(Locale.getDefault(), "%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        int totalExpense = dbHelper.getTotalExpenseForMonth(userId, yearMonth);
        int totalBudget = dbHelper.getTotalBudgetForMonth(userId, yearMonth);

        txExpense.setText("Total expenses: " + totalExpense);
        txBudget.setText("Total budget: " + totalBudget);

        // Bấm Home
        ibtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibtHome.setSelected(true);
                ibtInfor.setSelected(false);
            }
        });

        ibtRecurringExpenses.setOnClickListener(v->{
            Intent intent = new Intent(this, RecurringExpenses.class);
            startActivity(intent);
        });
        ibtBudgetSetting.setOnClickListener(v-> {
            Intent intent = new Intent(this, BudgetSetting.class);
            startActivity(intent);
        });
        // Bấm Infor
        ibtInfor.setOnClickListener(v-> {
            Intent intent = new Intent(this, Infor.class);
            startActivity(intent);
        });

        ibtSetting.setOnClickListener(v->{
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
        });

        ibtExpenseReports.setOnClickListener(v->{
            Intent intent = new Intent(this, ExpenseReports.class);
            startActivity(intent);
        });

        ibtExpenseOverview.setOnClickListener(v->{
            Intent intent = new Intent(this, ExpenseOverview.class);
            startActivity(intent);
        });

        ibtExpenseTracking.setOnClickListener(v->{
            Intent intent = new Intent(this, ExpenseTracking.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại tổng khi quay lại màn hình
        dbHelper.applyRecurringToExpenseTracking(userId);
        Calendar cal = Calendar.getInstance();
        String yearMonth = String.format(Locale.getDefault(), "%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        int totalExpense = dbHelper.getTotalExpenseForMonth(userId, yearMonth);
        int totalBudget = dbHelper.getTotalBudgetForMonth(userId, yearMonth);

        txExpense.setText("Total expenses: " + totalExpense);
        txBudget.setText("Total budget: " + totalBudget);
    }
}