package com.robynsilber.splitthebill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    public void goBackToChooseActionFromViewPeople(View view) {
        Intent intent = new Intent(this, ChooseActionActivity.class);
        intent.putExtra("bill", mBill);
        startActivity(intent);
    }
}
