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

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    private List<FavContent> favContents;

    public FavAdapter (Context context,List<FavContent> favContents) {
        this.context = context;
        this.favContents = favContents;
    }
    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        FavContent data = favContents.get(position);
        holder.p_name.setText(data.getP_name());
        holder.p_image.setImageBitmap(BitmapFactory.decodeByteArray(data.getP_image() , 0 ,data.getP_image().length));
    }

    @Override
    public int getItemCount() {
        return favContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView p_image;
        TextView p_name;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p_image = itemView.findViewById(R.id.plantimage1);
            p_name = itemView.findViewById(R.id.plantname2);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
