package com.example.personalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalapp.util.UserService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    private EditText username;
    private EditText password;
    private Button login;
    private Button register;

    //定义一个findViews(),找具体某一个xml下的具体 widget控件
    private void findViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                System.out.println(name);
                String pass = password.getText().toString();
                System.out.println(pass);
                System.out.println("****************************************");
                Log.i("TAG", name + "_" + pass);
//声明一个uService对象
                UserService uService = new UserService(LoginActivity.this);
                boolean flag = uService.login(name, pass);
                System.out.println("==============================================");
                if (flag) {
                    System.out.println("--------------获取成功-----------------------");
                    Log.i("TAG", "登录成功");
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                } else {
                    Log.i("TAG", "账号或者密码有误");
                    Toast.makeText(LoginActivity.this, "账号或者密码有误", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
