package com.example.approvaltest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.approvaltest.HubDatas.HubGetAux;
import com.example.approvaltest.HubDatas.HubGetData;
import com.example.approvaltest.HubDatas.HubGetPermission;
import com.example.approvaltest.HubDatas.HubGetPersons;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Models.AuxillaryCondition;
import Models.Inspection;
import Models.Measure;
import Models.Permission;
import Models.ResponsibleSubject;
import Models.Settings;

public class Synchronize extends Activity {

ProgressDialog barProgressDialog;
Handler updateBarHandler;
Settings mysettings;
ArrayList<Inspection> mylist;
ArrayList<Permission> mypermission;
ArrayList<ResponsibleSubject> myrepo;
ArrayList<AuxillaryCondition>myauxlist;
String Dateiname = "myinspection";
String AuxDateiname = "myaux";
String RepoDateiname = "myrepo";
String PermissionDateiname ="mypermission";
ArrayList<Measure> mymeasure;
ArrayList<Inspection>mylist1 = new ArrayList<Inspection>();
private static ObjectMapper mymapper = new ObjectMapper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_synchronize);
		
		updateBarHandler = new Handler();
		lauchBarDialog();
	}	
	
	public void lauchBarDialog(){
	
	barProgressDialog = new ProgressDialog(Synchronize.this);
	
	 barProgressDialog.setTitle("Synchronisiert Daten ...");
	 barProgressDialog.setMessage("Synchronisiert ... Bitte warten!");
	 barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
	 barProgressDialog.setProgress(0);
	 barProgressDialog.setMax(20);
	 barProgressDialog.show();
	  

     new Thread(new Runnable() {
     	 
         @Override

         public void run() {

             try {
                 // Here you should write your time consuming task...
            	 //
            	 //
            	 //
            	 //
            	 //
            	 //
            	 boolean isokay = true;
     			 ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    		     NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    		          
    		        if (mWifi.isConnected()) {
    		        
    		        mysettings = (Settings) new DataHandler().getObjectFromFile(DataHandler.SETTINGS_FILEMANE, getActivity());
    		        if(mysettings==null){
    		        	Toast toast = Toast.makeText(getActivity(), "Unable to get Connections. Missing Settings", Toast.LENGTH_LONG);
    		        	toast.show();      	
    		        }
    		        else{
    		        	try {    		        		
    		        	
    		        	try {
								/*mylist = new FileManager(getApplicationContext()).GetInspects(Dateiname);
								
								for (Inspection myinsp: mylist) {
									
									List<ConditionInspection> conlist = myinsp.getConditionInspections();	
									
									for (ConditionInspection conditionInspection : conlist) {
													
									ArrayList<Measure> mym = (ArrayList<Measure>) conditionInspection.getMeasures();    		        			
									
									for (Measure measure : mym) {
										
									boolean myym = measure.isInternalid();
									
									if (myym == true)
									{
									 
										Measure myobj = measure;
										Measure object = mymapper.readValue(myobj.toString(), Measure.class);
										mymeasure.add(measure);	
										
									}
									}
								}
								}*/
							} catch (Exception e) {
								
								e.printStackTrace();
							}
    		        		
    		        	new HubGetData(getActivity()).WriteDataToHub();
    		        	
    		        		/*if(mymeasure!=null)
    		        		{
    		        			//TODO HUB SET DATA
    		        			new HubGetData(getActivity()).WriteDataToHub();
    		        			
    		        		}*/
    		        		
    						mylist = new HubGetData(getActivity()).execute().get();
    						myauxlist = new HubGetAux(getActivity()).execute().get();
    						mypermission = new HubGetPermission(getActivity()).execute().get();
    						myrepo = new HubGetPersons(getActivity()).execute().get();
    						
    					} catch (InterruptedException e1) {
    						
    						e1.printStackTrace();
    					} catch (ExecutionException e1) {
    						
    						e1.printStackTrace();
    					}   	
    		        	
    		        	if (mylist.size()!=0 && myauxlist.size()!=0 && mypermission.size()!=0 && myrepo.size()!=0
    		        			) {				        		
    		        		
    		        		//boolean mycondition = true; 	
    		        		    		        		
    		        		for (Inspection inspection : mylist) {
    		        			
    		        			if(inspection.getProgress()==0)
    		        			{
    		        				mylist1.add(inspection);    		        				
    		        				
    		        			}
								
							}
    		        		/*do {
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
    		        			
    							
    						} while (mycondition==true);*/
    		        				        		
    		        		new FileManager(getActivity()).DeleteFiles();
    		        		//new FileManager(getActivity()).SaveObjectsToFile(mylist, Dateiname);
    		     			new FileManager(getActivity()).SaveInspects(mylist1, Dateiname);
    		     			new FileManager(getActivity()).SaveAux(myauxlist, AuxDateiname);	
    		     			new FileManager(getActivity()).SavePermission(mypermission, PermissionDateiname);
    		     			new FileManager(getActivity()).SaveRepo(myrepo, RepoDateiname);
    						}	
    		        	else{
    		        		Toast toast = Toast.makeText(getActivity(), "Probleme beim Synchronisieren! Server online ?", Toast.LENGTH_LONG);
    			        	toast.show();
    		        		isokay=false;
    		         }	        	    
    		        }
    		       }    
    		        else{
    		        	Toast toast = Toast.makeText(getActivity(), "Keine Internetverbindung", Toast.LENGTH_LONG);
    		        	toast.show();
    		        	isokay=false;
    		        } 
    		                 	 
            	 //
            	 //
            	 //
            	 //
            	 //
    		     //
                 while (barProgressDialog.getProgress() <= barProgressDialog.getMax()) {

                     Thread.sleep(1000);
                     updateBarHandler.post(new Runnable() {
                         public void run() {
                             barProgressDialog.incrementProgressBy(2);
                           }
                       });

                     if (barProgressDialog.getProgress() == barProgressDialog.getMax()) {
                         barProgressDialog.dismiss();
                         
                         if(isokay==true)
                         {
                        	 setResult(1);	 
                         } 
                         else
                         {
                        	 setResult(2);
                         }
                         finish();
                         
                     }
                 }
             } catch (Exception e) {
            	 e.printStackTrace();
             }
         }

		private Context getActivity() {
			
			return Synchronize.this;
		}

     }).start();
	
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

}
