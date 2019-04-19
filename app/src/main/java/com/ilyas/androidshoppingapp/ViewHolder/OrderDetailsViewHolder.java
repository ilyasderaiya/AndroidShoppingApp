package com.ilyas.androidshoppingapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilyas.androidshoppingapp.ItemClickListner;
import com.ilyas.androidshoppingapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtpName, txtpPrice, txtpQty;
    public ImageView imgpProd;
    public ItemClickListner listner;

    public OrderDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        txtpName = (TextView) itemView.findViewById(R.id.txt_prod_name_ordr_details);
        txtpPrice = (TextView) itemView.findViewById(R.id.txt_prod_price_ordr_details);
        txtpQty = (TextView) itemView.findViewById(R.id.txt_prod_qty_ordr_details);
        imgpProd = (ImageView)itemView.findViewById(R.id.prod_image_ordr_details);
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
