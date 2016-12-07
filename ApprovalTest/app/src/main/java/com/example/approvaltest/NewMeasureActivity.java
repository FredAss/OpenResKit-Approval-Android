package com.example.approvaltest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Models.AuxillaryCondition;
import Models.ConAux;
import Models.ConditionInspection;
import Models.Inspection;
import Models.Measure;
import Models.Measuresarechanged;
import Models.MyDate;
import Models.ResponsibleSubject;

public class NewMeasureActivity extends Activity {
	
	ArrayList<Inspection> myinspection1;
	ArrayList<Inspection> myinspection;
	String Dateiname = "myinspection";
	String AuxDateiname = "myaux";
	String RepoDateiname = "myrepo";
	List<ResponsibleSubject> res;
	ArrayList<AuxillaryCondition>myauxlist;
	int measureid;
	MyDate mydate;
	int conn;
	Measuresarechanged mshc;
	Date finalentrydate;
	Date finalduedate;
	int numb;
	int auxNumb;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_measure);
				
	    DateTimeFiller();		
		
		EditText edt = (EditText)findViewById(R.id.edittext_write);			
		TextView headline = (TextView)findViewById(R.id.nmtv1);
		
	    Intent in = getIntent();
		Bundle bndl = in.getExtras();
		
		auxNumb = bndl.getInt("AuxilId");
		conn = bndl.getInt("ConId");
		numb= bndl.getInt("InspectNumb");
		
				
		myinspection1= new FileManager(getActivity()).GetInspects(Dateiname);
		myauxlist = new FileManager(getActivity()).GetAux(AuxDateiname);
		myinspection = new FileCorrecter(getActivity(), myinspection1).correctthelist();
			
		MeasureIDCreator();
		
		
		Inspection myusedinspection = null;

		for (Inspection ins : myinspection) {
		
			if(ins.getId()==numb)
			{
				myusedinspection=ins;
				break;
				
			}
			
			
		}
		
		
		
			
		
		for (ConAux conaux : FilterAuxListWithID(myusedinspection)) {		
		Integer idi = conaux.getAux().getId();
		
			if(idi==auxNumb)			
			headline.setText(conaux.getAux().getCondition());		
		}		
	
		res = new FileManager(getActivity()).GetRepo(RepoDateiname);
		Spinner myspin = (Spinner)findViewById(R.id.spinner);
		SpinAdapter2 spin = new SpinAdapter2(getActivity(), R.layout.spinner, res);
		myspin.setAdapter(spin);
		
		
		
	}

	private Context getActivity() {
		
		return NewMeasureActivity.this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_measure, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void DateTimeFiller()
	{
		//Today
		TextView currentdate = (TextView)findViewById(R.id.currentdate);
		Date dt = new Date();			
		/*int dd = today.getDay();
		int mm = today.getMonth();
		int yyyy = today.getYear();
		String day = String.valueOf(dd);  
		String month = String.valueOf(mm);  
		String year = String.valueOf(yyyy);  */
		SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy");
		df.setTimeZone(TimeZone.getDefault());		
		//String c_Date = String.valueOf(df);
		currentdate.setText("Aktuelles Datum: " + df.format(dt));
				
	}

	public void MeasureIDCreator(){
			
	try {
		//id Array
		int[] id = new int[0];
		int[] valueid = new int[1];		
		
		TextView measure_id = (TextView)findViewById(R.id.meausure_id);
		
		for (Inspection myinsp: myinspection) {
			
		List<ConditionInspection> conlist = myinsp.getConditionInspections();	
		
		for (ConditionInspection conditionInspection : conlist) {
			
		List<Measure> mymeasures = conditionInspection.getMeasures();	
		
		for (Measure measure : mymeasures) {
		
		int value = measure.getId();
		int length = valueid.length;
		valueid[length-1] = value;
		id = valueid.clone();
		valueid = new int[length+1];
		
		for (int i = 0; i < id.length; i++) {
			
			valueid[i]=id[i];
			
		}
			
		}
		}
		}
		
		measureid = 1;
		boolean ok = false;
		
		if(id.length==0)
		{
			ok = true;
		}
		
		
		do {
			
		for (int i = 0; i < id.length; i++) {
		
		if(measureid!=id[i]){		
			
		}	
		else{
		
		measureid++;
		break;
					
		}	
		
		if(id.length-1==i && measureid!=id[i]){
		
		ok = true;	
			
		}
		
		}	
			
			
		} while (ok==false);

		String val = String.valueOf(measureid);
		measure_id.setText("MessID: " + val);
	} catch (Exception e) {
		int val=1;
		TextView measure_id = (TextView)findViewById(R.id.meausure_id);
		measure_id.setText("MessID: " + val);
	}
	}
	
	private List<ConAux> FilterAuxListWithID(Inspection number)
		{
	    	List<ConAux> conAuxList = new ArrayList<ConAux>();
	    	
			for (ConditionInspection condition :number.getConditionInspections()) 
			{
				for (AuxillaryCondition auxiliaryCondition : myauxlist) {
					if(condition.getAuxiliaryConditionId() == auxiliaryCondition.getId())
						conAuxList.add(new ConAux(auxiliaryCondition, condition, number.getId()));
				}
			}
			
			return conAuxList;
		}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");   

}

	public void acceptmeasure(View v){
		
	EditText datetimepicker = (EditText)findViewById(R.id.datetimepicker);
	String datetimetext = datetimepicker.getText().toString();
	
	if(datetimetext.equals("")){
		
	Toast toast = Toast.makeText(getActivity(), "Bitte Erledigungsdatum auswählen", Toast.LENGTH_LONG); 	
	toast.show();	
	
	}
	else{

		//DueDate
		mydate = (MyDate) new DataHandler().getObjectFromFile(DataHandler.MYDATE_FILENAME, getActivity());	
		String duedate = mydate.getMyDate();
    	//ID
		int finalid = measureid;	
		//Description
		String finaldescription;
		EditText edt = (EditText)findViewById(R.id.edittext_write);
		finaldescription = edt.getText().toString();
		//Name
		String finalname = "Maßnahme" + finalid;
		//ConditionInspectionID
		int finalconditioninspectionid = conn;	
		//Priority
		int finalprority = 1;	
		//Progress
		int finalprogress= 1;
	    //EntryDate
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); 
		DateTime dt = new DateTime();
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int day = dt.getDayOfMonth();
		
		String entrydate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day)+" 12:00:00:000";
		try {
			finalentrydate = df.parse(entrydate);
			finalduedate = df.parse(duedate);
		} catch (ParseException e){ 
			e.printStackTrace();
		}
		//ResponsiblesubjectID
		
		//Spinner s = (Spinner)findViewById(R.id.spinner);
		//String nameofrepo = (String)((TextView)findViewById(R.id.spinner)).getText();
		ResponsibleSubject user = (ResponsibleSubject)((Spinner)findViewById(R.id.spinner)).getSelectedItem();
		int finalresponsibleid = user.getId();
		
				
		//fill Object
		Measure myobj = new Measure();
		myobj.setId(finalid);
		myobj.setPriority(finalprority);
		myobj.setProgress(finalprogress);
		myobj.setName(finalname);
		myobj.setConditionInspection(finalconditioninspectionid);
		myobj.setResponsibleSubject(finalresponsibleid);
		myobj.setDescription(finaldescription);
		myobj.setEntryDate(finalentrydate);	
		myobj.setDueDate(finalduedate);
		myobj.setInternalid(true);
		//Upload vorbereiten
        //mshc = (Measuresarechanged) new DataHandler().getObjectFromFile(DataHandler.HASCHANGED_FILENAME, getActivity());
		
		//if (mshc==null){
			
		//	mshc = new Measuresarechanged();
		
		//}
		
		//boolean haschaneges = true;
		//mshc.setHaschanged(haschaneges);
		//new DataHandler().saveFileWObject(mshc, DataHandler.HASCHANGED_FILENAME, getActivity());
		boolean stop = false;
		
		
		/*for (Inspection inspection : myinspection) 
		{
			if(inspection.getId() == numb)
			{
				List<ConditionInspection> conlist = inspection.getConditionInspections();
				for (ConditionInspection conditionInspection : conlist) 
				{
					if(conditionInspection.getId() == auxNumb)
					{
						conditionInspection.getMeasures().add(myobj);
						break;
					}
				}
				break;
			}
		}*/
		
		for (Inspection myinsp: myinspection) {
			if(stop==true)
			{break;}
			List<ConditionInspection> conlist = myinsp.getConditionInspections();	
			
			for (ConditionInspection conditionInspection : conlist) {
							
			List<Measure> mymeasures = conditionInspection.getMeasures();	
			mymeasures.add(myobj);
			stop=true;
			if(stop==true)
			{break;}}
		}
		
		new FileManager(getActivity()).DeleteDocument("myinspection.JSON");
		new FileManager(getActivity()).SaveInspects(myinspection, "myinspection");	
		
		
		//Prüfung angelegt
		Toast toast = Toast.makeText(getActivity(), "Maßnahme Angelegt", Toast.LENGTH_LONG); 	
		toast.show();		
		NewMeasureActivity.this.finish();
		
			
		}
		
	}	
	
	
}

	
	
	
	
	
	