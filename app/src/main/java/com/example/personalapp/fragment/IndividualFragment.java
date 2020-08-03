package com.example.personalapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personalapp.R;
import com.example.personalapp.individual.NewsCollActivity;
import com.example.personalapp.individual.PicCollActivity;
import com.example.personalapp.individual.UserActivity;

public class IndividualFragment extends Fragment {
    private TextView mtvuser;
    private TextView mnewscoll;
    private TextView mpiccoll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //R.layout.fragment_finend就是碎片的布局文件
        return inflater.inflate(R.layout.fragment_individual, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtvuser = (TextView) view.findViewById(R.id.tv_user);
        mnewscoll = (TextView) view.findViewById(R.id.news_coll);
        mpiccoll = (TextView) view.findViewById(R.id.pic_coll);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //监听用户点击
        mtvuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), UserActivity.class);
                startActivity(intent1);
            }
        });
        mnewscoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), NewsCollActivity.class);
                startActivity(intent2);
            }
        });
        mpiccoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), PicCollActivity.class);
                startActivity(intent3);
            }
        });
    }

}
