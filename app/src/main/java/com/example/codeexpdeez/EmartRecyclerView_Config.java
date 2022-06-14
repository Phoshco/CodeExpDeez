package com.example.codeexpdeez;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmartRecyclerView_Config {
    private Context mContext;
    private EmartAdapter mEmartAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<EmartClass> emarts, List<String> keys){
        mContext = context;
        mEmartAdapter = new EmartAdapter(emarts,keys);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mEmartAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),layout.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    // to inflate the layout cart_items
    class EmartItemView extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mPrice;
        private TextView mId;
        private String id;

        private String key;

        public EmartItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.search_result_box, parent, false));
            mName = (TextView) itemView.findViewById(R.id.itemname);
            mPrice = (TextView) itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewEmartItem.class);
                    intent.putExtra("key", key);
                    intent.putExtra("id", id);
                    intent.putExtra("name", mName.getText().toString());
                    intent.putExtra("price", mPrice.getText().toString());

                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(EmartClass emart, String key){
            mName.setText(emart.getName());
            mPrice.setText(emart.getPrice());
            id = emart.getId();
            this.key = key;
        }



    }

    // the adaptor for the cart list item
    class EmartAdapter extends RecyclerView.Adapter<EmartItemView>{
        private List<EmartClass> mEmart;
        private List<String> mKey;

        @NonNull
        @Override
        public EmartItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EmartItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EmartItemView holder, int position) {
            holder.bind(mEmart.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mEmart.size();
        }

        public EmartAdapter(List<EmartClass> mEmart, List<String> mKey) {
            this.mEmart = mEmart;
            this.mKey = mKey;
        }
    }
}
