package com.robynsilber.splitthebill;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PersonAdapter extends BaseAdapter {

    private Context mContext;
    private Person[] mPersonArray;
    public static Map<Button, Integer> mDictionary;

    public PersonAdapter(Context context, ArrayList<Person> people){
        mContext = context;
        mPersonArray = new Person[people.size()];
        for(int i=0; i<people.size(); i++){
            mPersonArray[i] = people.get(i);
        }
        mDictionary = new HashMap<>();
    }

    /* Gets the size of the array */
    @Override
    public int getCount() {
        return mPersonArray.length;
    }

    /* Gets the item at index=position */
    @Override
    public Object getItem(int position) {
        return mPersonArray[position];
    }

    /* Allows for tagging items so that they can be easily referenced */
    @Override
    public long getItemId(int position) {
        return 0; // not implementing this at the moment
    }

    /* Method is called for each item in the list. This method is called when
     * each view is being prepared to be shown on the screen. */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the views that will be provided to the adapter.
        /* convertView is the object that we want to reuse. First time called:
         * convertView will be null. If null, create the view and set it up;
         * otherwise, reuse and reset the data. */

        // Declare ViewHolder. Used for efficient scrolling of views.
        ViewHolder holder;

        if(convertView == null){ // new view; create it using LayoutInflater
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_person, null);
            // null is a ViewGroup root, which we can leave out here (hence null)

            holder = new ViewHolder(); // instantiates ViewHolder
            holder.personName = (TextView) convertView.findViewById(R.id.person_name_textview);
            holder.amountOwed = (TextView) convertView.findViewById(R.id.person_amount_textview);
            holder.sendButton = (Button) convertView.findViewById(R.id.send_btn);

            // Sets a tag for the view that will be reused below
            convertView.setTag(holder);

        }else{ // holder already associated with a view
            // call getTag() to reuse
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the Person data
        Person person = mPersonArray[position];

        holder.personName.setText(person.getName());
        holder.amountOwed.setText(getStringBalance(person.getAmount()));

        if(mDictionary.containsKey(holder.sendButton)){
            Log.d("dictionary", "contains key already");
        }
        mDictionary.put(holder.sendButton, position);

        return convertView;
    }

    public static String getStringBalance(double amt){
        if(amt == 0.0){
            return "0.00";
        }

        String str = Double.toString(amt);
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

    /* ViewHolder helper class is defined to be the template for creating a
     * helper object that is associated with a view. It allows for reuse of
      * the same references to objects in a view. */
    public static class ViewHolder{
        TextView personName;
        TextView amountOwed;
        Button sendButton;
    }
}
