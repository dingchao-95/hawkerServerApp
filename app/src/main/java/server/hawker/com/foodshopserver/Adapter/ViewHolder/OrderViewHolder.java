package server.hawker.com.foodshopserver.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import server.hawker.com.foodshopserver.Interface.ItemClickListener;
import server.hawker.com.foodshopserver.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView txt_order_id, txt_order_price, txt_order_comment,txt_order_status;

        ItemClickListener itemClickListener;

public void setiItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        }

public OrderViewHolder(View itemView) {
        super(itemView);

        txt_order_id = (TextView)itemView.findViewById(R.id.txt_order_id);
        txt_order_price = (TextView)itemView.findViewById(R.id.txt_order_price);
        txt_order_comment = (TextView)itemView.findViewById(R.id.txt_order_comment);
        txt_order_status = (TextView)itemView.findViewById(R.id.txt_order_status);

        itemView.setOnClickListener(this);

        }


@Override
public void onClick(View v) {
        itemClickListener.OnClick(v,false);
    }
}
