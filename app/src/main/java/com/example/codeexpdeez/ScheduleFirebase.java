package com.example.codeexpdeez;

import static java.lang.Integer.parseInt;

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

public class ScheduleFirebase {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCart;
    private List<ScheduleEvent> events = new ArrayList<>();

    public ScheduleFirebase(String unit, String coy){
        try {
            String username = FirebaseAuth.getInstance().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReferenceCart = mDatabase.getReference().child("Events").child(unit).child(coy);
        }
        catch (Exception e){
            Log.i("ERROR" ,  e.toString());
        }
    }

    public interface DataStatus{
        void DataIsLoadedEvent(List<ScheduleEvent> events, List<String> keys);
        void DataInserted();
    }
    public void readSchedule(final DataStatus dataStatus){

        mReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode: snapshot.getChildren()){
                    //keys.add(keyNode.getKey());
                    keys.add(keyNode.child("date").getValue().toString());
                    ScheduleEvent event = keyNode.getValue(ScheduleEvent.class);
                    events.add(event);
                }
                dataStatus.DataIsLoadedEvent(events,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("PROBLEM FIREBASE", "not nice");
            }
        });
    }

    public void updateSchedule(ScheduleEvent event, final DataStatus dataStatus){
        String key = event.getDate();
        /*String deets = event.getDetails();
        String date = event.getDate();
        mReferenceCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    String qtyy = (String) snapshot.child(key).child("quantity").getValue();
                    int quantity = parseInt(qtyy);
                    cart.setQuantity(String.valueOf(quantity+qty));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        mReferenceCart.child(key).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dataStatus.DataInserted();
            }
        });
    }

    public void deleteSchedule(int i, final DataStatus dataStatus){
        String temp = events.get(i).getDate();
        mReferenceCart.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dataStatus.DataInserted();
            }
        });
    }
}
