package com.example.codeexpdeez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ViewEmartItem extends AppCompatActivity {
    private Spinner colourDropdown, sizeDropdown, qtyDropdown;
    private TextView nameTxtView;
    private TextView costValueTxtView;
    private TextView creditTxtView;
    private Button btnSumbit;
    private HashMap<String, String> user;
    private String credit;

    private String key;
    private String id;
    private String name;
    private String price;
    private String colour;
    private String size;
    private String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emart_item);

        key = getIntent().getStringExtra("key");
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");

        colourDropdown = findViewById(R.id.spinnerCOlour);
        sizeDropdown = findViewById(R.id.spinnerSize);
        qtyDropdown = findViewById(R.id.spinnerQuantity);
        nameTxtView = findViewById(R.id.emptytextview);
        costValueTxtView = findViewById(R.id.textViewCostValue);
        creditTxtView = findViewById(R.id.textViewCreditValue);


        SessionManager session = new SessionManager(ViewEmartItem.this);
        user = session.getUserDetails();
        credit = user.get("Credit").toString();
        creditTxtView.setText(credit);
        nameTxtView.setText(name);

        String[] citems = new String[]{"Red", "Black", "Blue"};
        ArrayAdapter<String> cadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, citems);
        colourDropdown.setAdapter(cadapter);

        String[] sitems = new String[]{"Red", "Black", "Blue"};
        ArrayAdapter<String> sadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sitems);
        sizeDropdown.setAdapter(sadapter);

        String[] qitems = new String[]{"1", "2", "3"};
        ArrayAdapter<String> qadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qitems);

        qtyDropdown.setAdapter(qadapter);
        qtyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                price = String.valueOf(Integer.parseInt(price) * Integer.parseInt(String.valueOf(qtyDropdown.getSelectedItem())));
                costValueTxtView.setText(price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSumbit = findViewById(R.id.dropdownSubmit);
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colour = String.valueOf(colourDropdown.getSelectedItem());
                size = String.valueOf(sizeDropdown.getSelectedItem());
                quantity = String.valueOf(qtyDropdown.getSelectedItem());

                Cart cart = new Cart();
                cart.setAllParam(id, name, price, colour, size, quantity, key);
                new CartFirebase().updateCart(cart, new CartFirebase.DataStatus(){

                    @Override
                    public void DataIsLoadedCart(List<Cart> carts, List<String> keys) {}

                    @Override
                    public void DataInserted() {
                        Toast.makeText(view.getContext(), "The item has been added successfully into cart", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), eMart.class);
                        startActivity(intent);
                    }
                });

            }
        });


    }
}