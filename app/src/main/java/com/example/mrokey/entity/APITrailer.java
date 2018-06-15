package com.example.mrokey.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APITrailer {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quicktime")
    @Expose
    private List<Object> quicktime = null;
    @SerializedName("youtube")
    @Expose
    private List<APIYoutube> youtube = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getQuicktime() {
        return quicktime;
    }

    public void setQuicktime(List<Object> quicktime) {
        this.quicktime = quicktime;
    }

    public List<APIYoutube> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<APIYoutube> youtube) {
        this.youtube = youtube;
    }
}
