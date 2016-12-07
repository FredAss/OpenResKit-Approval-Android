package com.example.approvaltest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.AuxillaryCondition;
import Models.ConAux;
import Models.ConditionInspection;
import Models.Inspection;
import Models.Permission;
import Models.Plants;
import Models.ResponsibleSubject;
import Models.Settings;

public class MainActivity extends Activity  {	

Settings mysettings;	
ArrayList<Inspection> mylist;
ArrayList<Inspection> mylistnew;
ArrayList<Permission> mypermission;
ArrayList<ResponsibleSubject> myrepo;
String Dateiname = "myinspection";
String AuxDateiname = "myaux";
String RepoDateiname = "myrepo";
String PermissionDateiname ="mypermission";
ArrayList<AuxillaryCondition>myauxlist;
Inspection mySelectedInspection;
ListView auxList;
String UsedPlants;
String UsedPermissions;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
              
        
       
        mylistnew = new FileManager(getActivity()).GetInspects(Dateiname);
		myauxlist = new FileManager(getActivity()).GetAux(AuxDateiname);
		mypermission = new FileManager(getActivity()).GetPermission(PermissionDateiname);	
		
		
		if(mylistnew!=null)
		mylist = new FileCorrecter(getActivity(), mylistnew).correctthelist();
		
		
				
	    auxList = (ListView)findViewById(R.id.list_auxillary);        
       
        InspectionAdapter ia = new InspectionAdapter(getActivity(), R.layout.list_item_row, mylist);
        final ListView inspection = (ListView)findViewById(R.id.list_inspection);
        inspection.setAdapter(ia);    
        
        //OnClickListener for the List
        inspection.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				view.setSelected(true);
				UsedPermissions = null;
				UsedPlants=null;				
				
				mySelectedInspection = null;				
				mySelectedInspection = (Inspection) inspection.getAdapter().getItem(position);
				
				//int myPosition = mySelectedInspection.getId();
				//int lenght = mySelectedInspection.getConditionInspections().size();
				//String[] aux = new String[lenght];
				
				TextView tv3 = (TextView)findViewById(R.id.textView3);
				TextView tv4 = (TextView)findViewById(R.id.textView4);
				TextView tv5 = (TextView)findViewById(R.id.textView5);//GenNamen
				TextView tv6 = (TextView)findViewById(R.id.textView6);//AnlagenNamen
				ImageView img = (ImageView)findViewById(R.id.image_view1);
				
				tv3.setVisibility(View.VISIBLE);
				tv4.setVisibility(View.VISIBLE);
				tv5.setVisibility(View.VISIBLE);
				tv6.setVisibility(View.VISIBLE);
				img.setVisibility(View.VISIBLE);
				
				
				//int inspecid = mySelectedInspection.getId();

				Boolean isfirst = true;
				Boolean istfirstplant = true;
				
				//Permissions,Anlagen zuordnen zur ausgewählten Prüfung
				for (Permission permission: mypermission) {	
					
				List<AuxillaryCondition> auxperm = permission.getAuxillaryconditions();	
				List<Plants> plaperm = permission.getPlants();
				
				for (AuxillaryCondition auxillaryCondition : auxperm) {
					
				List<ConditionInspection> condperm = auxillaryCondition.getConditionInspection();	
				
				for (ConditionInspection conditionInspection : condperm) {
					
				if(conditionInspection.getId() == mySelectedInspection.getId())	
				{							
					String value = permission.getName();
				
					if(isfirst==true)
					{UsedPermissions = value;
					isfirst=false;}
					else
					{UsedPermissions = UsedPermissions+";"+ value;}
			
					for (Plants plants : plaperm) {					
					  
						if(istfirstplant==true)
							{
								UsedPlants= plants.getNumber();							
								istfirstplant=false;
								byte[] data = plants.getPlantImageSource().getBinarySource();
								setImageViewWithByteArray(img, data);
							}
							else{
								UsedPlants= UsedPlants+","+plants.getNumber();	
							
					  						
					 }			
					}
				   }					 	
			    	}	
				     }					
				      }	
				       

				tv5.setText(UsedPermissions);
				tv6.setText(UsedPlants);
				
				auxList.setAdapter(null);				
				AuxillaryAdapter aa = new AuxillaryAdapter(getActivity(), R.layout.list_auxlliary_row ,FilterAuxListWithID(mySelectedInspection));
				auxList.setAdapter(aa);
				
				TextView auxhead = (TextView)findViewById(R.id.textView2);				
				Button startinspect = (Button)findViewById(R.id.btn_inspects);
				
