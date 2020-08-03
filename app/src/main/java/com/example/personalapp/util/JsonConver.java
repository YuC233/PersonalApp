package com.example.personalapp.util;

import com.example.personalapp.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonConver {

    public static List<News> getNews(String jsonStr) {
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonStr);
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                News news = new News();
                news.setTitle(item.getString("title"));
                news.setAuthor(item.getString("author"));
                news.setTime(item.getString("publishedAt"));
                news.setId(item.getString("_id"));
                newsList.add(news);
            }
            return newsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
