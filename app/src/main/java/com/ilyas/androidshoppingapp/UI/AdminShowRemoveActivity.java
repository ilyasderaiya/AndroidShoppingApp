package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.ViewHolder.ProductViewHolder;
import com.ilyas.androidshoppingapp.model.Products;
import com.squareup.picasso.Picasso;

public class AdminShowRemoveActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference productsRef;
    FirebaseAuth mAuth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_remove);

        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = (RecyclerView) findViewById(R.id.admin_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(productsRef, Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                        productViewHolder.txtPName.setText(products.getProductName());
                        productViewHolder.txtPPrice.setText("$" + products.getProductPrice());
                        Picasso.get().load(products.getImageUrl()).into(productViewHolder.pImageView);

                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminShowRemoveActivity.this);
                                builder.setTitle("Remove Product From the Database");
                                builder.setMessage("This Will Remove this Product Press Remove to proceed");

                                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Remove Product from Database
                                        productsRef.child(products.getKey())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        Toast.makeText(AdminShowRemoveActivity.this, "Product Removed SuccessFully", Toast.LENGTH_SHORT).show();
                                                        Snackbar.make(v, "Product Removed SuccessFully", Snackbar.LENGTH_LONG)
                                                                .setAction("Action", null).show();
                                                    }
                                                });
                                    }
                                });
                                builder.show();

                                /*Intent intent = new Intent(HomeActivity.this,ProductInfoAvtivity.class);
                                intent.putExtra("key", products.getKey());
                                startActivity(intent);*/
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
                        ProductViewHolder vh = new ProductViewHolder(v);
                        return vh;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
