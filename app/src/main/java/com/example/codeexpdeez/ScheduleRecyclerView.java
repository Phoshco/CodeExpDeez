package com.example.codeexpdeez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleRecyclerView {}
/*    public Context mContext;
    public EventAdapter mEventAdapter;
    //private EventAdapter.ItemClickListener mClickListener;

    public void setConfig(RecyclerView recyclerView, Context context, List<ScheduleEvent> events, List<String> keys){
        mContext = context;
        mEventAdapter = new EventAdapter(context,events,keys);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mEventAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),layout.getOrientation());
        recyclerView.addItemDecoration(divider);
    }*/
/*
    // to inflate the layout cart_items
    class EventItemView extends RecyclerView.ViewHolder *//*implements View.OnClickListener*//*{
        private TextView mTitle;
        private TextView mDetails;
        private TextView mDate;

        private String key;

        public EventItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.event_items, parent, false));
            mTitle = (TextView) itemView.findViewById(R.id.event_title);
            mDetails = (TextView) itemView.findViewById(R.id.event_details);
            mDate = (TextView) itemView.findViewById(R.id.event_date);

            //mTitle.setOnClickListener(this);
        }

        public void bind(ScheduleEvent events, String key){
            mTitle.setText(events.getTitle());
            mDate.setText(events.getDate());
            mDetails.setText(events.getDetails());
            this.key = key;
        }

        *//*@Override
        public void onClick(View view){
            if(mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }*//*
    }

    // the adaptor for the cart list item
    public class EventAdapter extends RecyclerView.Adapter<EventItemView>{
        private List<ScheduleEvent> mEvent;
        private List<String> mKey;

        @NonNull
        @Override
        public EventItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EventItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventItemView holder, int position) {
            holder.bind(mEvent.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mEvent.size();
        }

        public EventAdapter(List<ScheduleEvent> mEvent, List<String> mKey) {
            this.mEvent = mEvent;
            this.mKey = mKey;
        }

        *//*void setClickListener(ItemClickListener itemClickListener) {
            ScheduleRecyclerView.this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }*//*

    }*/
//}