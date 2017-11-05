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

        double total = 0.0;

        if(amt == rAmt){
            Person person = new Person(rAmt, num);
            mBill.addPersonToList(person);
            total = rAmt * num;
//            mBill.deductFromBalance(rAmt * num);
        }else if(amt > rAmt){
            double d = mBill.getBalance() - (num * rAmt);
            double temp = (d * 100);
            int oweExtraCent = (int) temp;

            Person person1 = new Person(rAmt+0.01, oweExtraCent);
            mBill.addPersonToList(person1);
            total = (rAmt+0.01) * oweExtraCent;
//            mBill.deductFromBalance((rAmt+0.01)*oweExtraCent);

            Person person2 = new Person(rAmt, num-oweExtraCent);
            mBill.addPersonToList(person2);
            total += rAmt*(num-oweExtraCent);
//            mBill.deductFromBalance(rAmt*(num-oweExtraCent));

        }else{
            double d =(num * rAmt) - mBill.getBalance();
            double temp = (d * 100);
            int oweLessCent = (int) temp;

            Person person1 = new Person(rAmt-0.01, oweLessCent);
            mBill.addPersonToList(person1);
            total = (rAmt-0.01) * oweLessCent;
//            mBill.deductFromBalance((rAmt-0.01)*oweLessCent);

            Person person2 = new Person(rAmt, num-oweLessCent);
            mBill.addPersonToList(person2);
            total += rAmt * (num-oweLessCent);
//            mBill.deductFromBalance(rAmt*(num-oweLessCent));
        }

        mBill.deductFromBalance(total);
        Log.d("splitTheBalance", "total = " +total);
        Log.d("splitTheBalance", "balance = " + mBill.getBalance());

        if(mBill.getBalance() == 0.0){
            goBackToChooseActionActivity();
        }else{
            Log.d("splitTheBalance", "Error: balance not = 0.0");
            throw new Error("Bill was not properly split");
        }

    }
}
