package com.ilyas.androidshoppingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    Button btnlgot, btnAddProd;
    EditText prodId, prodName, prodPrice, prodQty;
    ImageView prodImg;

    private String pId, pName, pPrice, pQty, saveCurrentDate, saveCurrentTime;
    private String randomId, downImageUrl;

    FirebaseAuth mAuth;
    private final static int galleryPick = 1;
    private Uri imageUri;
    private StorageReference prodImageRef;
    private DatabaseReference productRef;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        prodImageRef = FirebaseStorage.getInstance().getReference().child("product Images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        btnlgot =  (Button) findViewById(R.id.btnlogout_admin);
        btnAddProd = (Button) findViewById(R.id.btnAddProd);
        prodId = (EditText) findViewById(R.id.edtPrdId);
        prodName = (EditText) findViewById(R.id.edtProdName);
        prodPrice = (EditText) findViewById(R.id.edtProdPrice);
        prodQty = (EditText) findViewById(R.id.edtProdQuantity);
        prodImg = (ImageView)findViewById(R.id.prd_img);

        progressDialog = new ProgressDialog(AdminActivity.this);


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
            storeProductInfo();
        }
    }

    private void storeProductInfo() {

        progressDialog.setMessage("please wait adding new product..."); // Setting Message
        progressDialog.setTitle("Adding New Product"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currDate.format(calendar.getTime());

        SimpleDateFormat currTime = new SimpleDateFormat("HH:MM:SS a");
        saveCurrentTime = currTime.format(calendar.getTime());

        randomId = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = prodImageRef.child(imageUri.getLastPathSegment() + randomId + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(AdminActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            throw task.getException();
                        }

                        downImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downImageUrl = task.getResult().toString();

                            Toast.makeText(AdminActivity.this, "Product Image saved to Database", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveProductInfoToDatabase() {

        HashMap<String, Object> prodMap = new HashMap<>();
        prodMap.put("productID", pId);
        prodMap.put("productName", pName);
        prodMap.put("productPrice", pPrice);
        prodMap.put("quantity", pQty);
        prodMap.put("imageUrl", downImageUrl);
        prodMap.put("key", randomId);

        productRef.child(randomId).updateChildren(prodMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(AdminActivity.this, "Product is Added Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(AdminActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
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
