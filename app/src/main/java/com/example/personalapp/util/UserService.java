package com.example.personalapp.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personalapp.entity.User;


public class UserService {
    private DatabaseHelper dbHelper;

    public UserService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //登录数据库对比查找
    public boolean login(String username, String password) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from user where username=? and password=?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username, password});
        //move.ToFirst()为True时，有数据
        if (cursor.moveToFirst() == true) {
            int id = cursor.getInt(0);
            UserUtils.setUserId(id);
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean register(User user) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "insert into user(username,password,sex) values(?,?,?)";
        Object obj[] = {user.getUsername(), user.getPassword(), user.getSex()};
        sdb.execSQL(sql, obj);
        return true;


    }
}
