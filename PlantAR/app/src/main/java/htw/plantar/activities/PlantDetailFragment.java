package htw.plantar.activities;

import htw.plantar.models.Plant;

import com.example.plantar.R;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlantDetailFragment extends RoboFragment
{
	TextView m_NameTextView;
	TextView m_NumberTextView;
	TextView m_PositionTextView;
	TextView m_DescriptionInfoTextView;
	TextView m_DescriptionTextView;
	ImageView m_PictureImageView;
	@InjectView (R.id.layout_detail) RelativeLayout m_DetailsRelativeLayout;
	
	private Plant m_SelectedPlant;
	private ViewGroup m_View;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		m_View = (ViewGroup) inflater.inflate(R.layout.plant_detail_fragment_layout, container, false);			
		InitializeComponents();
		
		if(m_SelectedPlant != null)
			ChangeData(m_SelectedPlant, false);

		return m_View;
	}
	
	private void InitializeComponents()
	{
		m_NameTextView =(TextView)m_View.findViewById(R.id.plant_name);
		m_NumberTextView = (TextView)m_View.findViewById(R.id.plant_number);
		m_DescriptionInfoTextView = (TextView)m_View.findViewById(R.id.text_description);
		m_DescriptionTextView = (TextView)m_View.findViewById(R.id.plant_description);
		m_PositionTextView = (TextView)m_View.findViewById(R.id.plant_position);
		m_PictureImageView = (ImageView)m_View.findViewById(R.id.plant_image);
	}

	public void ChangeData(Plant data, boolean fromCamera)
	{	
		this.m_SelectedPlant = data;
		
		if(!fromCamera)
		{
			m_NameTextView.setText(getResources().getString(R.string.name )+ ":  " + data.getName());
			m_NumberTextView.setText(getResources().getString(R.string.number) + ":  " + data.getNumber());
			m_PositionTextView.setText(getResources().getString(R.string.position) + ":  " + data.getPosition());
			m_DescriptionInfoTextView.setText(getResources().getString(R.string.description));
			m_DescriptionTextView.setText(data.getDescription());
			
			if(data.getPlantImageSource() == null || data.getPlantImageSource().getBinarySource() == null)
			{
				//TODO Anwender darauf hinweisen, dass kein Bild existiert
			}
			else
				ConvertArrayToBitmap(data.getPlantImageSource().getBinarySource());
		}
	}
	
	private void ConvertArrayToBitmap(byte[] array)
	{
		Bitmap bm = BitmapFactory.decodeByteArray(array, 0, array.length);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		m_PictureImageView.setMinimumHeight(dm.heightPixels);
		m_PictureImageView.setMinimumWidth(dm.widthPixels);
		m_PictureImageView.setImageBitmap(bm);
	}
}
