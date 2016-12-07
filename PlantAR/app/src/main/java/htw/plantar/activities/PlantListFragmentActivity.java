package htw.plantar.activities;

import htw.plantar.models.Plant;

import com.example.plantar.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import roboguice.activity.RoboFragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class PlantListFragmentActivity extends RoboFragmentActivity implements PlantListFragment.Communicator
{
	private FragmentManager m_FragmentManager;
	private PlantListFragment m_PlantListFragment;
	private PlantDataFragment m_PlantDataFragment;
	private SearchView m_FilterPlantSearchView;
	private boolean m_FromCamera;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_list_activity_layout);
        SetScreenOrientation();

        Intent intent = getIntent();
        Plant selectedPlant = (Plant) intent.getSerializableExtra("selectedPlant");
        m_FromCamera = (boolean) intent.getBooleanExtra("FromCamera", false);
        
        m_FragmentManager = getSupportFragmentManager();
        
        m_PlantListFragment = (PlantListFragment) m_FragmentManager.findFragmentById(R.id.list_main_item_container);
        m_PlantListFragment.SetCommunicator(this);
        
        if(selectedPlant != null)
        	m_PlantListFragment.PerformAutoShowPlantDetails(selectedPlant);
    }
	
	private void SetScreenOrientation()
	{
		if(getResources().getBoolean(R.bool.isTablet))
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        m_FilterPlantSearchView = (SearchView) searchItem.getActionView();
        m_FilterPlantSearchView.setQueryHint(getResources().getString(R.string.hint_filter_plants));

        m_FilterPlantSearchView.setOnQueryTextListener(new OnQueryTextListener() 
        {
			public boolean onQueryTextSubmit(String query) 
			{
				searchItem.collapseActionView();
				return false;
			}
			
			public boolean onQueryTextChange(String newText) 
			{
				m_PlantListFragment.FilterPlantListWithSearchText(newText);
				return false;
			}
		});

        return true;
    }
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		this.finish();
	}

	@Override
	public void Respond(Plant data) 
	{
		m_PlantDataFragment = (PlantDataFragment)m_FragmentManager.findFragmentById(R.id.list_main_detail_container);
		
		if(m_PlantDataFragment != null)
		{
			m_PlantDataFragment.SendPlantData(data, m_FromCamera);
			m_FromCamera = false;
		}
		else
		{
			Intent intent = new Intent(this, PlantDataFragmentActivity.class);
			intent.putExtra("data", data);
			intent.putExtra("FromCamera", m_FromCamera);
			startActivity(intent);
		}
	}
}
