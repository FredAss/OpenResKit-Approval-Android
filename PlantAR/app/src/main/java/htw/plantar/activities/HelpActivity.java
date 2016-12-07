package htw.plantar.activities;

import com.example.plantar.R;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import roboguice.activity.RoboActivity;

public class HelpActivity extends RoboActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_activity_layout);
		SetScreenOrientation();
	}
	
	private void SetScreenOrientation()
	{
		if(getResources().getBoolean(R.bool.isTablet))
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
