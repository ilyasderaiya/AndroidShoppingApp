package com.ilyas.androidshoppingapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyas.androidshoppingapp.ItemClickListner;
import com.ilyas.androidshoppingapp.R;


public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderPlaced, txtTotal;
    public TextView txtOrderNo;
    private ItemClickListner itemClickListner;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderPlaced=itemView.findViewById(R.id.txtOrderPlaced);
        txtOrderNo=itemView.findViewById(R.id.txtOrderNo);
        txtTotal=itemView.findViewById(R.id.txtTotal);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
