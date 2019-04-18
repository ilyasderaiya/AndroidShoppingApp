package com.ilyas.androidshoppingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ilyas.androidshoppingapp.R;

public class AdminUiActivity extends AppCompatActivity {
    Button btnShowProd, btnAddProd, btnlgot;
    TextView txtAdminEmail;

    FirebaseAuth mAuth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ui);

        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();

        btnlgot =  (Button) findViewById(R.id.btnlogout_admin);
        txtAdminEmail = (TextView) findViewById(R.id.txt_admin_email);
        btnShowProd = (Button) findViewById(R.id.btn_show_remove_Product);
        btnAddProd = (Button) findViewById(R.id.btn_add_product);

        txtAdminEmail.setText(fUser.getEmail());


        btnlgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AdminUiActivity.this,LoginActivity.class));
                finish();
            }
        });


        btnShowProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminUiActivity.this, AdminShowRemoveActivity.class));
            }
        });

        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminUiActivity.this, AdminActivity.class));
            }
        });
    }
}
