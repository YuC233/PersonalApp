package com.example.personalapp.individual;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.personalapp.R;
import com.example.personalapp.adapter.PicCollectAdapter;
import com.example.personalapp.util.UserUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PicCollActivity extends AppCompatActivity {
    private RecyclerView collectPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_coll);

        collectPic = findViewById(R.id.collect_pic);
        SharedPreferences sp = getSharedPreferences("pic_collect", MODE_PRIVATE);
        Set<String> set = sp.getStringSet("" + UserUtils.getUserId(), new HashSet<String>());
        List<String> list = new ArrayList<>(set);

        PicCollectAdapter adapter = new PicCollectAdapter(this, list);
        collectPic.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        collectPic.setAdapter(adapter);
    }
}