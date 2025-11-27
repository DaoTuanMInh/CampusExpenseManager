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
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

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

public class BudgetSetting extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    ArrayList<BudgetItem> list;
    BudgetAdapter adapter;

    ImageButton ibtHome, ibtInfor;
    DatabaseHelper databaseHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_setting);

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

        // Get userId from SharedPreferences
        SharedPreferences sp = getSharedPreferences("AppData", MODE_PRIVATE);
        userId = (int) sp.getLong("user_id", -1L);
        if(userId == -1){
            Toast.makeText(this, "User is not logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load budget list from DB
        list = databaseHelper.getAllBudgetSettings(userId);

        adapter = new BudgetAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            BudgetItem item = list.get(position);
            showAddEditDialog(item);
        });

        fabAdd.setOnClickListener(v -> showAddEditDialog(null));

        ibtHome.setOnClickListener(v -> {
            Intent intent = new Intent(BudgetSetting.this, MainActivity.class);
            startActivity(intent);
        });

        ibtInfor.setOnClickListener(v -> {
            Intent intent = new Intent(BudgetSetting.this, Infor.class);
            startActivity(intent);
        });
    }

    private void showAddEditDialog(BudgetItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        builder.setView(dialogView);

        EditText edtCategory = dialogView.findViewById(R.id.edtEat);  // Renamed to edtCategory
        EditText edtLimit = dialogView.findViewById(R.id.edtMoney);
        EditText edtDate = dialogView.findViewById(R.id.edtDate);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());  // Changed to yyyy-MM

        if (item != null) {
            builder.setTitle("Edit Budget");
            edtCategory.setText(item.getCategory());
            edtLimit.setText(String.valueOf(item.getLimitAmount()));
            edtDate.setText(item.getMonth());
        } else {
            builder.setTitle("Add Budget");
        }

        edtDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    BudgetSetting.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        edtDate.setText(sdf.format(calendar.getTime()));  // Save as yyyy-MM
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        builder.setPositiveButton(item == null ? "Add" : "Save", (dialog, which) -> {
            String category = edtCategory.getText().toString().trim();
            String limitStr = edtLimit.getText().toString().trim();
            String month = edtDate.getText().toString().trim();

            if (category.isEmpty() || limitStr.isEmpty() || month.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int limitAmount;
            try {
                limitAmount = Integer.parseInt(limitStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            // === TỰ ĐỘNG TẠO DANH MỤC NẾU CHƯA TỒN TẠI ===
            int categoryId = databaseHelper.getCategoryIdByName(category, userId);

// Nếu chưa có → tự động tạo mới
            if (categoryId == -1) {
                categoryId = databaseHelper.addCategoryAndReturnId(userId, category);
                if (categoryId == -1) {
                    Toast.makeText(this, "Lỗi khi tạo danh mục mới!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
// === KẾT THÚC TỰ ĐỘNG TẠO ===

            if (item == null) {
                databaseHelper.addBudgetSetting(userId, category, categoryId, limitAmount, month);
                list.add(new BudgetItem(0, category, limitAmount, month));
                adapter.notifyItemInserted(list.size() - 1);
            } else {
                databaseHelper.updateBudgetSetting(item.getId(), category, categoryId, limitAmount, month);
                item.setCategory(category);
                item.setLimitAmount(limitAmount);
                item.setMonth(month);
                adapter.notifyItemChanged(list.indexOf(item));
            }
        });

        if (item != null) {
            builder.setNeutralButton("Delete", (dialog, which) -> {
                databaseHelper.deleteBudgetSetting(item.getId());
                int pos = list.indexOf(item);
                list.remove(item);
                adapter.notifyItemRemoved(pos);
            });
        }

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // ===============================
    // Adapter
    // ===============================
    public static class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
        private ArrayList<BudgetItem> list;
        private OnItemClickListener listener;

        interface OnItemClickListener { void onItemClick(int position); }
        public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }

        public BudgetAdapter(ArrayList<BudgetItem> list) { this.list = list; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewbudget, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            BudgetItem item = list.get(position);
            holder.tvCategory.setText(item.getCategory());
            holder.tvLimit.setText("Limit: " + item.getLimitAmount());
            holder.tvMonth.setText("Month: " + item.getMonth());
        }

        @Override
        public int getItemCount() { return list.size(); }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvCategory, tvLimit, tvMonth;
            public ViewHolder(View itemView) {
                super(itemView);
                tvCategory = itemView.findViewById(R.id.tvSpendingsection);
                tvLimit = itemView.findViewById(R.id.tvMoney);
                tvMonth = itemView.findViewById(R.id.tvDate);

                itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) listener.onItemClick(position);
                    }
                });
            }
        }
    }
}