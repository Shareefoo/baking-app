package com.udacity.bakingapp.models;

import org.parceler.Parcel;

@Parcel
public class Ingredient {

    public double quantity;
    public String measure;
    public String name;

    public Ingredient() {
    }

    public Ingredient(double quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    //    public double getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(double quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getMeasure() {
//        return measure;
//    }
//
//    public void setMeasure(String measure) {
//        this.measure = measure;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

}
