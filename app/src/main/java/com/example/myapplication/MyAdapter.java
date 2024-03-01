package com.example.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<homeContent> homeContents;

    public MyAdapter(Context context, List<homeContent> homeContent) {
        this.context = context;
        this.homeContents = homeContent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        homeContent data = homeContents.get(position);
        holder.plantname.setText(data.getPlantname());
//        holder.plantimage.setImageBitmap(BitmapFactory.decodeByteArray(data.getPlantimage() , 0 ,data.getPlantimage().length));
        holder.plantimage.setImageResource(homeContents.get(position).getPlantimage());

    }

    @Override
    public int getItemCount() {
        return homeContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView plantname;
        ImageView plantimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plantname = itemView.findViewById(R.id.plantname);
            plantimage = itemView.findViewById(R.id.plantimage);
        }
    }
}
