package com.robynsilber.splitthebill;

/**
 * Created by robynjsilber on 11/4/17.
 */

public class Bill {

    private double mBalance = 0.0;
    private double mtaxPercent = 0.0;

    public Bill(double beforeTaxBalance, double taxes){
        mBalance = beforeTaxBalance + taxes;
        mtaxPercent = taxes/beforeTaxBalance;
    }

    public double getBalance(){
        return mBalance;
    }

    public void deductFromBalance(double amount){
        if(amount <= mBalance){
            mBalance -= amount;
        }
    }

}
