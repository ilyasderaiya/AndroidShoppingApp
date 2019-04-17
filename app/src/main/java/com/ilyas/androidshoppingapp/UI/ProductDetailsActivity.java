package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.model.Customer;
import com.ilyas.androidshoppingapp.model.Products;
import com.ilyas.androidshoppingapp.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imgProd;
    FirebaseAuth firebaseAuth;
    TextView txtProductName;
    Button btnAdd;
    Products products;
    String url="url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        firebaseAuth =FirebaseAuth.getInstance();
        final FirebaseUser user  = firebaseAuth.getCurrentUser();
        imgProd=findViewById(R.id.imgProduct);
        txtProductName=findViewById(R.id.txtProductName);
        btnAdd=findViewById(R.id.btnAddCart);
        final FirebaseDatabase mFirebaseInstance= FirebaseDatabase.getInstance();
//        final DatabaseReference mRef=mFirebaseInstance.getReference("Products");

//        mRef.child("iphonedemo").setValue(new Products(1,"Iphone X 64GB Black",649.99f,50,"https://e-mac.eu/image/cache/Apple-iPhone-XS-Max-6.5-inch-64GB-Gold-MT522ZD:A_front-500x500.png"));
       /* mRef.child("iphonedemo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String mykey=dataSnapshot.getKey();
                //System.out.println("");
                Products p=dataSnapshot.getValue(Products.class);
                products=loadContent(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("---error---", "loadProduct:onCancelled", databaseError.toException());
            }
        });*/


        /*btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCart sc = new ShoppingCart();
//                sc.addCartItem(products.getProductID(),products.getQuantity(),products.getProductName(),products.getImageUrl(),products.getProductPrice());
                Customer c=Customer.getInstance();
                ArrayList<ShoppingCart> sclist=(ArrayList<ShoppingCart>) c.getArrCart();
                sclist.add(sc);
                c.setArrCart(sclist);
                mFirebaseInstance.getReference("Customer").child(user.getUid()).setValue(c);
                txtProductName.setText("Add to cart for "+products.getProductName());
            }
        });*/
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

//        System.out.println("ProductURL:"+url);
        /*Glide.with(this)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder500x500)
                .error(R.drawable.cart)
                .into(imgProd);
        txtProductName.setText(p.getProductName());*/
    }

    /*private Products loadContent(Products pObj) {
        if(pObj==null){
            System.out.println("Product is null");
        }
        else{
            System.out.println("Product" + pObj.getProductName()+"\t"+pObj.getImageUrl());
            Glide.with(this)
                    .load(pObj.getImageUrl())
                    .fitCenter()
                    .placeholder(R.drawable.placeholder500x500)
                    .error(R.drawable.cart)
                    .into(imgProd);
        }
        return pObj;
    }*/
}
