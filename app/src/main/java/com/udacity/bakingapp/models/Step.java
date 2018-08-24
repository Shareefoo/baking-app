package com.udacity.bakingapp.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Step {

    @SerializedName("id")
    public int id;

    @SerializedName("shortDescription")
    public String shortDescription;

    @SerializedName("description")
    public String description;

    @SerializedName("videoURL")
    public String videoURL;

    @SerializedName("thumbnailURL")
    public String thumbnailURL;

}