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


public class CheckoutHistoryView_Config {
    private Context mContext;
    private HistoryAdapter mCartAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<CheckoutHistory> checkouts, List<String> keys){
        mContext = context;
        mCartAdapter = new HistoryAdapter(checkouts,keys);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mCartAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),layout.getOrientation());
        recyclerView.addItemDecoration(divider);

    }

    // to inflate the layout cart_items
    class CartItemView extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mSize;
        private TextView mColour;
        private TextView mPrice;
        private TextView mQuantity;
        private TextView mPriceTotal;
        private TextView mCollectionTime;
        private String key;

        public CartItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.history_list_item, parent, false));
            mName = (TextView) itemView.findViewById(R.id.hitemname);
            mQuantity = (TextView) itemView.findViewById(R.id.hquantity_value);
            mSize = (TextView) itemView.findViewById(R.id.hsize_value);
            mColour = (TextView) itemView.findViewById(R.id.hcolour_value);
            mPrice = (TextView) itemView.findViewById(R.id.hprice_value);
            mCollectionTime = (TextView) itemView.findViewById(R.id.hcollect_time_value);
        }

        public void bind(CheckoutHistory checkouts, String key){
            mName.setText(checkouts.getName());
            mColour.setText(checkouts.getColour());
            String price = "Price: " + checkouts.getPrice();
            mPrice.setText(price);
            mSize.setText(checkouts.getSize());
            String temp = checkouts.getQuantity();
            mQuantity.setText(temp);
            String time = "Collection time : " + checkouts.getCollectionTime();
            mCollectionTime.setText(time);
            this.key = key;

        }
    }

    // the adaptor for the cart list item
    class HistoryAdapter extends RecyclerView.Adapter<CartItemView>{
        private List<CheckoutHistory> mCart;
        private List<String> mKey;

        @NonNull
        @Override
        public CartItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CartItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemView holder, int position) {
            holder.bind(mCart.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mCart.size();
        }

        public HistoryAdapter(List<CheckoutHistory> mCart, List<String> mKey) {
            this.mCart = mCart;
            this.mKey = mKey;
        }
    }
}
