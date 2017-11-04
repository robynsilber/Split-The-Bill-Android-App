package com.robynsilber.splitthebill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DeductAmountActivity extends AppCompatActivity {

    private Bill mBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deduct_amount);
        mBill = getIntent().getParcelableExtra("bill");

    }

    public void addTaxToAmount(View view) {
    }

    public void goBackToChooseActionActivity(View view) {
    }

    public void deductAmountFromBalance(View view) {
    }
}
