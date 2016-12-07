package htw.plantar.activities;

import roboguice.activity.RoboFragmentActivity;
import htw.plantar.models.Plant;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.example.plantar.R;

public class PlantDataFragmentActivity extends RoboFragmentActivity
{
	private FragmentManager m_FragmentManager;
	private PlantDataFragment m_PlantDataFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plant_data_activity_layout);
		SetScreenOrientation();
		
		Intent intent = getIntent();
		Plant receivedPlant = (Plant) intent.getSerializableExtra("data");
		
		m_FragmentManager = getSupportFragmentManager();
		m_PlantDataFragment = (PlantDataFragment) m_FragmentManager.findFragmentById(R.id.list_main_data_container);
		m_PlantDataFragment.SendPlantData(receivedPlant, true);
	}
	
	private void SetScreenOrientation()
	{
		if(getResources().getBoolean(R.bool.isTablet))
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
