package com.example.campusexpense_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class ExpenseTracking extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

    RecyclerView recyclerView;
    ExpenseAdapter adapter;
    ArrayList<ExpenseItem> expenseList;
    DatabaseHelper dbHelper;
    int userId;
    SharedPreferences sharedPreferences;
    ActivityResultLauncher<Intent> addEditLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracking);

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isLogin",false)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        dbHelper = new DatabaseHelper(this);
        userId = (int) sharedPreferences.getLong("user_id", -1);
        expenseList = dbHelper.getAllExpenses(userId);

        recyclerView = findViewById(R.id.recyclerViewTracking);
        FloatingActionButton fab = findViewById(R.id.fabAddTracking);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExpenseAdapter(expenseList, this);
        recyclerView.setAdapter(adapter);

        // FAB → Add new expense
        fab.setOnClickListener(v -> {
            Intent i = new Intent(ExpenseTracking.this, AddEditExpenseActivity.class);
            i.putExtra("user_id", userId);
            addEditLauncher.launch(i);
        });

        // Result from Add/Edit screen
        addEditLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        refreshList();
                    }
                });

        // Bottom navigation (you already have)
        findViewById(R.id.ibtHome).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.ibtInfor).setOnClickListener(v -> startActivity(new Intent(this, Infor.class)));
    }

    private void refreshList() {
        expenseList = dbHelper.getAllExpenses(userId);
        adapter.updateList(expenseList);
        checkBudgetWarning();
    }

    @Override
    public void onEdit(ExpenseItem item) {
        Intent i = new Intent(this, AddEditExpenseActivity.class);
        i.putExtra("user_id", userId);
        i.putExtra("expense_id", item.getId());
        // you can also pass other fields if you want pre-filled form
        addEditLauncher.launch(i);
    }

    @Override
    public void onDelete(ExpenseItem item) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete expense")
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", (d, w) -> {
                    dbHelper.deleteExpense(item.getId());
                    refreshList();
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void checkBudgetWarning() {
        // Lấy danh sách ngân sách hiện có
        ArrayList<BudgetItem> budgetList = dbHelper.getAllBudgetSettings(userId);

        // Duyệt qua từng category trong ngân sách
        for (BudgetItem budget : budgetList) {
            String category = budget.getCategory();
            // Dùng long để tránh tràn số nếu ngân sách lớn
            long budgetLimit = budget.getLimitAmount();

            // Tính tổng chi tiêu trong category này
            long totalExpense = 0; // Dùng long để tránh tràn số
            for (ExpenseItem expense : expenseList) {
                if (expense.getCategory().equalsIgnoreCase(category)) {
                    totalExpense += expense.getAmount();
                }
            }

            if (budgetLimit <= 0) {
                continue;
            }

            // Tính % sử dụng
            float percentFloat = (totalExpense * 100.0f) / budgetLimit;

            // Làm tròn thành số nguyên gần nhất để hiển thị
            int percent = Math.round(percentFloat);

            // --- BỎ QUA PHẦN HIỂN THỊ THÔNG BÁO TOAST ---
            // if (percent >= 100) {
            //     Toast.makeText(this, "⚠️ Bạn đã vượt ngân sách " + category + " " + percent + "%!", Toast.LENGTH_LONG).show();
            // } else if (percent >= 80) {
                Toast.makeText(this, "⚠️ Ngân sách " + category + " sắp hết (" + percent + "%)!", Toast.LENGTH_SHORT).show();
            // }
        }

    }}