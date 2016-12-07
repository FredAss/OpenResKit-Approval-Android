package com.example.approvaltest;

import Models.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	
	Settings mysettings;
	EditText URL;
	EditText Port;
	EditText Username;
	EditText Password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);	
		
		//Call the Views
		URL =(EditText)findViewById(R.id.settingsedittextip);
		Port =(EditText)findViewById(R.id.settingsedittextport);
		Username =(EditText)findViewById(R.id.settingsedittextuser);
		Password =(EditText)findViewById(R.id.settingsedittextpw);

		//Call Settings.java
		mysettings = (Settings) new DataHandler().getObjectFromFile(DataHandler.SETTINGS_FILEMANE, SettingsActivity.this);
		
		if (mysettings==null){
			
			mysettings = new Settings();
			
		}
		
		//fill textboxes
		if(mysettings.getURL()!=null){URL.setText(mysettings.getURL());}
		if(mysettings.getServerport()!=null){Port.setText(mysettings.getServerport());}
		if(mysettings.getUsername()!=null){Username.setText(mysettings.getUsername());}
		if(mysettings.getPassword()!=null){Password.setText(mysettings.getPassword());}
			
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//OnBackPressed
	
		@Override
		public void onBackPressed() {
		
			//Save the Data and go Back to the first View
			
		if(mysettings!=null){	
		mysettings.setURL(URL.getText().toString());	
		mysettings.setUsername(Username.getText().toString());
		mysettings.setServerport(Port.getText().toString());
		mysettings.setPassword(Password.getText().toString());
		new DataHandler().saveFileWObject(mysettings, DataHandler.SETTINGS_FILEMANE , SettingsActivity.this);
		SettingsActivity.this.finish();
		
		}		
	}
	
	
	
}
