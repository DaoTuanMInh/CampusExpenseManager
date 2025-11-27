package com.example.campusexpense_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecurringAdapter extends RecyclerView.Adapter<RecurringAdapter.ViewHolder> {

    private ArrayList<RecurringItem> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecurringAdapter(ArrayList<RecurringItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recurring, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecurringItem item = list.get(position);
        holder.tvCategory.setText(item.getCategory());
        holder.tvAmount.setText(item.getAmount() + " đ / month");
        holder.tvStartMonth.setText("From: " + item.getStartMonth());
        holder.tvEndMonth.setText(
                item.getEndMonth() == null || item.getEndMonth().isEmpty()
                        ? "end date"
                        : item.getEndMonth()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvAmount, tvStartMonth, tvEndMonth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStartMonth = itemView.findViewById(R.id.tvStartMonth);
            tvEndMonth = itemView.findViewById(R.id.tvEndMonth);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
