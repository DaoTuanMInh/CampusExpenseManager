package com.example.campusexpense_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton ibtHome, ibtInfor, ibtSetting, ibtNotification, ibtExpenseReports, ibtExpenseOverview, ibtExpenseTracking, ibtBudgetSetting, ibtRecurringExpenses;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("isLogin",false)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // EdgeToEdge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibtBudgetSetting = findViewById(R.id.ibtBudgetSetting);
        ibtRecurringExpenses = findViewById(R.id.ibtRecurringExpenses);
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        ibtSetting = findViewById(R.id.ibtSetting);
        ibtNotification = findViewById(R.id.ibtNotification);
        ibtExpenseReports = findViewById(R.id.ibtExpenseReports);
        ibtExpenseOverview = findViewById(R.id.ibtExpenseOverview);
        ibtExpenseTracking = findViewById(R.id.ibtExpenseTracking);

        // Trang Home là trang chính → nút Home mặc định được chọn
        ibtHome.setSelected(true);
        ibtInfor.setSelected(false);

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

        ibtNotification.setOnClickListener(v->{
            Intent intent = new Intent(this, Notification.class);
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
}
