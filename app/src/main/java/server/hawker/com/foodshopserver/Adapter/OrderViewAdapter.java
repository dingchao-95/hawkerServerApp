package server.hawker.com.foodshopserver.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import server.hawker.com.foodshopserver.Adapter.ViewHolder.OrderViewHolder;
import server.hawker.com.foodshopserver.Interface.ItemClickListener;
import server.hawker.com.foodshopserver.Model.Order;
import server.hawker.com.foodshopserver.R;
import server.hawker.com.foodshopserver.Utils.Common;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    Context context;
    List<Order> orderList;

    public OrderViewAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.txt_order_id.setText(new StringBuilder("#").append(orderList.get(position).getOrderId()));
        holder.txt_order_price.setText(new StringBuilder("$").append(orderList.get(position).getOrderPrice()));
        holder.txt_order_comment.setText(orderList.get(position).getOrderComment());
        holder.txt_order_status.setText(new StringBuilder("Order Status: ").append(Common.convertCodeToStatus(orderList.get(position).getOrderStatus())));

        holder.setiItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, boolean isLongClick) {
                //implement it later.
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
