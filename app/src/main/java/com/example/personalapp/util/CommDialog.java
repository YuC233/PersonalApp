package com.example.personalapp.util;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CommDialog extends Dialog implements View.OnClickListener {

    private Context context;//上下文
    private int layoutResID;//布局文件id
    private int[] listenedItem;//监听的控件id

    public CommDialog(Context context, int layoutResID, int[] listenedItem) {
        super(context);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        setContentView(layoutResID);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5;// 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);//点击外部Dialog消失
        //遍历控件id添加点击注册
        for (int id : listenedItem) {
            findViewById(id).setOnClickListener(this);
        }
    }

    OnClickListener listener;

    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void OnItemClick(CommDialog dialog, View view);
    }


    @Override
    public void onClick(View v) {
        dismiss();
        listener.OnItemClick(this, v);
    }

}