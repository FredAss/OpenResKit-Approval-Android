package htw.plantar.functions;

import java.util.ArrayList;
import java.util.List;
import com.example.plantar.R;
import htw.plantar.models.Substance;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class SubstanceAdapter extends ArrayAdapter<Substance> 
{
	private List<Substance> m_Substances;
	private List<Substance> m_FilterSubstance;
	private Filter m_Filter;
	private Context m_Context;
	
	private int m_CategoryConstraint;
	private int m_TypeConstraint;

	public SubstanceAdapter(Context context, int resource, List<Substance> objects) 
	{
		super(context, resource, objects);

		m_FilterSubstance = new ArrayList<Substance>(objects);
		m_Substances = new ArrayList<Substance>(objects);
		m_Filter = new SubstanceFilter();
		m_Context = context;
	}
	
	private class ViewHolder
	{
		TextView m_SubstanceSubstanceNameTextView;
		TextView m_ItemSubstanceCategoryTextView;
		TextView m_ItemSubstanceTypeTextView;
		TextView m_ItemSubstanceRiskTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		final ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.substance_item_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.m_SubstanceSubstanceNameTextView = (TextView)convertView.findViewById(R.id.SubstanceName);
			viewHolder.m_ItemSubstanceCategoryTextView = (TextView)convertView.findViewById(R.id.SubstanceCategory);
			viewHolder.m_ItemSubstanceTypeTextView = (TextView)convertView.findViewById(R.id.SubstanceType);
			viewHolder.m_ItemSubstanceRiskTextView = (TextView)convertView.findViewById(R.id.SubstanceRisk);
		
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.SubstanceName, viewHolder.m_SubstanceSubstanceNameTextView);
			convertView.setTag(R.id.SubstanceCategory, viewHolder.m_ItemSubstanceCategoryTextView);
			convertView.setTag(R.id.SubstanceType, viewHolder.m_ItemSubstanceTypeTextView);		
			convertView.setTag(R.id.SubstanceRisk, viewHolder.m_ItemSubstanceRiskTextView);
		}
		else
			viewHolder = (ViewHolder)convertView.getTag();
		
		Substance currentSubstance = getItem(position);
		
		viewHolder.m_SubstanceSubstanceNameTextView.setTag(position);
		viewHolder.m_ItemSubstanceCategoryTextView.setTag(position);
		viewHolder.m_ItemSubstanceTypeTextView.setTag(position);
		viewHolder.m_ItemSubstanceRiskTextView.setTag(position);
		
		if(currentSubstance.getName() != null)
			viewHolder.m_SubstanceSubstanceNameTextView.setText(currentSubstance.getName() + " (" + currentSubstance.getDescription() + ")");
		
		viewHolder.m_ItemSubstanceCategoryTextView.setText(m_Context.getResources().getStringArray(R.array.category)[currentSubstance.getCategory()].toString());
		viewHolder.m_ItemSubstanceTypeTextView.setText(m_Context.getResources().getStringArray(R.array.type)[currentSubstance.getType()].toString());
		viewHolder.m_ItemSubstanceRiskTextView.setText(m_Context.getResources().getStringArray(R.array.risk_potential)[currentSubstance.getRiskPotential()].toString());

		switch (currentSubstance.getRiskPotential()) 
		{
		case 0:
			viewHolder.m_ItemSubstanceRiskTextView.setBackgroundColor(Color.parseColor("#40FF00"));
			break;

		case 1:
			viewHolder.m_ItemSubstanceRiskTextView.setBackgroundColor(Color.parseColor("#FFFF00"));
			break;
			
		case 2:
			viewHolder.m_ItemSubstanceRiskTextView.setBackgroundColor(Color.parseColor("#FF0000"));
			break;
			
		default:
			break;
		}
				
		return convertView;
	}
	
	public void SetConstraints(int category, int type)
	{
		m_CategoryConstraint = category;
		m_TypeConstraint = type;
		m_Filter.filter(null);
	}
	
	@Override
	public Filter getFilter() 
	{
		if(m_Filter == null)
		{
			m_Filter = new SubstanceFilter();
		}
		return m_Filter;
	}
	
	private class SubstanceFilter extends Filter
	{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) 
		{
			FilterResults result = new FilterResults();
			

				ArrayList<Substance> filt = new ArrayList<Substance>();
				ArrayList<Substance> lItems = new ArrayList<Substance>();
				
				synchronized(this)
				{
					lItems.addAll(m_Substances);
				}
								
				for(int i = 0; i < lItems.size(); i++)
				{
					Substance substance = lItems.get(i);
					
					
					if(m_CategoryConstraint == 0 && m_TypeConstraint == 0)
					{
						filt.add(substance);
					}
					
					if(m_CategoryConstraint != 0 && m_TypeConstraint == 0)
					{
						if(substance.getCategory() == m_CategoryConstraint)
						{
							filt.add(substance);
						}
					}
					
					if(m_CategoryConstraint == 0 && m_TypeConstraint != 0)
					{
						if(substance.getType() == m_TypeConstraint)
						{
							filt.add(substance);
						}
					}
					
					if(m_CategoryConstraint != 0 && m_TypeConstraint != 0)
					{
						if(substance.getCategory() == m_CategoryConstraint && substance.getType() == m_TypeConstraint)
						{
							filt.add(substance);
						}
					}
				}
				
				result.count = filt.size();
				result.values = filt;
			
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) 
		{
			m_FilterSubstance = (ArrayList<Substance>)results.values;
			notifyDataSetChanged();
			clear();
			
			for(int i = 0; i < m_FilterSubstance.size(); i ++)
			{
				add(m_FilterSubstance.get(i));
			}
		}
	}
}
