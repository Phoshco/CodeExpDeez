package com.example.codeexpdeez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleRecyclerView {
    private Context mContext;
    private EventAdapter mEventAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<ScheduleEvent> events, List<String> keys){
        mContext = context;
        mEventAdapter = new EventAdapter(events,keys);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mEventAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),layout.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    // to inflate the layout cart_items
    class EventItemView extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mDetails;
        private TextView mDate;

        private String key;

        public EventItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.event_items, parent, false));
            mTitle = (TextView) itemView.findViewById(R.id.event_title);
            mDetails = (TextView) itemView.findViewById(R.id.event_details);
            mDate = (TextView) itemView.findViewById(R.id.event_date);
        }

        public void bind(ScheduleEvent events, String key){
            mTitle.setText(events.getTitle());
            mDate.setText(events.getDate());
            mDetails.setText(events.getDetails());
            this.key = key;
        }
    }

    // the adaptor for the cart list item
    class EventAdapter extends RecyclerView.Adapter<EventItemView>{
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
    }
}