package com.example.codeexpdeez;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HistoryFirebase {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCheckout;
    private List<CheckoutHistory> checkouts = new ArrayList<>();

    public HistoryFirebase() {
        try {
            String username = FirebaseAuth.getInstance().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReferenceCheckout = mDatabase.getReference(username).child("Checkout");
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
    }

    public interface DataStatus {
        void DataIsLoadedCart(List<CheckoutHistory> checkouts, List<String> keys, Integer totalPrice);

        void DataInserted();
    }

    public void readCheckout(final DataStatus dataStatus) {
        mReferenceCheckout.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkouts.clear();
                List<String> keys = new ArrayList<>();
                Integer totalPrice = 0;

                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    CheckoutHistory checkout = keyNode.getValue(CheckoutHistory.class);
                    checkouts.add(checkout);
                    totalPrice = totalPrice + Integer.parseInt(checkout.getPrice());
                }
                dataStatus.DataIsLoadedCart(checkouts, keys, totalPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("PROBLEM FIREBASE", "not nice");
            }
        });
    }
}