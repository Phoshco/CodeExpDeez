package com.example.codeexpdeez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementItemView> {
    private List<AnnouncementObject> mAnnouncement;
    private List<String> mKey;
    private ItemClickListener mClickListener;
    private Context mContext;

    @NonNull
    @Override
    public AnnouncementItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new AnnouncementItemView(parent);
    }

    @Override
    public void onBindViewHolder (@NonNull AnnouncementItemView holder, int position){
        holder.bind(mAnnouncement.get(position), mKey.get(position));
    }

    @Override
    public int getItemCount(){
        return mAnnouncement.size();
    }

    public AnnouncementAdapter(Context context, List<AnnouncementObject> mAnnouncement, List<String> mKey){
        this.mAnnouncement= mAnnouncement;
        this.mKey= mKey;
        this.mContext= context;
    }
    void setClickListener(AnnouncementAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    class AnnouncementItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitle;
        private TextView mMessage;
        private TextView mDate;
        private String key;

        public AnnouncementItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.announcement_item, parent, false));
            mTitle= (TextView) itemView.findViewById(R.id.announcement_title);
            mMessage= (TextView) itemView.findViewById(R.id.announcement_message);
            mDate= (TextView) itemView.findViewById(R.id.announcement_date);

            mTitle.setOnClickListener(this);
        }

        public void bind(AnnouncementObject announcement, String key){
            String tem= announcement.getDate() + "\n" + announcement.getTime();
            mTitle.setText(announcement.getTitle());
            mDate.setText(tem);
            mMessage.setText(announcement.getMessage());
            this.key = key;
        }

        @Override
        public void onClick(View view) {
            if(mClickListener!= null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

}
