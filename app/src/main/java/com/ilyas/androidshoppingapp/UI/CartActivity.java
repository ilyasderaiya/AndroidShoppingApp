package com.ilyas.androidshoppingapp.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.ViewHolder.CartViewHolder;
import com.ilyas.androidshoppingapp.model.Cart;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button btnShippingInfo;
    TextView totalPrice;

    String prodName, prodPrice, prodQty, prodImgUrl;

    FirebaseAuth mAuth;
    FirebaseUser fuser;

    Float orderTotal = 0.00f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_cart_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        btnShippingInfo = (Button)findViewById(R.id.btn_shipping_info_cart);
        totalPrice = (TextView)findViewById(R.id.total_price_cart);

       FirebaseDatabase.getInstance().getReference().child("Cart Item").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists())
               {
                   btnShippingInfo.setVisibility(View.VISIBLE);
               }
               else
               {
                   Snackbar.make(findViewById(R.id.ConstraintLayout), "You Don't have anything in your Cart", Snackbar.LENGTH_SHORT).show();
                   btnShippingInfo.setVisibility(View.GONE);

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


        btnShippingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Intent intent = new Intent(CartActivity.this, ShippingInfoActivity.class);
                        intent.putExtra("Total Price", String.valueOf(orderTotal));
                        startActivity(intent);
                        finish();
                    }
                });

                /*Intent intent = new Intent(CartActivity.this, ShippingInfoActivity.class);
                intent.putExtra("Total Price", String.valueOf(orderTotal));
                *//*intent.putExtra("pname", prodName);
                intent.putExtra("pprice", prodPrice);
                intent.putExtra("quantity", prodQty);
                intent.putExtra("image", prodImgUrl);*//*
                startActivity(intent);
                finish();*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart Item");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User view")
                .child(fuser.getUid()).child("products"), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new
                FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                        cartViewHolder.txtProductName.setText(cart.getPname());
                        cartViewHolder.txtProductPrice.setText("Price: $" + cart.getPprice());
                        cartViewHolder.txtProductQty.setText("Quantity:" + cart.getQuantity());
                        Picasso.get().load(cart.getImage()).into(cartViewHolder.imgProductImage);

                        prodName = cart.getPname();
                        prodPrice = cart.getPprice();
                        prodQty = cart.getQuantity();
                        prodImgUrl = cart.getImage();

                        Float oneProductPrice = ( (Float.valueOf(cart.getPprice())) * Float.valueOf(cart.getQuantity()));
                        orderTotal += oneProductPrice;

                        totalPrice.setText("Total: $" + String.valueOf(orderTotal));

                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Edit",
                                                "Delete"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options:");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i)
                                    {
                                        if(i == 0)
                                        {
                                            Intent intent = new Intent(CartActivity.this, ProductInfoAvtivity.class);
                                            intent.putExtra("key", cart.getKey());
                                            startActivity(intent);
                                        }
                                        if(i == 1)
                                        {
                                            cartListRef.child("User view").child(fuser.getUid())
                                                    .child("products").child(cart.getKey())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(CartActivity.this, "Item Deleted from Cart", Toast.LENGTH_SHORT).show();
//                                                                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_list, parent,false);
                        CartViewHolder holder = new CartViewHolder(v);
                        return holder;
                    }
                };
                recyclerView.setAdapter(adapter);
                adapter.startListening();
    }
}
