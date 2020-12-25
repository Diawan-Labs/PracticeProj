package com.example.practice.NewRecyclerViewExample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfNumbers implements Serializable
{

    @SerializedName("numbers")
    @Expose
    private List<Number> numbers = null;
    private final static long serialVersionUID = -1271048405794705591L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ListOfNumbers() {
    }

    /**
     *
     * @param numbers
     */
    public ListOfNumbers(List<Number> numbers) {
        super();
        this.numbers = numbers;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
    }
    @Override
    public String toString() {
        return "ListOfNumbers{" +
                "numberArrayList=" + numbers +
                '}';
    }
}
