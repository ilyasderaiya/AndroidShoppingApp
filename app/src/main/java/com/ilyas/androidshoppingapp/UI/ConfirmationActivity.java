package com.ilyas.androidshoppingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ilyas.androidshoppingapp.R;

public class ConfirmationActivity extends AppCompatActivity {

    Button btnContinueShop;
    String sName, sPhone, sAddress, sCity;
    TextView txtSName, txtSPhone, txtSAddress, txtSCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        sName = getIntent().getStringExtra("sname");
        sPhone = getIntent().getStringExtra("sphone");
        sAddress = getIntent().getStringExtra("saddress");
        sCity = getIntent().getStringExtra("scity");

        btnContinueShop = (Button) findViewById(R.id.btn_continue_shopping);
        txtSName = (TextView) findViewById(R.id.shipping_name_confirm);
        txtSPhone = (TextView) findViewById(R.id.shipping_phone_confirm);
        txtSAddress = (TextView) findViewById(R.id.shipping_address_confirm);
        txtSCity = (TextView) findViewById(R.id.shipping_city_confirm);


        txtSName.setText(sName);
        txtSPhone.setText(sPhone);
        txtSAddress.setText(sAddress);
        txtSCity.setText(sCity);

        btnContinueShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ConfirmationActivity.this, HomeActivity.class));

                finish();
            }
        });
    }
}
