package com.ilyas.androidshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyas.androidshoppingapp.model.Customer;
import com.ilyas.androidshoppingapp.model.Products;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView txtUser;
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser =  (TextView) findViewById(R.id.txtDetails);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        firebaseAuth =FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };

        final FirebaseUser user  = firebaseAuth.getCurrentUser();
        txtUser.setText("Hi " + user.getDisplayName());
        ///Setting Customer////////////////////////
        ///////////////////////////////////////////
        Customer customer=new Customer();
        //customer.setCustomerName(user.getDisplayName());
        customer.setCustomerName("Test1");
        customer.setEmail(user.getEmail());
        customer.setCreditCardInfo("creditinfo1");
        customer.setShippingInfo("shippinginfo1");
        customer.dispInfo();
        Customer customer2=new Customer();
        customer.setCustomerName("Test2");
        customer.setEmail("cust2@gmail.com");
        customer.setCreditCardInfo("creditinfo2");
        customer.setShippingInfo("shippinginfo2");
        //////////////////////////////////////////////
        FirebaseDatabase mFirebaseInstance= FirebaseDatabase.getInstance();
        DatabaseReference mRef=mFirebaseInstance.getReference("Customer");
        mRef.child(user.getUid()).setValue(customer);
        mRef=mFirebaseInstance.getReference("Products");
        String cid=mRef.push().getKey();
        mRef.child(cid).setValue(new Products(1,"Iphone X 64GB Black",649.99f,50,"https://e-mac.eu/image/cache/Apple-iPhone-XS-Max-6.5-inch-64GB-Gold-MT522ZD:A_front-500x500.png"));


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}
