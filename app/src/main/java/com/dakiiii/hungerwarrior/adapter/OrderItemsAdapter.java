package com.dakiiii.hungerwarrior.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder> {

    List<OrderItem> eOrderItems = new ArrayList<>();

    @NonNull
    @Override
    public OrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_row, parent, false);
        return new OrderItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsViewHolder holder, int position) {
        OrderItem orderItem = eOrderItems.get(position);
        holder.eTextViewFoodName.setText(orderItem.getFoodName());
        holder.eTextViewFoodQty.setText(String.valueOf(orderItem.getQuantity()));
        holder.eTextViewOrderItemStatus.setText(orderItem.getStatus());
        holder.eTextViewOrderId.setText(String.valueOf(orderItem.getId()));

    }

    @Override
    public int getItemCount() {
        if (eOrderItems != null) {
            return eOrderItems.size();
        }
        return 0;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        eOrderItems = orderItems;
        notifyDataSetChanged();
    }

    public class OrderItemsViewHolder extends RecyclerView.ViewHolder {
        TextView eTextViewFoodName;
        TextView eTextViewFoodQty;
        TextView eTextViewOrderItemStatus;
        TextView eTextViewOrderId;

        public OrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            eTextViewOrderId = itemView.findViewById(R.id.textView_orderItem_order_id);
            eTextViewFoodName = itemView.findViewById(R.id.textView_orderItem_name);
            eTextViewOrderItemStatus = itemView.findViewById(R.id.textView_orderItem_status);
            eTextViewFoodQty = itemView.findViewById(R.id.textView_orderItem_quantity);

        }
    }
}
