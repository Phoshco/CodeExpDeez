package com.example.codeexpdeez;

import android.util.Log;

import androidx.annotation.NonNull;

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

public class EmartFirebase {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCart;
    private List<EmartClass> items = new ArrayList<>();

    public EmartFirebase(String itemType){
        try {
            String username = FirebaseAuth.getInstance().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReferenceCart = mDatabase.getReference().child("Emart2").child("Shirt");
            Log.i("CORRECTT" ,  "NICE");

        }
        catch (Exception e){
            Log.i("ERROR" ,  e.toString());
        }
    }

    public interface DataStatus{
        void DataIsLoadedEmart(List<EmartClass> items, List<String> keys);
        void DataInserted();
    }
    public void readEmart(final DataStatus dataStatus){

        mReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    EmartClass emartThing = keyNode.getValue(EmartClass.class);
                    items.add(emartThing);
                }
                Log.i("DATABASE", String.valueOf(items));
                dataStatus.DataIsLoadedEmart(items,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("PROBLEM FIREBASE", "not nice");
            }
        });
    }


}
