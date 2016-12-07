package htw.plantar.activities;

import com.example.plantar.R;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import roboguice.activity.RoboPreferenceActivity;

public class PreferenceActivity extends RoboPreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	
		setPreferenceScreen(CreatePreferenceScreen());
		
		SetScreenOrientation();
	}
	
	private void SetScreenOrientation()
	{
		if(getResources().getBoolean(R.bool.isTablet))
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	private PreferenceScreen CreatePreferenceScreen()
	{
		@SuppressWarnings("deprecation")
		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);		
		PreferenceCategory dialogBasedPrefCat = new PreferenceCategory(getApplicationContext());
		dialogBasedPrefCat.setTitle(getResources().getString(R.string.settings_string));
		root.addPreference(dialogBasedPrefCat);
		
		
		EditTextPreference urlPref = new EditTextPreference(this);
	    urlPref.setKey("default_url");
	    EditTextPreference portPref = new EditTextPreference(this);
	    portPref.setKey("default_port");
	    EditTextPreference userPref = new EditTextPreference(this);
	    userPref.setKey("auth_user");
	    EditTextPreference passwordPref = new EditTextPreference(this);
	    passwordPref.setKey("auth_password");
	    
	    String serviceURI = "141.45.165.40";
	    String servicePort = "7000";
	    String serviceUser = "root";
	    String servicePass = "ork123";
	    
	    urlPref.setText(serviceURI);
	    urlPref.setDialogTitle(getResources().getString(R.string.url_string));
	    urlPref.setTitle(getResources().getString(R.string.url_string));
	    urlPref.setSummary(getResources().getString(R.string.url_description_string));
	    urlPref.setPositiveButtonText(getResources().getString(R.string.ok_string));
	    urlPref.setNegativeButtonText(getResources().getString(R.string.cancel_string));
	    dialogBasedPrefCat.addPreference(urlPref);
	    
	    portPref.setText(servicePort);
	    portPref.setDialogTitle(getResources().getString(R.string.port_string));
	    portPref.setTitle(getResources().getString(R.string.port_string));
	    portPref.setSummary(getResources().getString(R.string.port_description_string));
	    portPref.setPositiveButtonText(getResources().getString(R.string.ok_string));
	    portPref.setNegativeButtonText(getResources().getString(R.string.cancel_string));
	    dialogBasedPrefCat.addPreference(portPref);
	    
	    userPref.setText(serviceUser);
	    userPref.setDialogTitle(getResources().getString(R.string.user_string));
	    userPref.setTitle(getResources().getString(R.string.user_string));
	    userPref.setSummary(getResources().getString(R.string.user_description_string));
	    userPref.setPositiveButtonText(getResources().getString(R.string.ok_string));
	    userPref.setNegativeButtonText(getResources().getString(R.string.cancel_string));
	    dialogBasedPrefCat.addPreference(userPref);
	    
	    passwordPref.setText(servicePass);
	    passwordPref.setDialogTitle(getResources().getString(R.string.password_string));
	    passwordPref.setTitle(getResources().getString(R.string.password_string));
	    passwordPref.setSummary(getResources().getString(R.string.password_description_string));
	    passwordPref.setPositiveButtonText(getResources().getString(R.string.ok_string));
	    passwordPref.setNegativeButtonText(getResources().getString(R.string.cancel_string));
	    dialogBasedPrefCat.addPreference(passwordPref);
	    
	    return root;
	}	
}
