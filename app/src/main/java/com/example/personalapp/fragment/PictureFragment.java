package com.example.personalapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.personalapp.R;
import com.example.personalapp.adapter.PictureAdapter;
import com.example.personalapp.entity.Picture;
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


public class PictureFragment extends Fragment {

    private static final int GET_PIC = 1;
    private static final String API_URL = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/1000";

    RecyclerView recyclerView;
    private PictureAdapter pictureAdapter;
    private List<Picture> PicUrlList;
    private View mView;

    @SuppressLint("HandlerLeak")
    //用Handler通知UI刷新
            Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_PIC:
                    pictureAdapter.notifyDataSetChanged();
                    LogUtil.i("照片取出来了" + PicUrlList);//照片集合
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.from(getActivity()).inflate(R.layout.fragment_picture, container, false);
        //先加载数据，再显示View
        initData();
        initView();

        return mView;
    }


    protected void initData() {
        String[] s = new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), s, 1);
        }
        PicUrlList = new ArrayList<>();
        getBeauty();

    }

    protected void initView() {
        recyclerView = mView.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        pictureAdapter = new PictureAdapter(getActivity(), PicUrlList);
        recyclerView.setAdapter(pictureAdapter);
    }


    //    protected void initEvent() {
//    }
    private void getBeauty() {
        HttpUtils httpUtils = new HttpUtils();
        Request request = new Request.Builder()
                .url(API_URL)
                .removeHeader("User-Agent")
                .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();
        httpUtils.getClient().newCall(request).enqueue(new Callback() {
            //请求失败的回调方法
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.i("找不到图啦，，，");//
            }

            //请求成功的回调方法
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject object = new JSONObject(data);
                    JSONArray dataArray = object.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject item = dataArray.getJSONObject(i);

                        Picture picture = new Picture();

                        picture.setUrl(item.getString("url"));
                        picture.setDesc(item.getString("desc"));
                        picture.set_id(item.getString("_id"));

                        PicUrlList.add(picture);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                LogUtil.i("照片的集合" + PicUrlList);
//                response.body().close();

                mHandler.sendEmptyMessage(GET_PIC);
            }
        });
    }
}