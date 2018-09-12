package server.hawker.com.foodshopserver.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import server.hawker.com.foodshopserver.Interface.ItemClickListener;
import server.hawker.com.foodshopserver.R;

public class FoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public ImageView img_product;
    public TextView txt_food_name,txt_price;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodListViewHolder(View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.img_product);
        txt_food_name = (TextView)itemView.findViewById(R.id.txt_food_name);
        txt_price = (TextView)itemView.findViewById(R.id.txt_price);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,false);
    }
}
