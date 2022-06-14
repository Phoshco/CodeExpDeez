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

public class AnnouncementFirebase {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCart;
    private List<AnnouncementObject> announcements = new ArrayList<>();

    public AnnouncementFirebase(String unit, String coy){
        try {
            String username = FirebaseAuth.getInstance().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReferenceCart= mDatabase.getReference().child("Announcements").child(unit).child(coy);
        }
        catch(Exception e){
            Log.i("ERROR", e.toString());
        }
    }
    public interface DataStatus{
        void DataIsLoadedAnnouncements(List<AnnouncementObject> announcements, List<String> keys);
        void DataInserted();
    }
    public void readAnnouncement(final DataStatus dataStatus){
        mReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                announcements.clear();
                List<String> keys= new ArrayList<>();
                for (DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.child("date").getValue().toString());
                    AnnouncementObject announcement= keyNode.getValue(AnnouncementObject.class);
                    announcements.add(announcement);
                }
                dataStatus.DataIsLoadedAnnouncements(announcements,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Problem Firebase", "not stonks");
            }
        });
    }
    public void updateAnnouncement(AnnouncementObject announcement, final DataStatus dataStatus){
        String key= announcement.getDate();
        mReferenceCart.child(key).setValue(announcement).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dataStatus.DataInserted();
            }
        });
    }
    public void deleteSchedule(int i, final DataStatus dataStatus){
        String temp = announcements.get(i).getDate();
        mReferenceCart.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dataStatus.DataInserted();
            }
        });
    }
}
