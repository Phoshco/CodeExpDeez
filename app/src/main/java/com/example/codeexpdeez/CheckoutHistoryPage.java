package com.example.codeexpdeez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class CheckoutHistoryPage extends AppCompatActivity {
    private RecyclerView historyRecycler;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_history_page);

        historyRecycler = findViewById(R.id.checkout_list);
        backBtn = findViewById(R.id.back_btn);
        HistoryFirebase fb = new HistoryFirebase();
        fb.readCheckout(new HistoryFirebase.DataStatus() {
            @Override
            public void DataIsLoadedCart(List<CheckoutHistory> checkouts, List<String> keys, Integer tPrice) {
                Log.i("PRICE", tPrice.toString());
//                totalP = tPrice.toString();
//                totalPrice.setText(totalP);
                new CheckoutHistoryView_Config().setConfig(historyRecycler, CheckoutHistoryPage.this, checkouts , keys);

            }

            @Override
            public void DataInserted() {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}