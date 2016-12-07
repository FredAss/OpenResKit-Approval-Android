package htw.plantar.activities;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;

import com.example.plantar.R;

import htw.plantar.functions.PlantAdapter;
import htw.plantar.models.Plant;
import htw.plantar.odata.ApprovalRepository;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PlantListFragment extends RoboFragment
{	
	@Inject ApprovalRepository m_Repository;
	
	private ViewGroup m_View;
	private Communicator m_Communicator;
	private ListView m_PlantListView;
	private List<Plant> m_Plants;
	private PlantAdapter m_PlantAdapter;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		m_View = (ViewGroup) inflater.inflate(R.layout.plant_list_fragment_layout, container, false);  
		InitializeComponents();
		return m_View;
	}

	private void InitializeComponents()
	{
		m_PlantListView = (ListView)m_View.findViewById(R.id.PlantListView );
		m_Plants = new ArrayList<Plant>(m_Repository.m_Plants);
		m_PlantAdapter = new PlantAdapter(getActivity(), R.layout.plant_item_row, m_Plants);
        m_PlantListView.setAdapter(m_PlantAdapter);
        m_PlantListView.setOnItemClickListener(PerformShowPlantDetails);
	}
	
	public void PerformAutoShowPlantDetails(Plant receivedPlant)
	{	
		for(int i = 0; i < m_Repository.m_Plants.size(); i++)
		{
			if(m_Repository.m_Plants.get(i).getNumber().equals(receivedPlant.getNumber()))
			{
				m_PlantListView.performItemClick(m_PlantListView.getAdapter().getView(i,  null,  null), i, m_PlantListView.getAdapter().getItemId(i));
				break;
			}
		}
	}

	public void SetCommunicator(Communicator c)
	{
		m_Communicator = c;
	}
	
	public interface Communicator
	{
		public void Respond(Plant data);
	}
	
	public void FilterPlantListWithSearchText(String filter)
	{
		m_PlantAdapter.getFilter().filter(filter);
	}
	
	private OnItemClickListener PerformShowPlantDetails = new OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			m_Communicator.Respond((Plant) m_PlantListView.getAdapter().getItem(position));
		}
	};	
}
