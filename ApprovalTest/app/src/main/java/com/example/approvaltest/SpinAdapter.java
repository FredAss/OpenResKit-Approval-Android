/*package com.example.approvaltest;

import java.util.ArrayList;

import Models.ConditionInspection;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

	private final ArrayList<ConditionInspection> Con;
	private final int Number;
		
	public SpinAdapter(ArrayList<ConditionInspection> con, int number) {
		super();
		Con = con;
		Number = number;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Con.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Con.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View recycle, ViewGroup parent) {
        TextView text;
        if (recycle != null){
            // Re-use the recycled view here!
            text = (TextView) recycle;
        } else {
            // No recycled view, inflate the "original" from the platform:
            text = (TextView)getLayoutInflater().inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );
        }
        text.setTextColor(Color.BLACK);
        text.setText(Con.get(position).getMeasures());
        return text;
    }

}
*/