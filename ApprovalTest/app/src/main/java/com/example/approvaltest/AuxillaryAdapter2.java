package com.example.approvaltest;

import java.util.List;

import Models.AuxillaryCondition;
import Models.ConAux;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class AuxillaryAdapter2 extends ArrayAdapter<ConAux>
{
	private List<AuxillaryCondition> m_ScheduledTask;
	private List<ConAux> m_ConAuxs;
	private Context m_Context;

	public AuxillaryAdapter2(Context context, int resource,List<ConAux> objects) {
		super(context, resource, objects);
			
		m_ConAuxs = objects;
		m_Context = context;
	}
		
	private class ViewHolder
	{
		TextView m_InspectionNameTextView;
		CheckBox m_InspectionCheckBox;
		Button m_InspectionButton;	
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		final ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_auxlliary_row2, null);
			
			viewHolder = new ViewHolder();
			viewHolder.m_InspectionButton=(Button)convertView.findViewById(R.id.auxillary_item_btn);
			viewHolder.m_InspectionNameTextView = (TextView)convertView.findViewById(R.id.auxillary_item_name2);
			viewHolder.m_InspectionCheckBox=(CheckBox)convertView.findViewById(R.id.auxillary_item_box);
			viewHolder.m_InspectionButton.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) 
				{
					Intent in = new Intent(getContext(), NewMeasureActivity.class);
										
					ConAux conaux = getItem(position);
					Bundle bndl = new Bundle();
					bndl.clear();
					Integer auxx = conaux.getAux().getId();
					Integer conn = conaux.getCon().getId();
					int number = conaux.getInspectionnumber();
										
					bndl.putInt("AuxilId", auxx);
					bndl.putInt("ConId", conn);
					bndl.putInt("InspectNumb", number);
										
		          	in.putExtras(bndl);		          	
					m_Context.startActivity(in);
					
				}
			});
			
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.auxillary_item_name2, viewHolder.m_InspectionNameTextView);
			convertView.setTag(R.id.auxillary_item_box, viewHolder.m_InspectionCheckBox);
			convertView.setTag(R.id.auxillary_item_btn, viewHolder.m_InspectionButton);
			
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		ConAux currentAux = getItem(position);		
		viewHolder.m_InspectionNameTextView.setTag(position);		
		viewHolder.m_InspectionNameTextView.setText(currentAux.getAux().getCondition().toString());
		viewHolder.m_InspectionCheckBox.setTag(position);
		viewHolder.m_InspectionCheckBox.setChecked(false);
		viewHolder.m_InspectionButton.setTag(position);
	
		return convertView;
	}
	
	
	
	
}
