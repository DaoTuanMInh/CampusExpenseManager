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
import java.util.Calendar;
import java.util.Locale;

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

        // SharedPreferences → kiểm tra login
        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isLogin", false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        dbHelper = new DatabaseHelper(this);
        userId = (int) sharedPreferences.getLong("user_id", -1);

        // Lấy danh sách chi tiêu
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

        // Bottom navigation
        findViewById(R.id.ibtHome).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.ibtInfor).setOnClickListener(v -> startActivity(new Intent(this, Infor.class)));
    }

    // Cập nhật danh sách chi tiêu
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
        Calendar cal = Calendar.getInstance();
        String yearMonth = String.format(Locale.getDefault(), "%d-%02d",
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

        String highestWarning = null;

        // Get the list of budgets
        ArrayList<BudgetItem> budgetList = dbHelper.getAllBudgetSettings(userId);

        // Get the latest expense list from the database
        expenseList = dbHelper.getAllExpenses(userId);

        for (BudgetItem budget : budgetList) {
            String budgetMonth = budget.getMonth();
            if (budgetMonth == null || budgetMonth.length() < 7) continue;
            budgetMonth = budgetMonth.substring(0, 7); // yyyy-MM

            if (!budgetMonth.equals(yearMonth)) continue;

            String category = budget.getCategory();
            long budgetLimit = budget.getLimitAmount();
            if (budgetLimit <= 0) continue;

            long totalExpenseInMonth = 0;

            for (ExpenseItem expense : expenseList) {
                String expenseDate = expense.getDate();
                if (expenseDate == null || expenseDate.length() < 7) continue;
                expenseDate = expenseDate.substring(0, 7); // yyyy-MM

                if (expenseDate.equals(yearMonth) &&
                        expense.getCategory().equalsIgnoreCase(category)) {
                    long amount = expense.getAmount();
                    if (amount < 0) amount = 0; // do not count negative spending
                    totalExpenseInMonth += amount;
                }
            }

            String message = null;
            if (totalExpenseInMonth > budgetLimit) {
                message = "🔥 Budget for " + category + " HAS BEEN EXCEEDED! (Spent: "
                        + totalExpenseInMonth + " / " + budgetLimit + ")";
            } else if (totalExpenseInMonth >= budgetLimit * 0.8) {
                message = "⚠️ Budget for " + category + " is ALMOST REACHED! (Spent: "
                        + totalExpenseInMonth + " / " + budgetLimit + ")";
            }

            if (message != null) {
                highestWarning = message; // only show the most recent warning
            }
        }

        if (highestWarning != null) {
            Toast.makeText(this, highestWarning, Toast.LENGTH_LONG).show();
        }
    }




}
