package com.ilyas.androidshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyas.androidshoppingapp.model.Products;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imgProd;
    FirebaseAuth firebaseAuth;
    TextView txtProductName;
    Products p;
    String url="url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        firebaseAuth =FirebaseAuth.getInstance();
        final FirebaseUser user  = firebaseAuth.getCurrentUser();
        imgProd=findViewById(R.id.imgProduct);
        txtProductName=findViewById(R.id.txtProductName);
        FirebaseDatabase mFirebaseInstance= FirebaseDatabase.getInstance();
        final DatabaseReference mRef=mFirebaseInstance.getReference("Products");

//        mRef.child("iphonedemo").setValue(new Products(1,"Iphone X 64GB Black",649.99f,50,"https://e-mac.eu/image/cache/Apple-iPhone-XS-Max-6.5-inch-64GB-Gold-MT522ZD:A_front-500x500.png"));
        mRef.child("iphonedemo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mykey=dataSnapshot.getKey();
                System.out.println("");
                p=dataSnapshot.getValue(Products.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("---error---", "loadProduct:onCancelled", databaseError.toException());
            }
        });
        /*mRef.child("iphonedemo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p=dataSnapshot.getValue(Products.class);
                //mRef.child("demo").setValue(p);
                if(p!=null) {

                    url=p.getImageUrl();
                    System.out.println("Product" + p.getProductName()+"\t"+url);

                }
                else{
                    System.out.println("Null found");
                }
                System.out.println("ProductURL:"+url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("---error---", "loadProduct:onCancelled", databaseError.toException());
            }
        });*/
        if(p==null){
            System.out.println("Product is null");
        }
        System.out.println("Product" + p.getProductName()+"\t"+p.getImageUrl());
//        System.out.println("ProductURL:"+url);
        /*Glide.with(this)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder500x500)
                .error(R.drawable.cart)
                .into(imgProd);
        txtProductName.setText(p.getProductName());*/
    }
}
