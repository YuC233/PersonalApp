package com.example.personalapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.personalapp.R;
import com.example.personalapp.util.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "下载开始", Toast.LENGTH_SHORT).show();
        LogUtil.d("onCreate executed");
        NotificationChannel channel = null;
        //Android8.0要求设置通知渠道
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("DEFAULT", "foregroundName", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this)
                .setChannelId("DEFAULT")
                .setContentTitle("图片开始下载")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("onStartCommand()");
        DownloadTask downloadTask = new DownloadTask();
        //intent传过来的图片地址
        String url = intent.getStringExtra("url");
        //执行下载操作
        downloadTask.execute(url);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy开启");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Params表示用于AsyncTask执行任务的参数的类型
     * Progress表示在后台线程处理的过程中，可以阶段性地发布结果的数据类型
     * Result表示任务全部完成后所返回的数据类型
     */
    public class DownloadTask extends AsyncTask<String, Void, Void> {

        //在子线程中运行耗时任务，任务结束返回为空
        @Override
        protected Void doInBackground(String... strings) {
            final String url = strings[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("DownloadTask", "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    InputStream in = response.body().byteStream();
                    //解析网络资源
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    String dir = Environment.getDataDirectory() + "/data/com.example.personalapp/files/";
                    String name = url.substring(url.lastIndexOf("/"), url.length());
                    String path = dir + name;
                    File file = new File(path);
                    FileOutputStream out = new FileOutputStream(file);
                    //bitmap进行压缩
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    stopSelf();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
