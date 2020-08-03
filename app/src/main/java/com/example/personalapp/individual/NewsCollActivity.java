package com.example.personalapp.individual;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalapp.R;
import com.example.personalapp.util.UserUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewsCollActivity extends AppCompatActivity {

    private ListView collectLv;
    private SharedPreferences sp;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_coll);
        collectLv = findViewById(R.id.lv_news_coll);
        //tv=findViewById(R.id.new_item_coll);

        sp = getSharedPreferences("news_collect", MODE_PRIVATE);
        Set<String> stringStr = sp.getStringSet(UserUtils.getUserId() + "", new HashSet<String>());
        List<String> list = new ArrayList<>(stringStr);
        Log.e("NewsCollActivity", "onCreate: " + (list == null ? "isEmpty" : "not null"));
        collectLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }
}