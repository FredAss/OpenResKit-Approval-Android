package htw.plantar.activities;

import htw.plantar.models.Plant;

import com.example.plantar.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import roboguice.fragment.RoboFragment;

public class PlantDataFragment extends RoboFragment
{
	Button m_ShowDetailButton;
	Button m_ShowSubstancesButton;
	Button m_ShowDocumentsButton;

	FrameLayout m_DetailFrameLayout;
	FrameLayout m_SubstanceFrameLayout;
	FrameLayout m_DocumentFrameLayout;
	
	private ViewGroup m_View;
	private FragmentManager m_FragmentManager;
	
	private PlantDetailFragment m_PlantDetailFragment;
	private PlantSubstanceFragment m_PlantSubstanceFragment;
	private PlantDocumentFragment m_PlantDocumentFragment;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		m_View = (ViewGroup) inflater.inflate(R.layout.plant_data_fragment_layout, container, false);			
		InitializeComponents();

		m_FragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = m_FragmentManager.beginTransaction();
		
		m_PlantDetailFragment = new PlantDetailFragment();
		m_PlantSubstanceFragment = new PlantSubstanceFragment();
		m_PlantDocumentFragment = new PlantDocumentFragment();
		
		transaction.replace(R.id.detailFragment, m_PlantDetailFragment);
		transaction.replace(R.id.substanceFragment, m_PlantSubstanceFragment);
		transaction.replace(R.id.documentFragment, m_PlantDocumentFragment);
		transaction.commit();
		
		m_DetailFrameLayout.setVisibility(View.VISIBLE);
		m_SubstanceFrameLayout.setVisibility(View.INVISIBLE);
		m_DocumentFrameLayout.setVisibility(View.INVISIBLE);
		
		return m_View;
	}
	
	private void InitializeComponents()
	{
		m_ShowDetailButton = (Button)m_View.findViewById(R.id.DetailButton);
		m_ShowDetailButton.setOnClickListener(DetailButtonListener);
		m_ShowSubstancesButton = (Button)m_View.findViewById(R.id.SubstanceButton);
		m_ShowSubstancesButton.setOnClickListener(SubstanceButtonListener);
		m_ShowDocumentsButton = (Button)m_View.findViewById(R.id.DocumentButton);
		m_ShowDocumentsButton.setOnClickListener(DocumentButtonListener);
		m_DetailFrameLayout = (FrameLayout)m_View.findViewById(R.id.detailFragment);
		m_SubstanceFrameLayout = (FrameLayout)m_View.findViewById(R.id.substanceFragment);
		m_DocumentFrameLayout = (FrameLayout)m_View.findViewById(R.id.documentFragment);
		
		m_ShowDetailButton.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	public void SendPlantData(Plant plant, boolean fromCamera)
	{
		m_PlantDetailFragment.ChangeData(plant, fromCamera);
		m_PlantSubstanceFragment.ChangeData(plant.getSubstances(), fromCamera);
		m_PlantDocumentFragment.ChangeData(plant, fromCamera);
	}
	
	private OnClickListener DetailButtonListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			m_DetailFrameLayout.setVisibility(View.VISIBLE);
			m_SubstanceFrameLayout.setVisibility(View.INVISIBLE);
			m_DocumentFrameLayout.setVisibility(View.INVISIBLE);
			
			m_ShowDetailButton.setTypeface(Typeface.DEFAULT_BOLD);
			m_ShowSubstancesButton.setTypeface(Typeface.DEFAULT);
			m_ShowDocumentsButton.setTypeface(Typeface.DEFAULT);
		}
	};
	
	private OnClickListener SubstanceButtonListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			m_DetailFrameLayout.setVisibility(View.INVISIBLE);
			m_SubstanceFrameLayout.setVisibility(View.VISIBLE);
			m_DocumentFrameLayout.setVisibility(View.INVISIBLE);
			
			m_ShowDetailButton.setTypeface(Typeface.DEFAULT);
			m_ShowSubstancesButton.setTypeface(Typeface.DEFAULT_BOLD);
			m_ShowDocumentsButton.setTypeface(Typeface.DEFAULT);
		}
	};
	
	private OnClickListener DocumentButtonListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			m_DetailFrameLayout.setVisibility(View.INVISIBLE);
			m_SubstanceFrameLayout.setVisibility(View.INVISIBLE);
			m_DocumentFrameLayout.setVisibility(View.VISIBLE);
			
			m_ShowDetailButton.setTypeface(Typeface.DEFAULT);
			m_ShowSubstancesButton.setTypeface(Typeface.DEFAULT);
			m_ShowDocumentsButton.setTypeface(Typeface.DEFAULT_BOLD);
		}
	};
}
