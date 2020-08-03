package com.example.personalapp.entity;

public class Picture {

    private String url;
    private String desc;
    private String _id;

    public Picture() {
    }

    public Picture(String url, String desc, String _id) {
        this.url = url;
        this.desc = desc;
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}


