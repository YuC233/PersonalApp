package com.example.personalapp.util;

import okhttp3.OkHttpClient;

public class HttpUtils {

    private OkHttpClient client;

    public OkHttpClient getClient() {
        if (client == null) {
            synchronized (this) {
                client = new OkHttpClient();
            }
        }
        return client;
    }
}
