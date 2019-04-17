package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.ilyas.androidshoppingapp.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImg;
    EditText edtPhone, edtFullName, edtAddress;
    TextView txtCloseBtn, txtUpdateBtn, txtChangeProfile;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Uri imageUri;
    String myUrl = "";
    StorageReference storageDPRef;
    String checker = "";
    StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        storageDPRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        profileImg = (CircleImageView)findViewById(R.id.profile_image);
        edtPhone = (EditText) findViewById(R.id.edtPhoneNumber);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);

        txtCloseBtn = (TextView) findViewById(R.id.close_profile);
        txtUpdateBtn = (TextView) findViewById(R.id.update_account_profile);
        txtChangeProfile = (TextView) findViewById(R.id.change_profile);

        userInfoDisplay(profileImg, edtPhone, edtFullName, edtAddress);

        txtCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker == "clicked"){
                    userInfoSve();
                }else{
                    updateOnlyUserInfo();
                }
            }
        });

        txtChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(ProfileActivity.this);

            }
        });

    }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", edtFullName.getText().toString());
        userMap.put("address", edtAddress.getText().toString());
        userMap.put("phone", edtPhone.getText().toString());

        ref.child(user.getUid()).updateChildren(userMap);

//        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        Toast.makeText(ProfileActivity.this, "Info Updated", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImg.setImageURI(imageUri);
        }else{
            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            finish();
        }
    }

    private void userInfoSve() {
        if(TextUtils.isEmpty(edtFullName.getText().toString())){
            Toast.makeText(this, "Name is Mandatory", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(edtAddress.getText().toString())){
            Toast.makeText(this, "Address is Mandatory", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(edtPhone.getText().toString())){
            Toast.makeText(this, "Name is Mandatory", Toast.LENGTH_SHORT).show();
        }else if(checker == "clicked"){
            uploadImage();
        }
    }

    private void uploadImage() {

        if (imageUri != null){
            final StorageReference fileRef = storageDPRef.child(user.getUid() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            })
            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("name", edtFullName.getText().toString());
                        userMap.put("address", edtAddress.getText().toString());
                        userMap.put("phone", edtPhone.getText().toString());
                        userMap.put("image", myUrl);

                        ref.child(user.getUid()).updateChildren(userMap);

                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                        Toast.makeText(ProfileActivity.this, "Info Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(ProfileActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "Image is Not Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(final CircleImageView profileImg, final EditText edtPhone, final EditText edtFullName, final EditText edtAddress) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("image").exists()){
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();

                        Picasso.get().load(image).into(profileImg);
                        edtFullName.setText(name);
                        edtAddress.setText(address);
                        edtPhone.setText(phone);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
