package com.robynsilber.splitthebill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class DeductAmountActivity extends AppCompatActivity {

    private Bill mBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deduct_amount);
        mBill = getIntent().getParcelableExtra("bill");
    }


    private void goBackToChooseActionActivity(){
        Intent intent = new Intent(this, ChooseActionActivity.class);
        intent.putExtra("bill", mBill);
        startActivity(intent);
    }

    public void goBackToChooseActionActivity(View view) {
        goBackToChooseActionActivity();
    }


    public void deductAmountFromBalance(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.add_tax_checkbox);
        EditText editTextName = (EditText) findViewById(R.id.enter_name_here_edittext);
        EditText editTextAmt = (EditText) findViewById(R.id.amount_to_deduct_edittext);

        if(editTextAmt.getText().toString().length() > 0){

            Person person;
            double amt = Double.parseDouble(editTextAmt.getText().toString());

            if(amt <= mBill.getBalance()){

                if(checkBox.isChecked()){
                    double tax = amt * mBill.getTaxPercent();

                    if(amt + tax <= mBill.getBalance()){
                        amt += tax;
                    }else{ // amt+tax > balance
                        amt = mBill.getBalance(); // set amt to bal
                    }
                }

                if(editTextName.getText().toString().length() > 0){

                    person = new Person(editTextName.getText().toString(), amt);
                }else{
                    person = new Person(amt);
                }

                mBill.deductFromBalance(amt);
                mBill.addPersonToList(person);

                goBackToChooseActionActivity();

            }else{
                /**
                 * TODO: add error message (amt exceeds bill bal)
                 * */
            }



        }else{

            /**
             * TODO: display error message.
             * */

        }
    }


    public void checkedBoxToAddTax(View view) {

    }
}
