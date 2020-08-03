package com.example.personalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalapp.entity.User;
import com.example.personalapp.util.UserService;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private RadioGroup sex;
    private Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String sexstr = ((RadioButton) RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
//trim()去除字符串前后的空格
                Log.i("TAG", name + "_" + pass + "_" + "_" + sexstr);
                UserService uService = new UserService(RegisterActivity.this);
                //声明一个user对象，调用set方法
                User user = new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setSex(sexstr);
                uService.register(user);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        username = (EditText) findViewById(R.id.usernameRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        sex = (RadioGroup) findViewById(R.id.sexRegister);
        register = (Button) findViewById(R.id.Register);
    }

}