				startinspect.setVisibility(View.VISIBLE);
				auxhead.setVisibility(View.VISIBLE);
				auxList.setVisibility(View.VISIBLE);				
						
			}
		});
    }

    private List<ConAux> FilterAuxListWithID(Inspection number)
	{
    	List<ConAux> conAuxList = new ArrayList<ConAux>();     	
    	
		for (ConditionInspection condition :number.getConditionInspections()) 
		{
			for (AuxillaryCondition auxiliaryCondition : myauxlist) {
				if(condition.getAuxiliaryConditionId() == auxiliaryCondition.getId())
				{
					conAuxList.add(new ConAux(auxiliaryCondition, condition, number.getId()));
					
				}
			}
		}
		
		return conAuxList;
	}
  
    private Context getActivity() {
		// TODO Auto-generated method stub
		return MainActivity.this;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }   
    
	public void starttheinspecs(View v)
	{	
			Bundle bndl = new Bundle();
			bndl.clear();
			bndl.putString("UsedPlants", UsedPlants);
			bndl.putString("UsedPermissions", UsedPermissions);
			int fortheintent = mySelectedInspection.getId();
			bndl.putInt("CurrentInspection", fortheintent);
          	Intent in = new Intent(this, InspectionProgressActivity.class);
          	in.putExtras(bndl);
          	startActivity(in);          
	}
		
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	switch (item.getItemId()) {
		case R.id.action_settings:
			
			Intent in = new Intent(this, SettingsActivity.class);
        	startActivity(in);       
			
			return true;

		case R.id.action_synchronize:	
			
			Intent in1 = new Intent(this, Synchronize.class);
			startActivityForResult(in1, 1);
			
			/* ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		     NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		          
		        if (mWifi.isConnected()) {
		        
		        mysettings = (Settings) new DataHandler().getObjectFromFile(DataHandler.SETTINGS_FILEMANE, getActivity());
		        if(mysettings==null){
		        	Toast toast = Toast.makeText(getActivity(), "Unable to get Connections. Missing Settings", Toast.LENGTH_LONG);
		        	toast.show();      	
		        }
		        else{
		        	try {
						mylist = new HubGetData(getActivity()).execute().get();
						myauxlist = new HubGetAux(getActivity()).execute().get();
						mypermission = new HubGetPermission(getActivity()).execute().get();
						myrepo = new HubGetPersons(getActivity()).execute().get();
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}   	
		        	
		        	if (mylist.size()!=0 && myauxlist.size()!=0 && mypermission.size()!=0 && myrepo.size()!=0
		        			) {				        		
		        		
		        		/*boolean mycondition = true;
		        		
		        		do {
		        				int counter = 0;
		        				int listlength = mylist.size();
		        				for (Inspection insp : mylist) {
			        			
			        			double test = insp.getProgress();
			        			double numb = 0;		        			
			        			counter++;
			        			
			        			if(test!=numb)
			        			{int index = insp.getId();
			        			 mylist.remove(index);
			        			 
			        			if (counter == listlength) {
								mycondition = false;			        				
								}
			        			 
			        			 break;}	  	
		        				}
		        			
							
						} while (mycondition==true);
		        				        		
		        		new FileManager(getActivity()).DeleteFiles();
		     			new FileManager(getActivity()).SaveInspects(mylist, Dateiname);
		     			new FileManager(getActivity()).SaveAux(myauxlist, AuxDateiname);	
		     			new FileManager(getActivity()).SavePermission(mypermission, PermissionDateiname);
		     			new FileManager(getActivity()).SaveRepo(myrepo, RepoDateiname);
						}	
		        	else{
		        		Toast toast = Toast.makeText(this, "Probleme beim Synchronisieren! Server online ?", Toast.LENGTH_LONG);
			        	toast.show();
		        		
		         }	        	    
		        }
		       }    
		        else{
		        	Toast toast = Toast.makeText(this, "Keine Internetverbindung", Toast.LENGTH_LONG);
		        	toast.show();			        	
		        } 
		       */
			return true;
			
		default:
			return true;
			
    	}        
     }  
    
    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
    	
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
        
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	  super.onActivityResult(requestCode, resultCode, data);
	  if(resultCode==2)
	  {
		Toast toast = Toast.makeText(this, "Keine WiFi-Verbindung oder Probleme mit dem Server! Synchronisation fehlgeschlagen", Toast.LENGTH_LONG);
      	toast.show();			  
	  }
	  if(resultCode==1)
	  {
		Toast toast = Toast.makeText(this, "Synchronisation erfolgreich!", Toast.LENGTH_LONG);
      	toast.show();			  
	  }
	  
	}
	
    
    }


