package com.example.practice.ExoplayerExample.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MyVideosList implements Serializable
{

    @SerializedName("videos")
    @Expose
    private List<Video> videos = new ArrayList<Video>();
    private final static long serialVersionUID = -297106130359720338L;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(videos).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MyVideosList) == false) {
            return false;
        }
        MyVideosList rhs = ((MyVideosList) other);
        return new EqualsBuilder().append(videos, rhs.videos).isEquals();
    }

}