package com.robynsilber.splitthebill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ChooseActionActivity extends AppCompatActivity {

    private Bill mBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        mBill = getIntent().getParcelableExtra("bill");
        updateBalanceOnUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_choose_action);
        mBill = getIntent().getParcelableExtra("bill");
        updateBalanceOnUI();
    }

    private void updateBalanceOnUI(){
        TextView textView = (TextView) findViewById(R.id.balance);
        textView.setText(mBill.getStringBalance());
    }

    public void deductAnAmount(View view) {
        Intent intent = new Intent(this, DeductAmountActivity.class);
        intent.putExtra("bill", mBill);
        startActivity(intent);
    }

    public void splitRemaining(View view) {
    }

    public void viewPeople(View view) {
    }

    public void cancelAndGoToMain(View view) {
        mBill.setToZero();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
