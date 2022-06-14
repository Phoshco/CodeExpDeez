package com.example.codeexpdeez;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.view.EventRaiser;

import java.util.HashMap;
import java.util.List;

public class Announcement extends Fragment {

    private RecyclerView mRecyclerView;
    private View view;
    Switch remove;
    private HashMap<String, String> user;
    private Integer privilege;
    public Context mContext;
    public AnnouncementAdapter mAnnouncementAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_announcement, container, false);
        remove = view.findViewById(R.id.remove);

        SessionManager session= new SessionManager(getActivity());
        user = session.getUserDetails();
        privilege= Integer.parseInt(user.get("Privilege").toString());

        mRecyclerView= (RecyclerView) view.findViewById(R.id.announcement_recycler);
        AnnouncementFirebase fb = new AnnouncementFirebase(user.get("Unit").toString(), user.get("Coy").toString());
        fb.readAnnouncement(new AnnouncementFirebase.DataStatus(){
            @Override
            public void DataIsLoadedAnnouncements(List<AnnouncementObject> announcements, List<String> keys) {
                mContext = view.getContext();
                mAnnouncementAdapter = new AnnouncementAdapter(mContext, announcements, keys);
                LinearLayoutManager layout = new LinearLayoutManager(mContext);
                mRecyclerView.setLayoutManager(layout);
                mRecyclerView.setAdapter(mAnnouncementAdapter);
                DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(),layout.getOrientation());
                mRecyclerView.addItemDecoration(divider);
            }

            @Override
            public void DataInserted() {

            }
        });
        remove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(view.getContext(),"Tap on items to remove",Toast.LENGTH_SHORT).show();
                    mAnnouncementAdapter.setClickListener(new AnnouncementAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            fb.deleteSchedule(position, new AnnouncementFirebase.DataStatus() {

                                @Override
                                public void DataIsLoadedAnnouncements(List<AnnouncementObject> announcements, List<String> keys) {

                                }

                                @Override
                                public void DataInserted() {
                                }
                            });
                            mAnnouncementAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else{
                    mAnnouncementAdapter.setClickListener(null);
                }
            }
        });

        setHasOptionsMenu(true);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu addmenu, MenuInflater inflater) {
        inflater.inflate(R.menu.addannouncement, addmenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(privilege==0){
            Toast.makeText(view.getContext(), "You do not have the required authority to add announcements", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            if(id == R.id.add_announcement){
                startActivity(new Intent(view.getContext(), AddAnnouncementObject.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}