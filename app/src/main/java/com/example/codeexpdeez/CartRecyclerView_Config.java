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

/*
This class has the adaptor subclass, which is used for the recycler view of the cart list items.
 */

public class CartRecyclerView_Config {
    private Context mContext;
    private CartAdapter mCartAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Cart> carts, List<String> keys){
        mContext = context;
        mCartAdapter = new CartAdapter(carts,keys);
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

        private String key;

        public CartItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.cart_list_item, parent, false));
            mName = (TextView) itemView.findViewById(R.id.itemname);
            mQuantity = (TextView) itemView.findViewById(R.id.quantity_value);
            mSize = (TextView) itemView.findViewById(R.id.size_value);
            mColour = (TextView) itemView.findViewById(R.id.colour_value);
            mPrice = (TextView) itemView.findViewById(R.id.price_value);

        }

        public void bind(Cart carts, String key){
            mName.setText(carts.getName());
            mColour.setText(carts.getColour());
            String price = "Price: " + carts.getPrice();
            mPrice.setText(price);
            mSize.setText(carts.getSize());
            String temp = carts.getQuantity();
            mQuantity.setText(temp);
            this.key = key;

        }
    }

    // the adaptor for the cart list item
    class CartAdapter extends RecyclerView.Adapter<CartItemView>{
        private List<Cart> mCart;
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

        public CartAdapter(List<Cart> mCart, List<String> mKey) {
            this.mCart = mCart;
            this.mKey = mKey;
        }
    }
}
