package com.ilyas.androidshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    Button btnlgot, btnAddProd;
    EditText prodId, prodName, prodPrice, prodQty;
    ImageView prodImg;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnlgot =  (Button) findViewById(R.id.btnlogout_admin);
        btnAddProd = (Button) findViewById(R.id.btnAddProd);
        prodId = (EditText) findViewById(R.id.edtPrdId);
        prodName = (EditText) findViewById(R.id.edtProdName);
        prodPrice = (EditText) findViewById(R.id.edtProdPrice);
        prodQty = (EditText) findViewById(R.id.edtProdQuantity);
        prodImg = (ImageView)findViewById(R.id.prd_img);


        Toast.makeText(this, "Welcome Admin...", Toast.LENGTH_SHORT).show();


        mAuth = FirebaseAuth.getInstance();

        btnlgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AdminActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
