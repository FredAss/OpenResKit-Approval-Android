package com.example.approvaltest;

import java.util.List;

import Models.AuxillaryCondition;
import Models.ConAux;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AuxillaryAdapter extends ArrayAdapter<ConAux>
{
	private List<AuxillaryCondition> m_ScheduledTask;
	private List<ConAux> m_ConAuxs;

	public AuxillaryAdapter(Context context, int resource/*, Inspection positionInspecs */,List<ConAux> objects) {
		super(context, resource, objects);
		
		//m_ScheduledTask = objects;
		//m_ScheduledTask = new ArrayList<AuxiliaryCondition>();
		m_ConAuxs = objects;
		//FilterAuxListWithID(objects, positionInspecs);

	}

	/*private void FilterAuxListWithID(List<AuxiliaryCondition> auxList, Inspection number)
	{
		for (ConditionInspection condition :number.getConditionInspections()) 
		{
			for (AuxiliaryCondition auxiliaryCondition : auxList) {
				if(condition.getAuxiliaryConditionId() == auxiliaryCondition.getId())
					m_ConAuxs.add(new ConAux(auxiliaryCondition, condition));
			}
		}
		
		
		

	}*/
	
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
			convertView = inflater.inflate(R.layout.list_auxlliary_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.m_InspectionNameTextView = (TextView)convertView.findViewById(R.id.auxillary_item_name);

			
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.auxillary_item_name, viewHolder.m_InspectionNameTextView);
			
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		ConAux currentAux = getItem(position);		
		viewHolder.m_InspectionNameTextView.setTag(position);		
		viewHolder.m_InspectionNameTextView.setText(currentAux.getAux().getCondition().toString());
		
		return convertView;
	}
	
	
}
