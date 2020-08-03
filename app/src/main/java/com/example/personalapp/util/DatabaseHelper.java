package com.example.personalapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //声明一个数据库
    static String name = "user.db";
    static int dbVersion = 1;

    //写一个这个类的构造函数，参数为上下文context，所谓上下文就是这个类所在包的路径
// 指明上下文，数据库名，工厂默认空值，版本号默认
    public DatabaseHelper(Context context) {

        super(context, name, null, dbVersion);
    }

    //继承SQLiteDatabase中的抽象方法，必须重写
    //数据库首先的创建onCreate方法，onUpgrade进行增删改查
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id integer primary key autoincrement," +
                "username varchar(20),password varchar(20),age integer,sex varchar(2))";
        String sql2 = "create table news(id integer primary key autoincrement," + "newsTitle varchar(100), newsUrl vachar(100),newsDate varchar(100),newsImgUrl varchar(100))";
        String sql3 = "create table picture(id integer primary key autoincrement," + "url varchar(100), des vachar(100))";

        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

}
