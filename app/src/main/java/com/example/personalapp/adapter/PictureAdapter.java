package com.example.personalapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.personalapp.R;
import com.example.personalapp.entity.Picture;
import com.example.personalapp.service.DownloadService;
import com.example.personalapp.util.UserUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder>{
    private List<Picture> mlist;
    private View inflater;
    private Context mContext;

    public PictureAdapter(Context context, List<Picture> pictureList){
        this.mlist=pictureList;
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.picture_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String url = mlist.get(position).getUrl();
        Glide.with(mContext)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())   //过渡动画，防止图片变形
                .into(viewHolder.picBeauty);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //出现弹窗
                showDialog(mlist.get(position));
            }
        });

    }

    private void showDialog(final Picture picture) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.picture_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();

        ImageView iv_coll_img = view.findViewById(R.id.iv_coll_img);
        Glide.with(mContext).load(picture.getUrl()).into(iv_coll_img);
        Button bt_coll_img = view.findViewById(R.id.bt_coll_img);
        Button bt_down_img = view.findViewById(R.id.bt_down_img);
        bt_coll_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=mContext.getSharedPreferences("pic_collect",Context.MODE_PRIVATE);
                Set<String> set = sp.getStringSet("" + UserUtils.getUserId(), new HashSet<String>());
                Set<String> stringSet=new HashSet<>();
                stringSet.add(picture.getUrl());
                set.addAll(stringSet);
                sp.edit().putStringSet(""+ UserUtils.getUserId(),set).apply();
                Toast.makeText(mContext,"收藏成功",Toast.LENGTH_SHORT).show();
            }
        });
        iv_coll_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bt_down_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DownloadService.class);
                intent.putExtra("url",picture.getUrl());
                mContext.startService(intent);
            }
        });
        dialog.show();
        dialog.getWindow().setLayout((mContext.getResources().getDisplayMetrics().widthPixels/4*3), (mContext.getResources().getDisplayMetrics().heightPixels/4*3));
    }

    @Override
    public int getItemCount() {
        if (mlist == null)
            return 0;
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView picBeauty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picBeauty=itemView.findViewById(R.id.pic_Beauty);
        }
    }
}


