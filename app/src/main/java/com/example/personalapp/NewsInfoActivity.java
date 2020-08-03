package com.example.personalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalapp.entity.NewsData;
import com.example.personalapp.util.DatabaseHelper;
import com.example.personalapp.util.LogUtil;
import com.example.personalapp.util.UserUtils;

import java.util.HashSet;
import java.util.Set;

import static android.os.Build.VERSION_CODES;

@RequiresApi(api = VERSION_CODES.LOLLIPOP)
public class NewsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webviewinfo;
    private TextView titleTv;
    private Button collectBtn;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private NewsData newsData;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Set<String> listtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        initView();
        initData();
        //初始化View

    }

    private void initView() {

        webviewinfo = (WebView) this.findViewById(R.id.wv_info);

        //获取传递过来的数据
        Intent intent = this.getIntent();

        newsData = intent.getParcelableExtra("news");
        LogUtil.i("得到新闻" + newsData.toString());

        titleTv = findViewById(R.id.title_tv);
        collectBtn = findViewById(R.id.collect_btn);
        collectBtn.setOnClickListener(this);

        //WebView无法实现的问题
        webviewinfo.setWebChromeClient(new WebChromeClient());
        webviewinfo.setWebViewClient(new WebViewClient());
        webviewinfo.getSettings().setJavaScriptEnabled(true);


//    //加载网络图片
//    private void getImage(NewsInfoActivity newsInfoActivity, String newsImgUrl, ImageView imageinfo) {
//

    }

    private void initData() {
        sp = getSharedPreferences("news_collect", MODE_PRIVATE);
        //简单复制

        newsData = getIntent().getParcelableExtra("news");
        if (newsData != null) {
            titleTv.setText(newsData.getNewsTitle());
            //authorTv.setText(newsData.get());
        }
        Log.e("data", "initData: " + newsData.toString());

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        //查询数据库中用户id所对应的文章id（用户文章关系表）
        Log.e("NewsInfo", "initData: " + UserUtils.getUserId());
        listtitle = sp.getStringSet("" + UserUtils.getUserId(), new HashSet<String>());
        //是否存在，存在，设置按钮隐藏
        //存在
        //Log.e("News111", "initData: "+listId.toString());
        if (listtitle.contains(newsData.getNewsTitle())) {
            collectBtn.setVisibility(View.GONE);
        }
        webviewinfo.loadUrl(newsData.getNewsUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_btn:
                //进行加入收藏
                //进行数据库新增用户id和文章id；
                //用户id：userId:UserUtils.getUserId,文章id：news.getId;
                editor = sp.edit();
                Set<String> stringSet = new HashSet<>();
                stringSet.add(newsData.getNewsTitle());
                //文章集合
                listtitle.addAll(stringSet);
                editor.putStringSet(UserUtils.getUserId() + "", listtitle);
                editor.apply();
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                collectBtn.setVisibility(View.GONE);
                break;
        }
    }
}




