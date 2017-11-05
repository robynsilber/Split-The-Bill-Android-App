package com.robynsilber.splitthebill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

public class SplitBalanceActivity extends AppCompatActivity {

    private Bill mBill;
    NumberPicker mNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_balance);
        mBill = getIntent().getParcelableExtra("bill");
        initializeNumberPicker();
    }

    public void initializeNumberPicker(){
        mNumberPicker = (NumberPicker) findViewById(R.id.number_picker);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(100);
    }

    private void goBackToChooseActionActivity(){
        Intent intent = new Intent(this, ChooseActionActivity.class);
        intent.putExtra("bill", mBill);
        startActivity(intent);
    }

    public void cancelAndGoBackToChooseAction(View view) {
        goBackToChooseActionActivity();
    }

    public void splitTheBalance(View view) {
        int num = mNumberPicker.getValue();
        double amt = mBill.getBalance() / ((double) num);
        double rAmt = Bill.roundToTwoDecimalPlaces(amt);
        int diff = (int) ((mBill.getBalance() - (rAmt * num)) * 100);//num ppl who owe extra cent

        /**
         * TODO: check to see if Math.round could result in rounding up
         * **/
        if(diff == 0){ // even split
            mBill.addPersonToList(new Person(rAmt, num));
            mBill.deductFromBalance(rAmt * num);
        }else if(diff > 0){
            mBill.addPersonToList(new Person(rAmt + 0.01, diff));
            mBill.deductFromBalance((rAmt+0.01)*diff);
            mBill.addPersonToList(new Person(rAmt, (num-diff)));
            mBill.deductFromBalance(rAmt * (num-diff));
        }else{
            Log.d("splitTheBalance", "diff is less than 0");
            throw new Error("splitTheBalance: diff is less than 0");
            /**
             * TODO: handle this
             * **/
        }


        Log.d("splitTheBalance", "balance = " + mBill.getBalance());

        if(mBill.getBalance() == 0.0){
            goBackToChooseActionActivity();
        }else{
            throw new Error("Bill was not properly split");
        }

    }
}
