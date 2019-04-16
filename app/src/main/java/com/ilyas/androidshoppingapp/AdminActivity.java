package com.ilyas.androidshoppingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminActivity extends AppCompatActivity {

    Button btnlgot, btnAddProd;
    EditText prodId, prodName, prodPrice, prodQty;
    ImageView prodImg;

    private String pId, pName, pPrice, pQty, saveCurrentDate, saveCurrentTime;
    private String randomId;

    FirebaseAuth mAuth;
    private final static int galleryPick = 1;
    private Uri imageUri;
    private StorageReference prodImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        prodImageRef = FirebaseStorage.getInstance().getReference().child("product Images");

        btnlgot =  (Button) findViewById(R.id.btnlogout_admin);
        btnAddProd = (Button) findViewById(R.id.btnAddProd);
        prodId = (EditText) findViewById(R.id.edtPrdId);
        prodName = (EditText) findViewById(R.id.edtProdName);
        prodPrice = (EditText) findViewById(R.id.edtProdPrice);
        prodQty = (EditText) findViewById(R.id.edtProdQuantity);
        prodImg = (ImageView)findViewById(R.id.prd_img);


        Toast.makeText(this, "Welcome Admin...", Toast.LENGTH_SHORT).show();

        prodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

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

    private void ValidateProductData() {
        pId = prodId.getText().toString();
        pName = prodName.getText().toString();
        pPrice = prodPrice.getText().toString();
        pQty = prodQty.getText().toString();

        if(imageUri == null){
            Toast.makeText(this,"Image is Mandatory", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pId)){
            prodId.setError("Enter Product Id");
        }else if(TextUtils.isEmpty(pName)){
            prodName.setError("Enter Product Name");
        }else if(TextUtils.isEmpty(pPrice)){
            prodPrice.setError("Enter Product Price");
        }else if(TextUtils.isEmpty(pQty)){
            prodQty.setError("Enter Product Id");
        }else{
            storeProductInfo()
        }
    }

    private void storeProductInfo() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currDate.format(calendar.getTime());

        SimpleDateFormat currTime = new SimpleDateFormat("HH:MM:SS a");
        saveCurrentTime = currTime.format(calendar.getTime());

        randomId = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = prodImageRef.child(imageUri.getLastPathSegment() + randomId + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    private void openGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            prodImg.setImageURI(imageUri);
        }
    }
}
