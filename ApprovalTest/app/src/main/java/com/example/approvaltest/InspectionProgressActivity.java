package com.example.approvaltest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Models.AuxillaryCondition;
import Models.ConAux;
import Models.ConditionInspection;
import Models.Inspection;

public class InspectionProgressActivity extends Activity {
	
int CurrentInspection;	
String UsedPlants;
String UsedPermissions;
ArrayList<Inspection> mylist;
ArrayList<Inspection> mylist1;
ArrayList<AuxillaryCondition> myauxlist;
String Dateiname = "myinspection";
String AuxDateiname = "myaux";
Inspection myselectedinspecs;
ListView auxList;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspection_progress);
		
		
		Intent in = getIntent();
		Bundle bndl = in.getExtras();
		CurrentInspection = bndl.getInt("CurrentInspection");
		UsedPermissions = bndl.getString("UsedPermissions");
		UsedPlants = bndl.getString("UsedPlants");
		
		mylist1 = new FileManager(getActivity()).GetInspects(Dateiname);
		myauxlist = new FileManager(getActivity()).GetAux(AuxDateiname);
		mylist=new FileCorrecter(getActivity(), mylist1).correctthelist();
		
		TextView theplants = (TextView)findViewById(R.id.tvaipusedplantstext);
		TextView thepermissions = (TextView)findViewById(R.id.tvaipusedpermissionstext);
		
		theplants.setText(UsedPlants);
		thepermissions.setText(UsedPermissions);
		//Get the List 
		//getthelist();		
			

		for (Inspection inspecs : mylist) {
			
			if (inspecs.getId() == CurrentInspection) {				
				myselectedinspecs=inspecs;
			}	
		}
		
		String NameofInspec = myselectedinspecs.getRelatedSeries().getName();
		TextView tvinspec = (TextView)findViewById(R.id.tvaip1);
		tvinspec.setText("Prüfung: " + NameofInspec ); 		
		
		auxList = (ListView)findViewById(R.id.list_auxillary2);
		auxList.setAdapter(null);				
		AuxillaryAdapter2 aa = new AuxillaryAdapter2(getActivity(), R.layout.list_auxlliary_row2 ,FilterAuxListWithID(myselectedinspecs));
		auxList.setAdapter(aa);
			
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
		
	private Context getActivity() {
		// TODO Auto-generated method stub
		return InspectionProgressActivity.this;
	}

	/*private void getthelist() {
		JsonElement jelement = new JsonParser().parse(CurrentInspection);
		JsonObject myobj = jelement.getAsJsonObject();
		Inspection object;
		try {
			object = mymapper.readValue(myobj.toString(), Inspection.class);
			mylist.add(object);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspection_progress, menu);
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
	
	public void Endtheinspecs(View v)
	{	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 
		builder.setTitle("Prüfung Beenden");
		builder.setMessage("Möchten Sie die Prüfung wirklich beenden ?");
		 
		 
		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
		 
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		        
			   			   
			   for (Inspection insp : mylist) {
					
			        if(insp.getId()==CurrentInspection)
			        {
			           insp.setProgress(2);           
			           
			           new FileManager(getApplication()).SaveInspects(mylist, Dateiname);
			        }	
			        	
					}   
			   
			   
		        Intent in = new Intent(getActivity(), MainActivity.class);
		        startActivity(in);
		         
		        
		        dialog.dismiss();
		        finish();
		   }
		 
		});
		 
		builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
			 
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			 
			        // Code that is executed when clicking NO
			 
			        dialog.dismiss();
			   }
			 
			});
			 
			 
			AlertDialog alert = builder.create();
			alert.show();
		
		
	}
}
