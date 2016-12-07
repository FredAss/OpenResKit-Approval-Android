package com.example.approvaltest;

import java.util.Calendar;

import Models.MyDate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	MyDate mydate;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
	
	
	@Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

			
		mydate = (MyDate) new DataHandler().getObjectFromFile(DataHandler.MYDATE_FILENAME, getActivity());
		
		if (mydate==null){			
			mydate = new MyDate();			
		}
		
		
        String a = String.valueOf(day);
        String b = String.valueOf(month + 1);
        String c = String.valueOf(year);

        View id = getActivity().findViewById(R.id.datetimepicker);
        EditText dateValue = (EditText) id;
        dateValue.setText("Erledigung bis zum: " + a + "." + b + "." + c);
        
        String myhubduedate = c +"-"+ b + "-" + a + " 12:00:00:000";
        mydate.setMyDate(myhubduedate);
        new DataHandler().saveFileWObject(mydate, DataHandler.MYDATE_FILENAME , getActivity());

    }
	
}
