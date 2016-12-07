package htw.plantar.activities;

import htw.plantar.functions.BitmapProcessing;
import htw.plantar.models.Plant;
import htw.plantar.models.Substance;
import htw.plantar.odata.ApprovalRepository;
import htw.plantar.views.CameraView;

import com.example.plantar.R;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class ArActivity extends RoboActivity implements Runnable, PreviewCallback
{
	private Handler m_Handler = new Handler();
	private Plant m_DetectedPlant = null;
	private Activity m_Context; 
	private Camera m_Camera;
	private ImageProcessingTask m_Task;
	
	@Inject ApprovalRepository m_Repository;
	@InjectView (R.id.cameraView1) CameraView m_CameraView;
	@InjectView (R.id.plantInfo) RelativeLayout m_LinearLayout; 
	@InjectView (R.id.ar_plant_Name_Number) TextView m_PlantNameNumberTextView;
	@InjectView (R.id.ar_plant_description) TextView m_PlantDescriptionTextView;
	@InjectView (R.id.ar_plant_position) TextView m_PlantPositionTextView;
	@InjectView (R.id.ar_plant_substances) TextView m_PlantSubstancesTextView;
	@InjectView (R.id.ar_plant_documents) TextView m_PlantDocumentsTextView;
	@InjectView (R.id.plantSearchRestart) Button m_RestartSearchButton;
	@InjectView (R.id.plantInfoButton) Button m_NextPageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ar_activity_layout);	
		SetScreenOrientation();
		
		m_Context = this;

		InitializeComponents();	
		StartCameraPreview();
		
	}
	
	private void SetScreenOrientation()
	{
		if(getResources().getBoolean(R.bool.isTablet))
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		m_DetectedPlant = null;
		SetPlantInARInfo();
		m_CameraView.StartCameraPreview();
	}

	@Override
	protected void onPause() 
	{
		m_CameraView.StopCameraPreview();
		m_Task.cancel(true);
		super.onPause();
	}

	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		m_CameraView.StopCameraPreview();
		m_Task.cancel(true);
		this.finish();
	}

	private void InitializeComponents()
	{
		m_RestartSearchButton.setOnClickListener(RestartARButtonClickListener);
		m_NextPageButton.setOnClickListener(ShowDataInNextActivity);
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
		if(m_DetectedPlant == null)
		{
			m_Camera = camera;			
			//int i = m_Camera.getParameters().getPreviewFormat();
			m_Task = (ImageProcessingTask) new ImageProcessingTask().execute(data);
		}
	}	

	private Plant GetPlantFromProcessedText(String processedText)
	{
		Plant correctPlant = null;
		
		if(processedText == null || processedText.equals(""))
		{
			return correctPlant;
		}
		else
		{
			for (Plant plant : m_Repository.m_Plants) 
			{
				if(plant.getNumber().equals(processedText))
				{
					correctPlant = plant;
					setTitle("SaftyPlant");
					break;
				}
			}
			return correctPlant;
		}
	}
	
	private void SetPlantInARInfo()
	{
		if(m_DetectedPlant != null)
		{
			m_LinearLayout.setVisibility(View.VISIBLE);
			m_PlantNameNumberTextView.setText(m_DetectedPlant.getName() + " (" + m_DetectedPlant.getNumber() + ")");
			m_PlantDescriptionTextView.setText(m_DetectedPlant.getDescription());
			m_PlantPositionTextView.setText(m_DetectedPlant.getPosition());
			m_PlantSubstancesTextView.setText(Html.fromHtml(m_DetectedPlant.getSubstances().size() + " " + getResources().getString(R.string.substance_info) + " " + "(<font color=#FF0000>" + GetNumberOfRiskSubstance(2) + "</font><font color=#F1F1F1>/</font>" + "<font color=#FFFF00>" + GetNumberOfRiskSubstance(1) + "</font><font color=#F1F1F1>/</font>" + "<font color=#40FF00>" + GetNumberOfRiskSubstance(0) + "</font><font color=#F1F1F1>)</font>"));
			m_PlantDocumentsTextView.setText(getResources().getString(R.string.attached_documents) + " " + m_DetectedPlant.getAttachedDocuments().size());
		}
		else
			m_LinearLayout.setVisibility(View.INVISIBLE);
	}
	
	private int GetNumberOfRiskSubstance(int riskNumber)
	{
		int substanceOfRiskCount = 0;
		
		for (Substance substance : m_DetectedPlant.getSubstances()) 
		{
			if(substance.getRiskPotential() == riskNumber)
				substanceOfRiskCount++;
		}
		
		return substanceOfRiskCount;
	}
	
	@Override
	public void run() 
	{
		StartCameraPreview();
	}

	private void StartCameraPreview()
	{
		m_CameraView.setOneShotPreviewCallback(this);
		m_Handler.postDelayed(this, 1000);
	}
		
	private OnClickListener RestartARButtonClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			if(m_DetectedPlant != null)
			{
				m_DetectedPlant = null;
				SetPlantInARInfo();
			}
		}
	};
	
	private OnClickListener ShowDataInNextActivity = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent(m_Context, PlantListFragmentActivity.class);
			intent.putExtra("selectedPlant", m_DetectedPlant);
			intent.putExtra("FromCamera", true);
			startActivity(intent);
		}
	};
	
	private class ImageProcessingTask extends AsyncTask<byte[], Void, String>
	{	
		@Override
		protected String doInBackground(byte[]... params) 
		{
			if(m_DetectedPlant == null)
			{
				Bitmap processedImage = BitmapProcessing.CreateBitmapFromByteArray(params[0], m_Camera);
				Bitmap rotatedProcessedImage = BitmapProcessing.RotateImage(processedImage, m_CameraView.GetRotation());
				BitmapProcessing.SaveBitmap(rotatedProcessedImage, "Image-After_Rotate");
				String recognizedTextFromProcessedImage = BitmapProcessing.DetectTextFromImage(rotatedProcessedImage, m_Context);
				return recognizedTextFromProcessedImage;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) 
		{
			if(m_DetectedPlant == null && result != null)
			{
				m_Task.cancel(true);
				m_DetectedPlant = GetPlantFromProcessedText(result);

				SetPlantInARInfo();
			}

			super.onPostExecute(result);
		}
	}
}
