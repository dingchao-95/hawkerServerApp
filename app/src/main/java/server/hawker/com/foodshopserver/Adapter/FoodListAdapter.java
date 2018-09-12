package server.hawker.com.foodshopserver.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import server.hawker.com.foodshopserver.Adapter.ViewHolder.FoodListViewHolder;
import server.hawker.com.foodshopserver.FoodList;
import server.hawker.com.foodshopserver.Interface.ItemClickListener;
import server.hawker.com.foodshopserver.Model.Food;
import server.hawker.com.foodshopserver.R;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListViewHolder>{

    Context context;
    List<Food> foodList;

    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.food_item_layout,parent,false);

        return new FoodListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {
        Picasso.with(context)
                .load(foodList.get(position).Link)
                .into(holder.img_product);

        holder.txt_price.setText(new StringBuilder("$").append(foodList.get(position).Price).toString());
        holder.txt_food_name.setText(foodList.get(position).Name);

        //On event of null item click
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, boolean isLongClick) {
                //implement this later
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
