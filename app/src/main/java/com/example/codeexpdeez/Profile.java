package com.example.codeexpdeez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    private TextView username;
    private TextView details;
    private ImageView logout;
    private Button changePass;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_profile, container, false);

        username= (TextView) view.findViewById(R.id.username);
        changePass = (Button) view.findViewById(R.id.changePass);
        details = (TextView) view.findViewById(R.id.details);

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference root = FirebaseDatabase.getInstance("https://expcode-2022-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(uid);

        setHasOptionsMenu(true);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = new User();
                user.setName(snapshot.child("info").child("name").getValue()+"");
                user.setRank(snapshot.child("info").child("rank").getValue()+"");
                user.setUnit(snapshot.child("info").child("unit").getValue()+"");
                user.setCoy(snapshot.child("info").child("coy").getValue()+"");
                String usernameToAssign= user.getRank() + " " + user.getName();
                username.setText(usernameToAssign);
                String detailsToAssign = user.getUnit() + " " + user.getCoy();
                details.setText(detailsToAssign);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ChangePass.class));
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(view.getContext(),"Logged Out!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(view.getContext(), Launch.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}