package htw.plantar.functions;

import java.util.ArrayList;
import java.util.List;
import com.example.plantar.R;
import htw.plantar.models.Plant;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlantAdapter extends ArrayAdapter<Plant> 
{
	private List<Plant> m_Plants;
	private List<Plant> m_FilteredPlants;
	private Filter m_Filter;
	
	public PlantAdapter(Context context, int resource, List<Plant> objects) 
	{
		super(context, resource, objects);
				
		m_FilteredPlants = new ArrayList<Plant>(objects);
		m_Plants = new ArrayList<Plant>(objects);
		m_Filter = new PlantFilter();
	}
	
	private class ViewHolder
	{
		TextView m_ItemPlantNameTextView;
		TextView m_ItemPlantNumberTextView;
		TextView m_ItemPlantPositionTextView;
		ImageView m_ItemPlantImageImageView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		final ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.plant_item_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.m_ItemPlantNameTextView = (TextView)convertView.findViewById(R.id.item_plant_name);
			viewHolder.m_ItemPlantNumberTextView = (TextView)convertView.findViewById(R.id.item_plant_number);
			viewHolder.m_ItemPlantPositionTextView = (TextView)convertView.findViewById(R.id.item_plant_position);
			viewHolder.m_ItemPlantImageImageView = (ImageView)convertView.findViewById(R.id.item_plant_image);
		
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.item_plant_name, viewHolder.m_ItemPlantNameTextView);
			convertView.setTag(R.id.item_plant_number, viewHolder.m_ItemPlantNumberTextView);
			convertView.setTag(R.id.item_plant_position, viewHolder.m_ItemPlantPositionTextView);		
			convertView.setTag(R.id.item_plant_image, viewHolder.m_ItemPlantImageImageView);
		}
		else
			viewHolder = (ViewHolder)convertView.getTag();
		
		Plant currentPlant = getItem(position);
		
		viewHolder.m_ItemPlantNameTextView.setTag(position);
		viewHolder.m_ItemPlantNumberTextView.setTag(position);
		viewHolder.m_ItemPlantPositionTextView.setTag(position);
		viewHolder.m_ItemPlantImageImageView.setTag(position);
		
		if(currentPlant.getName() != null)
			viewHolder.m_ItemPlantNameTextView.setText(currentPlant.getName());
	
		if(currentPlant.getNumber() != null)
			viewHolder.m_ItemPlantNumberTextView.setText(" (" + currentPlant.getNumber() + ")");

		if(currentPlant.getPosition() != null)
			viewHolder.m_ItemPlantPositionTextView.setText(currentPlant.getPosition());
		
		if(currentPlant.getPlantImageSource() == null || currentPlant.getPlantImageSource().getBinarySource() == null)
			viewHolder.m_ItemPlantImageImageView.setImageResource(R.drawable.ic_alternate_plant_picture);
		else
			viewHolder.m_ItemPlantImageImageView.setImageBitmap(ConvertArrayToBitmap(currentPlant.getPlantImageSource().getBinarySource()));

		return convertView;
	}
	
	@Override
	public Filter getFilter() 
	{
		if(m_Filter == null)
		{
			m_Filter = new PlantFilter();
		}
		return m_Filter;
	}

	private Bitmap ConvertArrayToBitmap(byte[] array)
	{
		Bitmap bm = BitmapFactory.decodeByteArray(array, 0, array.length);
		return bm;
	}	
	
	private class PlantFilter extends Filter
	{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) 
		{
			String constraintToCheck = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			
			if(constraintToCheck != null && constraintToCheck.toString().length() > 0)
			{
				ArrayList<Plant> filt = new ArrayList<Plant>();
				ArrayList<Plant> lItems = new ArrayList<Plant>();
				
				synchronized(this)
				{
					lItems.addAll(m_Plants);
				}
								
				for(int i = 0; i < lItems.size(); i++)
				{
					Plant plant = lItems.get(i);
					if(plant.getName().toString().toLowerCase().contains(constraintToCheck) || plant.getNumber().toString().toLowerCase().contains(constraintToCheck))
					{
						filt.add(plant);
					}
				}
				
				result.count = filt.size();
				result.values = filt;
			}
			else
			{
				synchronized (this) 
				{
					result.values = m_Plants;
					result.count = m_Plants.size();
				}
			}
			
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) 
		{
			m_FilteredPlants = (ArrayList<Plant>)results.values;
			notifyDataSetChanged();
			clear();
			
			for(int i = 0; i < m_FilteredPlants.size(); i ++)
			{
				add(m_FilteredPlants.get(i));
			}
		}
	}
}
