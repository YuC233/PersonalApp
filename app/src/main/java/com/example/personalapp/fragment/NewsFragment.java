package com.example.personalapp.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personalapp.NewsInfoActivity;
import com.example.personalapp.R;
import com.example.personalapp.adapter.NewsAdapter;
import com.example.personalapp.entity.NewsData;
import com.example.personalapp.util.DatabaseHelper;
import com.example.personalapp.util.HttpUtils;
import com.example.personalapp.util.LogUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {

    public static final String URL = "http://v.juhe.cn/toutiao/index?type=top&key=2d960b1653ffda7dfafa77e2d4b67208";

    private ListView listView;
    private Button mClick;
    // 新闻集合对象
    private List<NewsData> datas = new ArrayList<NewsData>();
    //定义Adapter对象
    private NewsAdapter adapter;
    private View mView;
    //RecyclerView
    private int GET_PIC1 = 10;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.from(getActivity()).inflate(R.layout.fragment_news, container, false);

        initView();
        initData();
        return mView;

    }

    private void initView() {
        listView = mView.findViewById(R.id.listview);
    }

    private void initData() {
        if (null != datas) {
            datas.clear();
        } else {
            datas = new ArrayList<>();
        }
        getDatas(URL);
        /**
         * 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)
         */
        adapter = new NewsAdapter(getActivity(), datas);
        listView.setAdapter(adapter);
//监听点击Item事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getActivity(), NewsInfoActivity.class);
                intent.putExtra("news", datas.get(position));
                getActivity().startActivity(intent);

            }
        });

    }


    private void getDatas(String url) {
        HttpUtils httpUtils = new HttpUtils();
        Request request = new Request.Builder()
                .url(URL)
                .removeHeader("User-Agent")
                .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();
        httpUtils.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.i("请求失败");
            }

            //请求成功的回调
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String newsdata = response.body().string();
                try {
                    JSONObject object = new JSONObject(newsdata);
                    JSONObject result = object.getJSONObject("result");
                    JSONArray newsArray = result.getJSONArray("data");
                    for (int i = 0; i < newsArray.length(); i++) {
                        JSONObject item = newsArray.getJSONObject(i);
                        NewsData newsData = new NewsData();
                        newsData.setId(item.getString("uniquekey"));
                        newsData.setNewsTitle(item.getString("title"));
                        newsData.setNewsDate(item.getString("date"));
                        newsData.setNewsImgUrl(item.getString("thumbnail_pic_s"));
                        newsData.setNewsUrl(item.getString("url"));
                        datas.add(newsData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //在主线程里面调用notifyDataSetChanged进行列表刷新，并滚动回到顶部
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        Log.i("新闻解析成功", "run: " + datas);
                    }
                });
            }
        });

    }
}
