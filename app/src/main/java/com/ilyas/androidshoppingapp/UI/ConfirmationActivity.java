package com.ilyas.androidshoppingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ilyas.androidshoppingapp.R;

public class ConfirmationActivity extends AppCompatActivity {

    Button btnContinueShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btnContinueShop = (Button) findViewById(R.id.btn_continue_shopping);

        btnContinueShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmationActivity.this, HomeActivity.class));
            }
        });
    }
}
