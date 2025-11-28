package com.example.campusexpense_manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecurringExpenses extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    ArrayList<RecurringItem> list;
    RecurringAdapter adapter;

    ImageButton ibtHome, ibtInfor;
    DatabaseHelper databaseHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_expenses);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences sp = getSharedPreferences("AppData", MODE_PRIVATE);
        userId = (int) sp.getLong("user_id", -1L);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadData();

        ibtHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        ibtInfor.setOnClickListener(v -> startActivity(new Intent(this, Infor.class)));
        fabAdd.setOnClickListener(v -> showAddEditDialog(null));
    }

    private void loadData() {
        // Đổi tên method cho đúng nghĩa (nhưng vẫn dùng lại bảng cũ)
        list = databaseHelper.getAllRecurringExpenses(userId);  // bạn sẽ thêm method này trong DatabaseHelper
        adapter = new RecurringAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> showAddEditDialog(list.get(position)));
    }

    private void showAddEditDialog(RecurringItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_recurring, null);
        builder.setView(dialogView);

        EditText edtCategory = dialogView.findViewById(R.id.edtCategory);
        EditText edtAmount = dialogView.findViewById(R.id.edtAmount);
        EditText edtStartMonth = dialogView.findViewById(R.id.edtStartMonth);
        EditText edtEndMonth = dialogView.findViewById(R.id.edtEndMonth);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (item != null) {
            builder.setTitle("Periodic budget revision");
            edtCategory.setText(item.getCategory());
            edtAmount.setText(String.valueOf(item.getAmount()));
            edtStartMonth.setText(item.getStartMonth());
            if (item.getEndMonth() != null) edtEndMonth.setText(item.getEndMonth());
        } else {
            builder.setTitle("Add recurring budget");
        }

        edtStartMonth.setOnClickListener(v -> new DatePickerDialog(this,
                (view, year, month, day) -> {
                    calendar.set(year, month, 1);
                    edtStartMonth.setText(sdf.format(calendar.getTime()));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1).show());

        edtEndMonth.setOnClickListener(v -> new DatePickerDialog(this,
                (view, year, month, day) -> {
                    calendar.set(year, month, 1);
                    edtEndMonth.setText(sdf.format(calendar.getTime()));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1).show());

        builder.setPositiveButton(item == null ? "Add" : "Save", (dialog, which) -> {
            String category = edtCategory.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();
            String startMonth = edtStartMonth.getText().toString().trim();
            String endMonth = edtEndMonth.getText().toString().trim();

            if (category.isEmpty() || amountStr.isEmpty() || startMonth.isEmpty()) {
                Toast.makeText(this, "Please fill in completely", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            int categoryId = databaseHelper.getCategoryIdByName(category, userId);
            if (categoryId == -1) {
                categoryId = databaseHelper.addCategoryAndReturnId(userId, category);
                if (categoryId == -1) {
                    Toast.makeText(this, "Unable to create category", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (item == null) {
                // THÊM MỚI → chỉ lưu vào recurring_expenses (giờ là ngân sách định kỳ)
                databaseHelper.addRecurringExpense(userId, category, categoryId, amount, startMonth,
                        endMonth.isEmpty() ? null : endMonth);
                Toast.makeText(this, "Added recurring budget!", Toast.LENGTH_SHORT).show();
            } else {
                // CHỈNH SỬA → cập nhật bản ghi
                databaseHelper.updateRecurringExpense(item.getId(), category, categoryId, amount, startMonth,
                        endMonth.isEmpty() ? null : endMonth);
                item.setCategory(category);
                item.setAmount(amount);
                item.setStartMonth(startMonth);
                item.setEndMonth(endMonth.isEmpty() ? null : endMonth);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
            }

            databaseHelper.applyRecurringToExpenseTracking(userId);
            loadData();
        });

        if (item != null) {
            builder.setNeutralButton("Delete", (dialog, which) -> {
                databaseHelper.deleteRecurringExpense(item.getId());
                list.remove(item);
                adapter.notifyDataSetChanged();
                databaseHelper.applyRecurringToExpenseTracking(userId); // cập nhật lại limit các tháng
                Toast.makeText(this, "Deleted recurring budget", Toast.LENGTH_SHORT).show();
            });
        }

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseHelper.applyRecurringToExpenseTracking(userId);
        loadData();
    }
}