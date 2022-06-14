package com.example.codeexpdeez;


import static java.lang.Integer.parseInt;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
The class FirebaseHelper
 */

public class CartFirebase {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceProducts;
    private DatabaseReference mReferenceCart;
    private DatabaseReference mReferenceCheckout;
    //private List<Product> products = new ArrayList<>();
    private List<Cart> carts = new ArrayList<>();

    public CartFirebase(){
        try {
            String username = FirebaseAuth.getInstance().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReferenceCheckout = mDatabase.getReference(username).child("Checkout");
            mReferenceCart = mDatabase.getReference(username).child("Cart");
        }
        catch (Exception e){
            Log.i("ERROR" ,  e.toString());
        }
    }

    public interface DataStatus{
        void DataIsLoadedCart(List<Cart> carts, List<String> keys, Integer totalPrice);
        void DataInserted();
//        void DataUpdated();
    }
    public void readCart(final DataStatus dataStatus){
        mReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carts.clear();
                List<String> keys = new ArrayList<>();
                Integer totalPrice = 0;

                for(DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Cart cart = keyNode.getValue(Cart.class);
                    carts.add(cart);
                    totalPrice = totalPrice + Integer.parseInt(cart.getPrice());
                }
                dataStatus.DataIsLoadedCart(carts,keys, totalPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("PROBLEM FIREBASE", "not nice");
            }
        });
    }

    public void updateCart(Cart cart, final DataStatus dataStatus){
        String key = cart.getKey();
        String qtystr = cart.getQuantity();
        int qty = parseInt(qtystr);
        mReferenceCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChild(key)){
//                    String qtyy = (String) snapshot.child(key).getValue();
//                    int quantity = parseInt(qtyy);
//                    cart.setQuantity(String.valueOf(quantity+qty));
//                }
                mReferenceCart.child(key).setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dataStatus.DataInserted();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void checkoutCart(String collectionTime, final DataStatus dataStatus){

        mReferenceCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                carts.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Cart cart = keyNode.getValue(Cart.class);
                    mReferenceCheckout.child(keyNode.getKey()).setValue(cart);
                    mReferenceCheckout.child(keyNode.getKey()).child("collection_time").setValue(collectionTime).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dataStatus.DataInserted();
                        }
                    });

                    keyNode.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}