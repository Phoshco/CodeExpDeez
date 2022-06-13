package com.example.codeexpdeez;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class eMart extends Fragment {

    private View view;
    private HashMap<String, String> user;
    private Integer privilege;
    private RecyclerView mRecyclerView;
    public EventAdapter mEventAdapter;
    ScheduleFirebase fb;
    private static final String TAG = "Firebase";
    private SearchView mSearchView;
    private EditText editText;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_emart, container, false);
        mSearchView = view.findViewById(R.id.search_bar);
        mSearchView.setQueryHint("Search View"); // set the hint text to display in the query text field
        mRecyclerView = view.findViewById(R.id.search_results);



        // perform set on query text listener event
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                CharSequence value = mSearchView.getQuery(); // get the query string currently in the text field
//                Log.d("LALA", String.valueOf(value));
                updateRecyclerView(String.valueOf(value));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                return false;
            }
        });



        return view;
    }

    public void updateRecyclerView(String searchValue){
        List<EmartClass> emartThings = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance("https://expcode-2022-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Em");

        EmartFirebase fb = new EmartFirebase(searchValue);
        fb.readEmart(new EmartFirebase.DataStatus() {
            @Override
            public void DataInserted() {}

            @Override
            public void DataIsLoadedEmart(List<EmartClass> emartThings, List<String> keys){
                Log.i("RECYCLER", "IN");
                new EmartRecyclerView_Config().setConfig(mRecyclerView, view.getContext(), emartThings , keys);
            }
        });
    }
}