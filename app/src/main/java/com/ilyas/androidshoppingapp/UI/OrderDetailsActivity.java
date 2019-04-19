package com.ilyas.androidshoppingapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyas.androidshoppingapp.R;
import com.ilyas.androidshoppingapp.ViewHolder.OrderDetailsViewHolder;
import com.ilyas.androidshoppingapp.model.OrderedProduct;
import com.squareup.picasso.Picasso;

public class OrderDetailsActivity extends AppCompatActivity {

    String key, sName, sPhone, sAddress, sCity, total;

    FirebaseAuth mAuth;
    FirebaseUser fUser;
    DatabaseReference myRef;

    TextView txtTotal, txtSName, txtSPhone, txtSAddress, txtSCity;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        key = getIntent().getStringExtra("key");
        sName = getIntent().getStringExtra("name");
        sPhone = getIntent().getStringExtra("phone");
        sAddress = getIntent().getStringExtra("address");
        sCity = getIntent().getStringExtra("city");
        total = getIntent().getStringExtra("total");

        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child("orders").child(fUser.getUid()).child(key).child("ordered_product");

        setTextView();


        recyclerView = (RecyclerView) findViewById(R.id.ordered_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setTextView()
    {
        txtSName = (TextView) findViewById(R.id.shipping_name_ordered);
        txtSPhone = (TextView) findViewById(R.id.shipping_phone_ordered);
        txtSAddress = (TextView) findViewById(R.id.shipping_address_ordered);
        txtSCity = (TextView) findViewById(R.id.shipping_city_ordered);
        txtTotal = (TextView) findViewById(R.id.total_price_ordr_details);

        txtSName.setText(sName);
        txtSPhone.setText(sPhone);
        txtSAddress.setText(sAddress);
        txtSCity.setText(sCity);
        txtTotal.setText("Total Price: $" + total);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<OrderedProduct> options =
                new FirebaseRecyclerOptions.Builder<OrderedProduct>()
                        .setQuery(myRef, OrderedProduct.class).build();

        FirebaseRecyclerAdapter<OrderedProduct, OrderDetailsViewHolder> adapter =
                new FirebaseRecyclerAdapter<OrderedProduct, OrderDetailsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderDetailsViewHolder orderDetailsViewHolder, int i, @NonNull OrderedProduct orderedProduct) {
                        orderDetailsViewHolder.txtpName.setText(orderedProduct.getPname());
                        orderDetailsViewHolder.txtpPrice.setText("Price: $" + orderedProduct.getPprice());
                        orderDetailsViewHolder.txtpQty.setText("Quantity: " + orderedProduct.getQuantity());
                        Picasso.get().load(orderedProduct.getImage()).into(orderDetailsViewHolder.imgpProd);
                    }

                    @NonNull
                    @Override
                    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, parent, false);
                        OrderDetailsViewHolder ODVH = new OrderDetailsViewHolder(v);
                        return ODVH;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
