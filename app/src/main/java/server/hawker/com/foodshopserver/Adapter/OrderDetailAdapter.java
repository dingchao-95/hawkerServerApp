package server.hawker.com.foodshopserver.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

import server.hawker.com.foodshopserver.Model.Cart;
import server.hawker.com.foodshopserver.R;
import server.hawker.com.foodshopserver.Utils.Common;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    Context context;
    List<Cart> itemList;

    public OrderDetailAdapter(Context context) {
        this.context = context;
        this.itemList = new Gson().fromJson(Common.currentOrder.getOrderDetail(), new TypeToken<List<Cart>>(){}.getType());
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_layout,parent,false);
        return new OrderDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        holder.txt_food_amount.setText(""+itemList.get(position).getAmount());
        holder.txt_food_name.setText(new StringBuilder(itemList.get(position).getName()));
        holder.txt_size.setText(itemList.get(position).getMealTakeaway()==0?"Standard":"More");

        Picasso.with(context).load(itemList.get(position).getLink()).into(holder.img_order_item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        ImageView img_order_item;
        TextView txt_food_name,txt_food_amount,txt_size;
        public OrderDetailViewHolder(View itemView) {
            super(itemView);

            img_order_item = (ImageView)itemView.findViewById(R.id.img_order_item);

            txt_food_name = (TextView)itemView.findViewById(R.id.txt_food_name);
            txt_food_amount = (TextView)itemView.findViewById(R.id.txt_food_amount);
            txt_size = (TextView)itemView.findViewById(R.id.txt_size);
        }
    }
}
