package com.example.codeexpdeez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

public class Schedule extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private HashMap<String, String> user;
    private Integer privilege;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_schedule, container, false);

        SessionManager session = new SessionManager(getActivity());
        user = session.getUserDetails();
        privilege = Integer.parseInt(user.get("Privilege").toString());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler);
        ScheduleFirebase fb = new ScheduleFirebase(user.get("Unit").toString(),user.get("Coy").toString());
        fb.readSchedule(new ScheduleFirebase.DataStatus() {
            @Override
            public void DataInserted() {}

            @Override
            public void DataIsLoadedEvent(List<ScheduleEvent> events, List<String> keys){
                new ScheduleRecyclerView().setConfig(mRecyclerView, view.getContext(), events, keys);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu addmenu, MenuInflater inflater) {
        inflater.inflate(R.menu.addmenu, addmenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(privilege==0){
            Toast.makeText(view.getContext(), "You do not have the required authority to add announcements", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            if(id == R.id.item){
                startActivity(new Intent(view.getContext(), AddScheduleEvent.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}