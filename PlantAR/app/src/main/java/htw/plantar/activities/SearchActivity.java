package htw.plantar.activities;

import htw.plantar.functions.FileManager;
import htw.plantar.models.Plant;
import htw.plantar.odata.ApprovalRepository;
import htw.plantar.views.ImageButtonTextView;
import com.example.plantar.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import javax.inject.Inject;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import roboguice.activity.RoboActivity;

public class SearchActivity extends RoboActivity 
{
	@Inject ApprovalRepository m_Repository;
	
	ImageButtonTextView m_ExecuteListButton;
	ImageButtonTextView m_ExecuteQRButton;
	ImageButtonTextView m_ExecuteARButton;
	
	private Activity m_Context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_search_activity_layout);
        m_Context = this;
                
        CreateNeddedFiles();
        
        InitializeComponents();      
        
        SetScreenOrientation();
    }
    
    private void SetScreenOrientation()
	{
		if(getResources().getBoolean(R.bool.isTablet))
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
    
    private void CreateNeddedFiles()
    {
    	FileManager fm = new FileManager(m_Context);
    	fm.CreateNeededFiles();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
       
    @Override
	public boolean onOptionsItemSelected(MenuItem item) 
    {
		switch (item.getItemId()) 
		{
		case R.id.showPreferences:
			CreateNewActivityIntent(PreferenceActivity.class);
			return true;	
		case R.id.startLoadData:
			syncAndResetView();
			return true;	
		case R.id.deleteLocalData:
			m_Repository.DeleteData();
			return true;	
		case R.id.showHelp:
			CreateNewActivityIntent(HelpActivity.class);	
		default:
			return super.onOptionsItemSelected(item);
		}
	}

    private void syncAndResetView() 
	{
		m_Repository.GetDataFromOdataService(m_Context);
		invalidateOptionsMenu();
	}
    
	private void InitializeComponents()
    {	
		m_ExecuteListButton = new ImageButtonTextView(this, R.id.ExecuteListButton);
		m_ExecuteListButton.setImageResource(R.id.test_button_image, R.drawable.ic_lupe);
		m_ExecuteListButton.setText(R.id.test_button_text2, getResources().getString(R.string.search_list_string));
		m_ExecuteListButton.setOnClickListener(ExecuteListButtonClickListener);
		
		m_ExecuteQRButton = new ImageButtonTextView(this, R.id.ExecuteQRButton);
		m_ExecuteQRButton.setImageResource(R.id.test_button_image, R.drawable.ic_qr);
		m_ExecuteQRButton.setText(R.id.test_button_text2, getResources().getString(R.string.search_qr_string));
		m_ExecuteQRButton.setOnClickListener(ExecuteQRButtonClickListener);
		
		m_ExecuteARButton = new ImageButtonTextView(this, R.id.ExecuteARButton);
		m_ExecuteARButton.setImageResource(R.id.test_button_image, R.drawable.camera_icon);
		m_ExecuteARButton.setText(R.id.test_button_text2, getResources().getString(R.string.search_ar_string));
		m_ExecuteARButton.setOnClickListener(ExecuteARButtonClickListener);
    }
    
    private OnClickListener ExecuteListButtonClickListener = new OnClickListener() 
    {
		@Override
		public void onClick(View v) 
		{
			CreateNewActivityIntent(PlantListFragmentActivity.class);
		}
	};
	
	private OnClickListener ExecuteQRButtonClickListener = new OnClickListener() 
    {
		@Override
		public void onClick(View v) 
		{
			IntentIntegrator.initiateScan(m_Context);
		}
	};
	
	private OnClickListener ExecuteARButtonClickListener = new OnClickListener() 
    {
		@Override
		public void onClick(View v) 
		{
			CreateNewActivityIntent(ArActivity.class);
		}
	};
    
    private <T> void CreateNewActivityIntent(Class<T> targetClass)
    {
    	Intent intent = new Intent(m_Context, targetClass);
    	startActivity(intent);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		String barcode = scanResult.getContents();
		
		if(resultCode == RESULT_OK)
		{	
			Plant recognizedPlant = SearchForPlantWithRecognizedNumber(barcode);
			
			if(recognizedPlant != null)
			{				
				Intent intent = new Intent(this, PlantListFragmentActivity.class);
				intent.putExtra("selectedPlant", recognizedPlant);
				intent.putExtra("FromCamera", true);
				startActivity(intent);
			}
		}
	}
    
    private Plant SearchForPlantWithRecognizedNumber(String barcode)
    {
    	Plant recognizedPlant = new Plant();
		
		for (Plant plant : m_Repository.m_Plants) 
		{
			if(plant.getNumber().equals(barcode))
			{
				recognizedPlant = plant;
				break;
			}
		}
		
		return recognizedPlant;
    }
}
