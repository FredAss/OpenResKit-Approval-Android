package com.example.approvaltest;

import java.util.List;

import Models.Inspection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InspectionAdapter extends ArrayAdapter<Inspection>
{
	private List<Inspection> m_ScheduledTask;

	public InspectionAdapter(Context context, int resource, List<Inspection> objects) {
		super(context, resource, objects);
		
		m_ScheduledTask = objects;
	}

	private class ViewHolder
	{
		TextView m_InspectionNameTextView;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		final ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.m_InspectionNameTextView = (TextView)convertView.findViewById(R.id.inspection_item_name);

			
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.inspection_item_name, viewHolder.m_InspectionNameTextView);

		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		Inspection currentInspection = getItem(position);		
		viewHolder.m_InspectionNameTextView.setTag(position);		
		viewHolder.m_InspectionNameTextView.setText(currentInspection.getRelatedSeries().getName());
		
		return convertView;
		
	}
	
	
}
