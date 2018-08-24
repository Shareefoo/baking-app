package com.udacity.bakingapp.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ingredient {

    @SerializedName("quantity")
    public double quantity;

    @SerializedName("measure")
    public String measure;

    @SerializedName("ingredient")
    public String ingredient;

}