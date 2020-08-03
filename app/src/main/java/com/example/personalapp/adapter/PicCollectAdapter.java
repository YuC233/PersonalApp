package com.example.personalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personalapp.R;

import java.util.List;

public class PicCollectAdapter extends RecyclerView.Adapter<PicCollectAdapter.MyViewHolder> {
    private Context context;
    private List<String> urls;

    public PicCollectAdapter(Context context, List<String> url){
        this.context=context;
        this.urls=url;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.picture_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(urls.get(position)).into(holder.pic_Beauty);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic_Beauty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pic_Beauty=itemView.findViewById(R.id.pic_Beauty);
        }
    }
}
