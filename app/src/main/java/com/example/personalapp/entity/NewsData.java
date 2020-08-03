package com.example.personalapp.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class NewsData implements Parcelable {
    private String id;
    private String newsTitle;
    private String newsDate;
    private String newsImgUrl;  //图片URL
    private String newsUrl;  //新闻内容URL

    public NewsData() {

    }


    protected NewsData(Parcel in) {
        id = in.readString();
        newsTitle = in.readString();
        newsDate = in.readString();
        newsImgUrl = in.readString();
        newsUrl = in.readString();
    }

    public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel in) {
            return new NewsData(in);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsImgUrl() {
        return newsImgUrl;
    }

    public void setNewsImgUrl(String newsImgUrl) {
        this.newsImgUrl = newsImgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "id=" + id +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsDate='" + newsDate + '\'' +
                ", newsImgUrl='" + newsImgUrl + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(newsTitle);
        dest.writeString(newsDate);
        dest.writeString(newsImgUrl);
        dest.writeString(newsUrl);
    }
}

