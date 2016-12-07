package htw.plantar.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.plantar.R;

import java.util.List;

import htw.plantar.functions.SubstanceAdapter;
import htw.plantar.models.Substance;
import roboguice.fragment.RoboFragment;

public class PlantSubstanceFragment extends RoboFragment 
{
	Spinner m_CategorySpinner;
	Spinner m_TypeSpinner;
	ListView m_SubstancesListView;
	
	private ViewGroup m_View;
	private SubstanceAdapter m_SubstanceAdapter;
	private List<Substance> m_Substances;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		m_View = (ViewGroup) inflater.inflate(R.layout.plant_substance_fragment_layout, container, false);			
		InitializeComponents();
		
		if(m_Substances != null)
			ChangeData(m_Substances, false);
		
		return m_View;
	}
	
	private void InitializeComponents()
	{
		m_CategorySpinner = (Spinner)m_View.findViewById(R.id.CategorySpinner);
		
		ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_style, getResources().getStringArray(R.array.category));
		categoryAdapter.setDropDownViewResource(R.layout.spinner_item_list_style);
		m_CategorySpinner.setAdapter(categoryAdapter);
		m_CategorySpinner.setOnItemSelectedListener(ItemSelectedListener);

		m_TypeSpinner = (Spinner)m_View.findViewById(R.id.TypeSpinner);
		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_style, getResources().getStringArray(R.array.type));
		typeAdapter.setDropDownViewResource(R.layout.spinner_item_list_style);
		m_TypeSpinner.setAdapter(typeAdapter);
		m_TypeSpinner.setOnItemSelectedListener(ItemSelectedListener);
		
		m_SubstancesListView = (ListView)m_View.findViewById(R.id.SubstancesListView);
		m_SubstancesListView.setOnItemClickListener(SubstanceSelectedListener);
	}

	public void ChangeData(List<Substance> substances, boolean fromCamera) 
	{
		m_Substances = substances;
		
		if(m_SubstanceAdapter != null)
			m_SubstanceAdapter.SetConstraints(0, 0);
	
		if(!fromCamera)
		{
			m_CategorySpinner.setSelection(0);
			m_TypeSpinner.setSelection(0);
			
			m_SubstancesListView.setAdapter(null);
			m_SubstanceAdapter = null;
			
			m_SubstanceAdapter = new SubstanceAdapter(getActivity(), R.layout.substance_item_row, substances);
			m_SubstancesListView.setAdapter(m_SubstanceAdapter);
		}
	}
	
	private OnItemSelectedListener ItemSelectedListener = new OnItemSelectedListener() 
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
		{
			if(m_SubstanceAdapter != null)
				m_SubstanceAdapter.SetConstraints(m_CategorySpinner.getSelectedItemPosition(), m_TypeSpinner.getSelectedItemPosition());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) 
		{
			
		}
	};
	
	private OnItemClickListener SubstanceSelectedListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			Substance selectedSubstance = m_SubstanceAdapter.getItem(position);
			String[] dangerTypes = selectedSubstance.getDangerTypes().split(",");	
			
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View dialogLayout = inflater.inflate(R.layout.dialog_substance_layout, null);
			
			TextView actions = (TextView)dialogLayout.findViewById(R.id.substanceActions);
			LinearLayout imagesLayout = (LinearLayout)dialogLayout.findViewById(R.id.substanceDialogLayout);
			
			actions.setText(selectedSubstance.getAction());
			
			AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
			builder.setTitle(selectedSubstance.getName() + " (" + selectedSubstance.getDescription() + ")");

			SetWarningImagesInLayout(imagesLayout, dangerTypes);

			builder.setNegativeButton(getResources().getString(R.string.cancel_string), new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					dialog.dismiss();
				}
			});
			
			builder.setView(dialogLayout);
			builder.show();
		}
	};
	
	private void SetWarningImagesInLayout(LinearLayout layout, String[] dangerTypes)
	{
		if(dangerTypes != null && dangerTypes.length > 0 && !dangerTypes[0].equals(""))
		{
			for(int i = 0; i < dangerTypes.length; i++)
			{
				layout.addView(AddImageView(SearchForWarningDrawable(dangerTypes[i])));
			}
		}
	}
	
	private ImageView AddImageView(int res)
	{
		LayoutParams layout= new LinearLayout.LayoutParams(80, 80);
		layout.setMargins(15, 0, 15, 0);
		ImageView image = new ImageView(getActivity());
		image.setImageResource(res);
		image.setLayoutParams(layout);
		return image;
	}
	
	private int SearchForWarningDrawable(String dangerType)
	{
		switch (Integer.parseInt(dangerType)) 
		{
		case 0:
			return R.drawable.ic_accid_warning;
		case 1:
			return R.drawable.ic_environment_warning;
		case 2:
			return R.drawable.ic_explosive_warning;
		case 3:
			return R.drawable.ic_flamable_warning;
		case 4:
			return R.drawable.ic_harmful_warning;
		case 5:
			return R.drawable.ic_helpflamable_warning;
		case 6:
			return R.drawable.ic_light_flamable_warning;
		case 7:
			return R.drawable.ic_poison_warning;
		case 8:
			return R.drawable.ic_very_poison_warning;
		default:
			return -1;
		}
	}
}
