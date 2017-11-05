package com.robynsilber.splitthebill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ViewPeopleActivity extends AppCompatActivity {

    private Bill mBill;
    private ListView mListView;
    private PersonAdapter mPersonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_people);
        mBill = getIntent().getParcelableExtra("bill");
        mListView = (ListView) findViewById(R.id.list_view);
        updateUI();
    }

    private void updateUI(){
        // construct PersonAdapter
        mPersonAdapter = new PersonAdapter(this, mBill.getPersonArrayList());
        mListView.setAdapter(mPersonAdapter);
    }



    public void createNewEmail(View view) {

        int idx = PersonAdapter.mDictionary.get(view);
        if(idx>=0 && idx<mBill.getPersonArrayList().size()){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String amt = formatDollarString(mBill.getPersonArrayList().get(idx).getAmount());
            String messageText = "Amount owed: $" + amt;
            intent.putExtra(Intent.EXTRA_SUBJECT, "Bill: Amount owed");
            intent.putExtra(Intent.EXTRA_TEXT, messageText);
            startActivity(intent);
        }else{
            Log.d("dictionary", "Problem with getting index");
        }


    }

    public void goBackToChooseActionFromViewPeople(View view) {
        Intent intent = new Intent(this, ChooseActionActivity.class);
        intent.putExtra("bill", mBill);
        startActivity(intent);
    }

    public static String formatDollarString(double amount){
        if(amount == 0.0){
            return "0.00";
        }

        String str = Double.toString(amount);
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
}
