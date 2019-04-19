package com.ilyas.androidshoppingapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyas.androidshoppingapp.ItemClickListner;
import com.ilyas.androidshoppingapp.R;


public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrder;
    public TextView txtOrderNo;
    private ItemClickListner itemClickListner;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrder=itemView.findViewById(R.id.txtOrder);
        txtOrderNo=itemView.findViewById(R.id.txtOrderNo);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
