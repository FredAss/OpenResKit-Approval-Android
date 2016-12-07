package com.example.approvaltest;

import java.util.List;

import Models.Employee;
import Models.EmployeeGroup;
import Models.ResponsibleSubject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinAdapter2 extends ArrayAdapter<ResponsibleSubject> 
{

	public SpinAdapter2(Context context, int resource,
			List<ResponsibleSubject> objects) 
	{
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}


	private class ViewHolder
	{
		TextView tv;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder viewHolder;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.spinner, null);
			
			viewHolder = new ViewHolder();
			viewHolder.tv = (TextView)convertView.findViewById(R.id.repoName);
			
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.repoName, viewHolder.tv);

		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		ResponsibleSubject repo = getItem(position);
		
		String last_name;
		
		
		
		if(repo.getClass().getName().equals("Models.Employee")){
			
			last_name = ((Employee)repo).getLastName().toString();
			viewHolder.tv.setText("Name des Verantwortlichen: " + last_name);
			viewHolder.tv.setTag(position);
			
			return convertView;
		}
		else
		viewHolder.tv.setText("Name des Verantwortlichen:" + ((EmployeeGroup)repo).getName().toString());
	    viewHolder.tv.setTag(position);
		
		return convertView;
		
	}
	
	

}
