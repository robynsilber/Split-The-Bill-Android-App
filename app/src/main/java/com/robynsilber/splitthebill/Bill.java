package com.robynsilber.splitthebill;


public class Bill {

    private double mBalance = 0.0;
    private final double mtaxPercent;

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

            mBalance = beforeTaxBalance + taxes;
            mtaxPercent = taxes/beforeTaxBalance;
        }else{
            mtaxPercent = 0.0;
        }
    }

    public double getBalance(){
        return mBalance;
    }

    public void deductFromBalance(double amount){
        if(amount <= mBalance){
            mBalance -= amount;
        }
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

}
