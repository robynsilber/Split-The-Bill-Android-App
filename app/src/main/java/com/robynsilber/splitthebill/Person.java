package com.robynsilber.splitthebill;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Person implements Parcelable, Serializable {
    private static int mNum = 1;
    private String mName;
    private double mAmount = 0.0;

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public Person(String name, double amount){
        mName = name;
        mAmount = amount;
        mNum++;
    }

    public Person(double amount){
        mName = "Person" + Integer.toString(mNum);
        mAmount = amount;
        mNum++;
    }

    public Person(double amount, int numPeople){
        mName = "Person";
        if(numPeople == 1){
            mName += " " + Integer.toString(mNum);
        }else{
            mName += "s " + Integer.toString(mNum) + "-" + Integer.toString(mNum+numPeople-1);
        }
        mAmount = amount;
        mNum += numPeople;
    }

    protected Person(Parcel in) {
        mName = in.readString();
        mAmount = in.readDouble();
    }



    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public double getAmount(){
        return mAmount;
    }

    public void resetAmount(){
        mAmount = 0.0;
    }

    public void addAmount(double amount){
        mAmount += amount;
    }

    public void subtractAmount(double amount){
        mAmount -= amount;
    }

    public void setAmount(double amount){
        mAmount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeDouble(mAmount);
    }
}
