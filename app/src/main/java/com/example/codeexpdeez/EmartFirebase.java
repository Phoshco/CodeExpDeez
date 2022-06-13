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
    private List<EmartClass> emartThings = new ArrayList<>();

    public EmartFirebase(){
        try {
            String username = FirebaseAuth.getInstance().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReferenceCart = mDatabase.getReference().child("Emart");
        }
        catch (Exception e){
            Log.i("ERROR" ,  e.toString());
        }
    }

    public interface DataStatus{
        void DataIsLoadedEvent(List<EmartClass> emartThings, List<String> keys);
        void DataInserted();
    }
    public void readEmart(final DataStatus dataStatus){

        mReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emartThings.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    EmartClass emartClass = keyNode.getValue(EmartClass.class);
                    emartThings.add(emartClass);
                }
                dataStatus.DataIsLoadedEvent(emartThings,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("PROBLEM FIREBASE", "not nice");
            }
        });
    }

//    public void updateEmart(EmartClass emartClass, final DataStatus dataStatus){
////        String key = emartClass.getDate();
////        mReferenceCart.child(key).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
////            @Override
////            public void onComplete(@NonNull Task<Void> task) {
////                dataStatus.DataInserted();
////            }
////        });
////    }

//    public void deleteEmart(int i, final DataStatus dataStatus){
//        String temp = events.get(i).getDate();
//        mReferenceCart.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                dataStatus.DataInserted();
//            }
//        });
//    }
}
