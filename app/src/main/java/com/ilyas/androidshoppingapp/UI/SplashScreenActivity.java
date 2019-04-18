package com.ilyas.androidshoppingapp.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.ilyas.androidshoppingapp.R;

@SuppressWarnings("deprecation")
public class SplashScreenActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(SplashScreenActivity.this))
            buildDialog(SplashScreenActivity.this).show();
        else {
            setContentView(R.layout.activity_splash_screen);
            handler = new Handler();
            firebaseAuth = FirebaseAuth.getInstance();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (firebaseAuth.getCurrentUser() != null) {
                        if(firebaseAuth.getCurrentUser().getEmail().equals("admin@admin.com")){
                            startActivity(new Intent(SplashScreenActivity.this,AdminUiActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                            finish();
                        }
                    } else {
                        Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }
            }, 3000);
        }
    }



    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm != null ? cm.getActiveNetworkInfo() : null;

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) ||
                    (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else
                return false;
        }
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;

    }
}