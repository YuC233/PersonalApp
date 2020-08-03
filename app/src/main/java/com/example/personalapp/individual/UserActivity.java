package com.example.personalapp.individual;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalapp.R;
import com.example.personalapp.util.DatabaseHelper;
import com.example.personalapp.util.UserUtils;

public class UserActivity extends AppCompatActivity {

    private TextView tv_username, tv_sex, tv_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tv_username = findViewById(R.id.tv_username);
        tv_sex = findViewById(R.id.tv_sex);
        int userId = UserUtils.getUserId();
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from user where id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{userId + ""});
        //move.ToFirst()为True时
        if (cursor.moveToFirst()) {
            //获取索引值
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            tv_username.setText(username);
            tv_sex.setText(sex);
        }
    }
}