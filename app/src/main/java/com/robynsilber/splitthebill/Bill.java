package com.robynsilber.splitthebill;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bill implements Parcelable {

    private double mBalance = 0.0;
    private double mTaxPercent = 0.0;
    private ArrayList<Person> mPersonArrayList;

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };


    public Bill(String strBeforeTaxBalance, String strTaxes){

        // Check if input contains dollar signs; if so, remove them
        if(containsDollarSign(strBeforeTaxBalance)){
            strBeforeTaxBalance = strBeforeTaxBalance.substring(1); // removes dollar sign
        }
        if(containsDollarSign(strTaxes)){
            strTaxes = strTaxes.substring(1); // removes dollar sign
        }

        // Check if inputs are valid dollar quantities
        if(isPriceValid(strBeforeTaxBalance) && isPriceValid(strTaxes)){
            double beforeTaxBalance = Double.parseDouble(strBeforeTaxBalance);
            double taxes = Double.parseDouble(strTaxes);

            mBalance = 0.0 + beforeTaxBalance + taxes;
            mTaxPercent = 0.0 + roundToTwoDecimalPlaces(taxes/beforeTaxBalance);
            mPersonArrayList = new ArrayList<Person>();
        }
    }


    protected Bill(Parcel in) {
        mBalance = in.readDouble();
        mTaxPercent = in.readDouble();
        mPersonArrayList = new ArrayList<Person>();
        in.readTypedList(mPersonArrayList, Person.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mBalance);
        parcel.writeDouble(mTaxPercent);
        parcel.writeTypedList(mPersonArrayList);
    }



    public double getBalance(){
        return mBalance;
    }

    // deducts a specific amount from the balance
    public void deductFromBalance(double amount){
        if(amount <= mBalance){
            amount = roundToTwoDecimalPlaces(amount);
            mBalance -= amount;
        }
    }

    public double getTaxPercent(){
        return mTaxPercent;
    }

    // resets the members of Bill
    public void setToZero(){
        mBalance = 0.0;
        mTaxPercent = 0.0;
        mPersonArrayList = new ArrayList<Person>();
    }

    public String getStringBalance(){
        if(mBalance == 0.0){
            return "0.00";
        }

        String str = Double.toString(mBalance);
        int idx = str.indexOf('.');
        if(idx == -1){
            str += ".00";
        }else if(idx == str.length() - 1){
            str += "00";
        }else if(idx == str.length() - 2){
            str += "0";
        }else if(idx < str.length() - 3){
            str = str.substring(0, idx+3);
        }

        return str;
    }

    public void addPersonToList(Person person){
        mPersonArrayList.add(person);
    }

    public ArrayList<Person> getPersonArrayList(){
        return mPersonArrayList;
    }



    /* Checks if user input to edit text contains dollar symbol
     * Returns true if contains dollar symbol; otherwise false */
    public static boolean containsDollarSign(String userInput){
        if(userInput.length() > 0){
            if(userInput.charAt(0) == '$') return true;
        }
        return false;
    }

    /* Checks if user input to edit text contains a valid dollar quantity:
     * only numerical digits and no more than one decimal point, and
     * no more than two digits to the right of the decimal point */
    public static boolean isPriceValid(String userInput){
        int count = 0;
        boolean decimalFound = false;
        for(int i=0; i<userInput.length(); i++){
            if(userInput.charAt(i)>=48 && userInput.charAt(i)<=57){
                if(decimalFound){
                    count++;
                    if(count > 2) return false; // more than two digits to the right of decimal
                }
            }else if(userInput.charAt(i)==46){
                if(decimalFound) return false; // multiple decimal points
                else{
                    decimalFound = true;
                }
            }
        }
        return true;
    }

    // rounds a double to two decimal places
    public static double roundToTwoDecimalPlaces(double n){
        n = Math.round(n * 100.0);
        n = n/100.0;
        return n;
    }


    @Override
    public int describeContents() {
        return 0;
    }


}
