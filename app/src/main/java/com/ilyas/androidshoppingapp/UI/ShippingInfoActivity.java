package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.model.ShippingInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ShippingInfoActivity extends AppCompatActivity {

    EditText edtSName, edtSPhone, edtSAddress, edtSCity;
    Button btnConfirm;
    FirebaseAuth mAuth;
    FirebaseUser fuser;

    String key = "";

    String totalAmount = "";
    String pname, pprice, pquantity, pimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_info);

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();

        totalAmount = getIntent().getStringExtra("Total Price");
        pname = getIntent().getStringExtra("pname");
        pprice = getIntent().getStringExtra("pprice");
        pquantity = getIntent().getStringExtra("quantity");
        pimage = getIntent().getStringExtra("image");

        edtSName = (EditText) findViewById(R.id.shipping_name);
        edtSPhone = (EditText) findViewById(R.id.shipping_phone);
        edtSAddress = (EditText) findViewById(R.id.shipping_address);
        edtSCity = (EditText) findViewById(R.id.shipping_city);
        btnConfirm = (Button) findViewById(R.id.btn_shipping_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtSName.getText().toString()))
                {
                    edtSName.setError("Name Cannot be Empty");
                }
                else if(TextUtils.isEmpty(edtSPhone.getText().toString()))
                {
                    edtSPhone.setError("Name Cannot be Empty");
                }
                else if(TextUtils.isEmpty(edtSAddress.getText().toString()))
                {
                    edtSAddress.setError("Name Cannot be Empty");
                }
                else if(TextUtils.isEmpty(edtSCity.getText().toString()))
                {
                    edtSCity.setError("Name Cannot be Empty");
                }
                else
                {
                    confirmOrder();
                }
            }
        });
    }

    private void confirmOrder() {
        final String saveCurrDate, saveCurrTime;
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM, yyyy");
        saveCurrDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrTime = currentTime.format(callForDate.getTime());

        key = saveCurrDate + saveCurrTime;

        final DatabaseReference confirmOrderRef = FirebaseDatabase.getInstance().getReference()
                .child("orders")
                .child(fuser.getUid()).child(key);

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("key",key);
        ordersMap.put("total amount",totalAmount);
        ordersMap.put("name",edtSName.getText().toString());
        ordersMap.put("phone", edtSPhone.getText().toString());
        ordersMap.put("address", edtSAddress.getText().toString());
        ordersMap.put("city", edtSCity.getText().toString());
        ordersMap.put("date",saveCurrDate);
        ordersMap.put("time",saveCurrTime);
//        ordersMap.put("image", pImageUrl);

        confirmOrderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                System.out.println("Done");
                if(task.isSuccessful())
                {
                    //Empty Cart and confirm Order
                    FirebaseDatabase.getInstance().getReference().child("Cart Item")
                            .child("User view").child(fuser.getUid())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ShippingInfoActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ShippingInfoActivity.this, ConfirmationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


        /*confirmOrderRef.child("ordered product").updateChildren(orderProdMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //Empty Cart and confirm Order
                            FirebaseDatabase.getInstance().getReference().child("Cart Item")
                                    .child("User view").child(fuser.getUid())
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(ShippingInfoActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ShippingInfoActivity.this, ConfirmationActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });*/

    }
}
