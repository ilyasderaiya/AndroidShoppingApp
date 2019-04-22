package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.model.Products;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductInfoAvtivity extends AppCompatActivity {

    Button addTocartBtn;
    ImageView productImage;
    ElegantNumberButton numberButton;
    TextView productName, productPrice;

    String pImageUrl, pPrice;

    FirebaseAuth mAuth;
    FirebaseUser fuser;


    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info_avtivity);

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();

        key = getIntent().getStringExtra("key");
        System.out.println(key);

        addTocartBtn = (Button) findViewById(R.id.btn_add_to_cart);
        productImage = (ImageView) findViewById(R.id.product_image_info);
        numberButton = (ElegantNumberButton) findViewById(R.id.quantity_counter);
        productName = (TextView) findViewById(R.id.product_name_info);
        productPrice = (TextView) findViewById(R.id.product_price_info);
        productName.setText(key);
        getProductDetails(key);


        addTocartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    private void addToCart() {
        String saveCurrDate, saveCurrTime;
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM, yyyy");
        saveCurrDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrTime = currentTime.format(callForDate.getTime());

        DatabaseReference cartItemRef = FirebaseDatabase.getInstance().getReference().child("Cart Item");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("key",key);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("pprice",pPrice);
        cartMap.put("date",saveCurrDate);
        cartMap.put("time",saveCurrTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("image", pImageUrl);

        cartItemRef.child("User view").child(fuser.getUid()).child("products")
                .child(key).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductInfoAvtivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();

//                            startActivity(new Intent(ProductInfoAvtivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });


    }

    private void getProductDetails(String key) {
        DatabaseReference prodRef = FirebaseDatabase.getInstance().getReference().child("Products");

        prodRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    if (products != null ) {
                        productName.setText(products.getProductName());
                        pPrice = products.getProductPrice();
                        productPrice.setText("Price: $" + pPrice);
                        pImageUrl = products.getImageUrl();
                        Picasso.get().load(pImageUrl).into(productImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
