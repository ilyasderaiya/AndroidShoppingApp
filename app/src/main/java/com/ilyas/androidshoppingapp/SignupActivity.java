package com.ilyas.androidshoppingapp;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignupActivity extends AppCompatActivity {

    EditText email, password;
    Button registerBtn,cancelBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        registerBtn = (Button) findViewById(R.id.btnRegister);
        cancelBtn = (Button) findViewById(R.id.btnCancel);

        firebaseAuth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = email.getText().toString();
                String pwd = password.getText().toString();
                String msg = "";
                if(TextUtils.isEmpty(eml)){
                    msg = "Email Can't be Empty";
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    email.setError(msg);
                    return;
                }

                if(TextUtils.isEmpty(pwd)){
                    msg = "Password Can't be Empty";
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    password.setError(msg);
                    return;
                }

                if(pwd.length() < 6){
                    msg = "Password Must be Longer than 6 Character";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    password.setError(msg);
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(eml, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else{
                            //Toast.makeText(getApplicationContext(), "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                            Snackbar.make(findViewById(R.id.ConstraintLayout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }
}
