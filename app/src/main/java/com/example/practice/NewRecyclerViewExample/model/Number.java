package com.example.practice.NewRecyclerViewExample.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Number implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    private final static long serialVersionUID = -584680528378363684L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Number() {
    }

    /**
     *
     * @param name
     * @param id
     */
    public Number(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Number{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}