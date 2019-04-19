package com.ilyas.androidshoppingapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilyas.androidshoppingapp.ItemClickListner;
import com.ilyas.androidshoppingapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtPName, txtPPrice;
    public ImageView pImageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        pImageView = (ImageView)itemView.findViewById(R.id.prod_image_ordr_details);
        txtPName = (TextView) itemView.findViewById(R.id.txt_prod_name_ordr_details);
        txtPPrice = (TextView) itemView.findViewById(R.id.txtProdPrice);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false)   ;
    }
}
