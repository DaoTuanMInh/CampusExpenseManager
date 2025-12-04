package com.example.campusexpense_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    ArrayList<ExpenseItem> expenseList;
    OnExpenseClickListener listener;

    public interface OnExpenseClickListener {
        void onEdit(ExpenseItem item);
        void onDelete(ExpenseItem item);
    }

    public ExpenseAdapter(ArrayList<ExpenseItem> list, OnExpenseClickListener listener) {
        this.expenseList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseItem item = expenseList.get(position);

        holder.tvCategory.setText(item.getCategory());
        holder.tvDate.setText(item.getDate());
        holder.tvDescription.setText(item.getDescription());

        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvAmount.setText(fmt.format(item.getAmount()));

        holder.itemView.setOnClickListener(v -> listener.onEdit(item));

        // Long click → Delete (có xác nhận)
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDelete(item);
            return true; // đã xử lý long click
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvAmount, tvDate, tvDescription;

        ViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }

    public void updateList(ArrayList<ExpenseItem> newList) {
        expenseList = newList;
        notifyDataSetChanged();
    }
}