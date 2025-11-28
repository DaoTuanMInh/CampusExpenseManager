package com.example.campusexpense_manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

        // Edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        ibtHome = findViewById(R.id.ibtHome);
        ibtInfor = findViewById(R.id.ibtInfor);
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        databaseHelper = new DatabaseHelper(this);

        // Lấy userId từ SharedPreferences
        SharedPreferences sp = getSharedPreferences("AppData", MODE_PRIVATE);
        userId = (int) sp.getLong("user_id", -1L);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load danh sách chi phí định kỳ
        loadRecurringExpenses();

        // Nút Home & Info
        ibtHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        ibtInfor.setOnClickListener(v -> startActivity(new Intent(this, Infor.class)));

        // Nút thêm mới
        fabAdd.setOnClickListener(v -> showAddEditDialog(null));
    }

    private void loadRecurringExpenses() {
        list = databaseHelper.getAllRecurringExpenses(userId);
        adapter = new RecurringAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            RecurringItem item = list.get(position);
            showAddEditDialog(item);
        });
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

        if (item != null) {
            builder.setTitle("Edit Recurring Expense");
            edtCategory.setText(item.getCategory());
            edtAmount.setText(String.valueOf(item.getAmount()));
            edtStartMonth.setText(item.getStartMonth());
            edtEndMonth.setText(item.getEndMonth() != null ? item.getEndMonth() : "");
        } else {
            builder.setTitle("Add New Recurring Expense");
        }

        // Date picker cho Start Month
        edtStartMonth.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, day) -> {
                calendar.set(year, month, 1);
                edtStartMonth.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1).show();
        });

        // Date picker cho End Month (tùy chọn)
        edtEndMonth.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, day) -> {
                calendar.set(year, month, 1);
                edtEndMonth.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1).show();
        });

        builder.setPositiveButton(item == null ? "Add" : "Save", (dialog, which) -> {
            String category = edtCategory.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();
            String startMonth = edtStartMonth.getText().toString().trim();
            String endMonth = edtEndMonth.getText().toString().trim();

            if (category.isEmpty() || amountStr.isEmpty() || startMonth.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tự động tạo danh mục nếu chưa tồn tại
            int categoryId = databaseHelper.getCategoryIdByName(category, userId);
            if (categoryId == -1) {
                categoryId = databaseHelper.addCategoryAndReturnId(userId, category);
                if (categoryId == -1) {
                    Toast.makeText(this, "Failed to create category", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (item == null) {
                // Thêm mới
                long id = databaseHelper.addRecurringExpense(
                        userId, category, categoryId, amount, startMonth,
                        endMonth.isEmpty() ? null : endMonth
                );
                list.add(new RecurringItem((int) id, category, amount, startMonth, endMonth.isEmpty() ? null : endMonth));
                adapter.notifyItemInserted(list.size() - 1);
                Toast.makeText(this, "Added successfully!", Toast.LENGTH_SHORT).show();

                // QUAN TRỌNG: Áp dụng ngay recurring mới này
                databaseHelper.applyAllRecurringExpenses(userId);
            } else {
                // Cập nhật
                databaseHelper.updateRecurringExpense(item.getId(), category, categoryId, amount, startMonth,
                        endMonth.isEmpty() ? null : endMonth);
                item.setCategory(category);
                item.setAmount(amount);
                item.setStartMonth(startMonth);
                item.setEndMonth(endMonth.isEmpty() ? null : endMonth);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show();

                // Cập nhật → cần áp dụng lại toàn bộ lại để xóa cái cũ, thêm cái mới
                databaseHelper.applyAllRecurringExpenses(userId);
            }
        });

        if (item != null) {
            builder.setNeutralButton("Delete", (dialog, which) -> {
                databaseHelper.deleteRecurringExpense(item.getId());

                // Xóa các expense có description chứa "Recurring #itemId"
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.delete("expense_tracking",
                        "user_id = AND description = ?",
                        new String[]{String.valueOf(userId), "Recurring #" + item.getId()});

                int pos = list.indexOf(item);
                list.remove(pos);
                adapter.notifyItemRemoved(pos);
                Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();

                // Áp dụng lại toàn bộ (để đảm bảo sạch)
                databaseHelper.applyAllRecurringExpenses(userId);
            });
        }

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Khi quay lại màn hình này → reload dữ liệu (nếu có thay đổi từ nơi khác)
    @Override
    protected void onResume() {
        super.onResume();
        databaseHelper.applyAllRecurringExpenses(userId); // Đảm bảo luôn mới nhất
        loadRecurringExpenses();
    }
}