package server.hawker.com.foodshopserver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import server.hawker.com.foodshopserver.Adapter.ViewHolder.MenuViewHolder;
import server.hawker.com.foodshopserver.Interface.ItemClickListener;
import server.hawker.com.foodshopserver.Model.Category;
import server.hawker.com.foodshopserver.R;
import server.hawker.com.foodshopserver.UpdateCategoryActivity;
import server.hawker.com.foodshopserver.Utils.Common;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder>{

    Context context;
    List<Category> categoryList;

    public MenuAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,parent,false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        Picasso.with(context)
                .load(categoryList.get(position).Link)
                .into(holder.img_product);

        holder.txt_product.setText(categoryList.get(position).Name);

        //implement item click
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view) {
                //Assign this category to variable global
                Common.currentCategory = categoryList.get(position);
                //start new activity
                context.startActivity(new Intent(context, UpdateCategoryActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
