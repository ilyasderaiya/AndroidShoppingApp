package com.ilyas.androidshoppingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ilyas.androidshoppingapp.R;

public class ShippingInfoActivity extends AppCompatActivity {

    EditText edtSName, edtSPhone, edtSAddress, edtSCity;
    Button btnConfirm;

    String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_info);

        totalAmount = getIntent().getStringExtra("Total Price");

        edtSName = (EditText) findViewById(R.id.shipping_name);
        edtSPhone = (EditText) findViewById(R.id.shipping_phone);
        edtSAddress = (EditText) findViewById(R.id.shipping_address);
        edtSCity = (EditText) findViewById(R.id.shipping_city);
        btnConfirm = (Button) findViewById(R.id.btn_shipping_confirm);
    }
}
