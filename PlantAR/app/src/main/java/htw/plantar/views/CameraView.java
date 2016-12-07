package htw.plantar.views;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.example.plantar.R;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback 
{
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private boolean m_IsRunningPreview;
	private Context m_Context;
	private float m_Rotation;
	private Camera.Size m_PreviewSize;
	
	@SuppressWarnings("deprecation")
	public CameraView(Context context) 
	{
		super(context);
		m_Context = context;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
		camera = Camera.open(0);
	}
	
	@SuppressWarnings("deprecation")
	public CameraView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		m_Context = context;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
		camera = Camera.open(0);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		if(m_IsRunningPreview)
			StopCameraPreview();

		Parameters parameters = camera.getParameters();	
		Display display = ((WindowManager)m_Context.getSystemService(m_Context.WINDOW_SERVICE)).getDefaultDisplay();

		Size size = parameters.getPreviewSize();
		
		
		parameters.setPreviewSize(size.width, size.height);	
		//parameters.setPreviewFormat(ImageFormat.RGB_565);	

		if(!m_Context.getResources().getBoolean(R.bool.isTablet))
		{
			switch (display.getRotation()) 
			{
				case Surface.ROTATION_0:
					
					m_Rotation = 0;
					camera.setDisplayOrientation(90);
					break;
				case Surface.ROTATION_90:
					parameters.setPreviewSize(m_PreviewSize.width, m_PreviewSize.height);
					m_Rotation = 90;
					break;
				case Surface.ROTATION_180:
					parameters.setPreviewSize(m_PreviewSize.width, m_PreviewSize.height);
					m_Rotation = 180;
					break;
				case Surface.ROTATION_270:
					parameters.setPreviewSize(m_PreviewSize.width, m_PreviewSize.height);
		            camera.setDisplayOrientation(180);
		            m_Rotation = 270;
					break;	
				default:
					break;
			}
		}
		else
		{
			
			m_Rotation = -90;
		}

		
		camera.setParameters(parameters);
		
		

		try
		{
			camera.setPreviewDisplay(surfaceHolder);
			StartCameraPreview();
			//Camera.Parameters parameters = camera.getParameters();
			//parameters.setPreviewSize(width, height);
			//camera.setParameters(parameters);
		}
		catch(Exception ex)
		{
			Log.w("CameraView", "Exception: ", ex);
		}
		//camera.startPreview();
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		
		setMeasuredDimension(width, height);
		
		if(camera.getParameters().getSupportedPreviewSizes() != null)
		{
			m_PreviewSize = GetOptimalPreviewSize(width, height);
		}
	}

	private Camera.Size GetOptimalPreviewSize(int width, int height)
	{
		List<Camera.Size> previewSizes = camera.getParameters().getSupportedPreviewSizes();
	
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) height / width;
		
		if(previewSizes == null)
			return null;
		
		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		
		int targetHeight = height;
		
		for(Camera.Size size : previewSizes)
		{
			double ratio = (double) size.height / size.width;
			
			if(Math.abs(ratio + targetRatio) > ASPECT_TOLERANCE) continue;
			if(Math.abs(size.height - targetHeight) < minDiff)
			{
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		
		if(optimalSize == null)
		{
			minDiff = Double.MAX_VALUE;
			
			for(Camera.Size size : previewSizes)
			{
				if(Math.abs(size.height - targetHeight) < minDiff)
				{
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		
		return optimalSize;
	}
	
	public float GetRotation()
	{
		return m_Rotation;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{	
		//camera = Camera.open(0);
		//setDisplayOrientation(camera, 90);
		
		try 
		{
			camera.setPreviewDisplay(holder);
			//m_IsRunningPreview = true;
		} 
		catch (IOException e) 
		{
			DestroySurfaceOfCamera();
		}
	}

	protected void setDisplayOrientation(Camera camera, int angle){
	    Method downPolymorphic;
	    try
	    {
	        downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
	        if (downPolymorphic != null)
	            downPolymorphic.invoke(camera, new Object[] { angle });
	    }
	    catch (Exception e1)
	    {
	    }
	}
	
	public void StopCameraPreview()
	{
		if(m_IsRunningPreview && (camera != null))
		{
			camera.stopPreview();
			m_IsRunningPreview = false;
		}
	}
	
	public void StartCameraPreview()
	{
		if(camera == null)
		{
			camera = Camera.open(0);
		}
		
		if(!m_IsRunningPreview && (camera != null))
		{
			camera.startPreview();
			m_IsRunningPreview = true;
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		StopCameraPreview();
		DestroySurfaceOfCamera();
	}
	
	private void DestroySurfaceOfCamera()
	{
		camera.release();
		camera = null;
	}
	
	public void setOneShotPreviewCallback(PreviewCallback callback)
	{
		if(camera != null)
		{
			camera.setOneShotPreviewCallback(callback);
		}
	}
}
