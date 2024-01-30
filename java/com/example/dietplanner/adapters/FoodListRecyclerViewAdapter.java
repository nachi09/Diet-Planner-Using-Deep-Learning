package com.example.dietplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietplanner.R;
import com.example.dietplanner.models.FoodItemModel;

import java.util.ArrayList;

public class FoodListRecyclerViewAdapter extends RecyclerView.Adapter<FoodListRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<FoodItemModel> foodItemModelArrayList;

    public FoodListRecyclerViewAdapter (Context context, ArrayList<FoodItemModel> foodItemModelArrayList) {
    this.context=context;
    this.foodItemModelArrayList =foodItemModelArrayList;
    }

    @NonNull
    @Override
    public FoodListRecyclerViewAdapter.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_food_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull FoodListRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.tvName.setText(foodItemModelArrayList.get(position).getName());
        holder.tvCalories.setText(foodItemModelArrayList.get(position).getCalories());
        holder.tvQuantity.setText(foodItemModelArrayList.get(position).getQuantity());

    }

    @Override
    public int getItemCount () {
        return foodItemModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvCalories,tvQuantity;
        public MyViewHolder (@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_food_name);
            tvCalories=itemView.findViewById(R.id.tv_food_calories);
            tvQuantity=itemView.findViewById(R.id.tv_food_quantity);
        }
    }
}
