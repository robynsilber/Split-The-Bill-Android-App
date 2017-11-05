package com.robynsilber.splitthebill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Bill mBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createBill(View view) {
        EditText balanceEditText = (EditText) findViewById(R.id.balance_EditText);
        EditText taxesEditText = (EditText) findViewById(R.id.taxes_EditText);

        String taxStr = taxesEditText.getText().toString();
        if(taxStr.isEmpty()){
            taxStr = "0.0";
        }

        // creates new instance of Bill
        mBill = new Bill(balanceEditText.getText().toString(), taxStr);

        // Before starting next Activity, check that balance is greater than zero
        if(mBill.getBalance() > 0.0){
            // start next Activity
            Intent intent = new Intent(this, ChooseActionActivity.class);
            intent.putExtra("bill", mBill);
            startActivity(intent);
        }else{
            /**
             * TODO: display an error message
             * **/
        }

    }


}
