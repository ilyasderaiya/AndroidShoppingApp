package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.ViewHolder.OrdersViewHolder;
import com.ilyas.androidshoppingapp.model.Orders;

public class OrdersActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser fUser;
    DatabaseReference orderRef;
    TextView message;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        message = (TextView) findViewById(R.id.msg_orders);

        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        orderRef = FirebaseDatabase.getInstance().getReference().child("orders").child(fUser.getUid());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    message.setVisibility(View.GONE);
                }
                else
                {
                    message.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.order_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(orderRef, Orders.class).build();

        FirebaseRecyclerAdapter<Orders, OrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrdersViewHolder ordersViewHolder, int i, @NonNull final Orders orders) {
                        int j = i+1;

                        ordersViewHolder.txtOrderNo.setText("Order No: " + j);
                        ordersViewHolder.txtOrderPlaced.setText("Placed on:" + orders.getDate());
                        ordersViewHolder.txtTotal.setText("ToTal: $" + orders.getTotal_amount());

                        ordersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
                                intent.putExtra("key", orders.getKey());
                                intent.putExtra("name", orders.getName());
                                intent.putExtra("phone", orders.getPhone());
                                intent.putExtra("address", orders.getAddress());
                                intent.putExtra("city", orders.getCity());
                                intent.putExtra("total", orders.getTotal_amount());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_list, parent, false);
                        OrdersViewHolder OH = new OrdersViewHolder(v);
                        return OH;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
