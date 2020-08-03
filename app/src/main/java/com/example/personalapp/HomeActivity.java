package com.example.personalapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.personalapp.fragment.IndividualFragment;
import com.example.personalapp.fragment.NewsFragment;
import com.example.personalapp.fragment.PictureFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    //写一个List集合，把每个页面，也就是Fragment,存进去
    private List<Fragment> list = new ArrayList<>();
    private MyAdapter adapter;
    private String[] titles = {"新闻", "美图", "个人中心"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //实例化
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        //页面，数据源，里面是创建的三个页面（Fragment）
        list = new ArrayList<>();
        list.add(new NewsFragment());
        list.add(new PictureFragment());
        list.add(new IndividualFragment());
        //ViewPager的适配器，获得Fragment管理器
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        tabLayout.setupWithViewPager(viewPager);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }
        ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "未拥有相应权限", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                break;
        }
    }

    //创建Fragment的适配器
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);

        }

        //获得每个页面的下标
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        //获得List的大小
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}


