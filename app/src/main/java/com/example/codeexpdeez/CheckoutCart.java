package com.example.codeexpdeez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CheckoutCart extends AppCompatActivity {


    private TextView totalPrice;
    private Spinner collectionDropdown;
    private RecyclerView cartRecyclerView;
    private Button checkoutBtn;
    private String collectionTime;

    private String totalP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_cart);

        collectionDropdown = findViewById(R.id.spinnerPickUp);
        totalPrice = findViewById(R.id.price_value);
        cartRecyclerView = findViewById(R.id.cart_list);
        checkoutBtn = findViewById(R.id.check_out);
        String[] citems = new String[]{"15:00", "18:00", "21:00"};
        ArrayAdapter<String> cadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, citems);
        collectionDropdown.setAdapter(cadapter);

        totalPrice = findViewById(R.id.price_total);

        CartFirebase fb = new CartFirebase();
        fb.readCart(new CartFirebase.DataStatus() {
            @Override
            public void DataIsLoadedCart(List<Cart> carts, List<String> keys, Integer tPrice) {
                Log.i("PRICE", tPrice.toString());
                totalP = tPrice.toString();
                totalPrice.setText(totalP);
                new CartRecyclerView_Config().setConfig(cartRecyclerView, CheckoutCart.this, carts , keys);

            }

            @Override
            public void DataInserted() {

            }
        });



        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionTime = String.valueOf(collectionDropdown.getSelectedItem());

                fb.checkoutCart(collectionTime,new CartFirebase.DataStatus() {
                    @Override
                    public void DataIsLoadedCart(List<Cart> carts, List<String> keys, Integer p) {

                    }
                    @Override
                    public void DataInserted() {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "All items are checked out!",
                                Toast.LENGTH_LONG);
                        Intent intent = new Intent(CheckoutCart.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


    }
}