package com.example.approvaltest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import Models.ConditionInspection;
import Models.Inspection;
import Models.ScheduledTask;


public class FileCorrecter {
	
Context Ctx;
ArrayList<Inspection> List;
	
	
public FileCorrecter(Context ctx, ArrayList<Inspection> list)
{
	Ctx = ctx;
	List = list;

}
	
	
	
@SuppressWarnings("null")
public ArrayList<Inspection> correctthelist(){

ArrayList<Inspection> mylist = new ArrayList<Inspection>();	
int val;
int index = 0;
int valnew = 0;
Inspection obj = new Inspection();
List<ConditionInspection> condinsp = new ArrayList<ConditionInspection>();



		for (Inspection inspection : List) {

			try {
				int id = inspection.getId();
				obj.setId(id);
				obj.setProgress(inspection.getProgress());
				obj.setDueDate(inspection.getDueDate());
				obj.setEntryDate(inspection.getEntryDate());
				obj.setEntryResponsibleSubject(inspection.getEntryResponsibleSubject());
				obj.setRelatedSeries(inspection.getRelatedSeries());
				obj.setAppointmentResponsibleSubject(inspection.getAppointmentResponsibleSubject());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ArrayList<ConditionInspection> condi;
			condi = (ArrayList<ConditionInspection>) inspection.getConditionInspections();
			
		    valnew = 0;
		    int condiszize = 0;
		    condinsp.clear();
				
				for (ConditionInspection conditionInspection : condi) {
						
					int size = condi.size();
					val = conditionInspection.getId();

						if (val==valnew){
							condiszize++;							
	
							}
						else{
							condiszize++;
							valnew = val;
							try {
								condinsp.add(conditionInspection);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							}
						if(condiszize==size)
						{
							((Inspection) obj).setConditionInspection(condinsp);
							mylist.add(index, (Inspection) obj);
							index++;
							
						}
									}
												}				
	
return mylist;	
	
}	
	
	
	

}
