package com.robynsilber.splitthebill;

public class Person {
    private static int mNum = 1;
    private String mName;
    private double mAmount = 0.0;

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
}
