package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.ViewHolder.CartViewHolder;
import com.ilyas.androidshoppingapp.model.Cart;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button btnCheckout;
    TextView totalPrice;

    FirebaseAuth mAuth;
    FirebaseUser fuser;

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


        btnCheckout = (Button)findViewById(R.id.btn_checkout_cart);
        totalPrice = (TextView)findViewById(R.id.total_price_cart);

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
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                        cartViewHolder.txtProductName.setText(cart.getPname());
                        cartViewHolder.txtProductPrice.setText("Price: $" + cart.getPprice());
                        cartViewHolder.txtProductQty.setText("Quantity:" + cart.getQuantity());
                        Picasso.get().load(cart.getImage()).into(cartViewHolder.imgProductImage);
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
