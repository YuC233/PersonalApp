package com.example.personalapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    String id;
    private String title;
    private String time;
    private String content;
    private String author;

    public News() {
    }

    protected News(Parcel in) {
        id = in.readString();
        title = in.readString();
        time = in.readString();
        content = in.readString();
        author = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(content);
        dest.writeString(author);
    }
}
