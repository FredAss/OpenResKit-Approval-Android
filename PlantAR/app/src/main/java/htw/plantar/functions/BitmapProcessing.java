package htw.plantar.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapProcessing 
{	
	public static void SaveBitmap(YuvImage bitmap, Camera camera)
	{
		try
		{
			int width = camera.getParameters().getPreviewSize().width;
			int height = camera.getParameters().getPictureSize().height;
			
			File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Test-After.jpg");
			FileOutputStream fous = new FileOutputStream(file);
			bitmap.compressToJpeg(new Rect(0, 0,  width, height), 100, fous);
			fous.flush();
			fous.close();
		}
		catch(IOException ex)
		{
			
		}
	}
	
	public static void SaveBitmap(Bitmap image, String name)
	{
		try
		{
			File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + name+ ".jpg");
			FileOutputStream fous = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.PNG, 100, fous);
			fous.flush();
			fous.close();
		}
		catch(IOException ex)
		{
			
		}
	}
	
	public static void SavePreviewFrameAsJPEG(byte[] data, Camera camera)
	{
		int format = camera.getParameters().getPreviewFormat();
		
		if(format == ImageFormat.NV21)
		{
			try
			{
				int width = camera.getParameters().getPreviewSize().width;
				int height = camera.getParameters().getPictureSize().height;
					
				YuvImage map = new YuvImage(data, ImageFormat.NV21, width, height, null);
				File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Test-Befor.jpg");	
				FileOutputStream fous = new FileOutputStream(file);
				map.compressToJpeg(new Rect(0, 0, width, height), 100, fous);
				
				fous.flush();
				fous.close();
			}
			catch(IOException ex)
			{
				
			}
		}
	}
	
	public static byte[] GetPreviewFrameData(byte[] data, Camera camera)
	{		
		int width = camera.getParameters().getPreviewSize().width;
		int height = camera.getParameters().getPreviewSize().height;
		
		byte[] previewFrameData = null;
		
		if(previewFrameData == null)
		{
			previewFrameData = LoadPreviewFrame(width, height, data, previewFrameData);
		}
		
		return previewFrameData;
	}
	
	private static byte[] LoadPreviewFrame(int width, int height, byte[] data, byte[] previewFrameData)
	{
		try
		{
			YuvImage map = new YuvImage(data, ImageFormat.NV21, width, height, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			map.compressToJpeg(new Rect(0, 0, width, height), 50, baos);
			Bitmap previewImage = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
			
			Bitmap resizedPreviewImage = Bitmap.createScaledBitmap(previewImage, width, height, false);
			previewFrameData = BitmapConverter.ConvertToNV21(resizedPreviewImage);
							
			return previewFrameData;
		}
		catch (Exception e) 
		{
	        Log.e("DisabledCameraFrame", "Failed to loadPreviewFrame");
	    }
		return previewFrameData;
	}
	
	public static Bitmap CreateBitmapFromByteArray(byte[] imageBuffer, Camera camera)
	{
		int width = camera.getParameters().getPreviewSize().width;
		int height = camera.getParameters().getPreviewSize().height;
		
		YuvImage yuvImage = CreateYUVFromByteArray(imageBuffer, width, height);		
		Bitmap bitmap = CreateBitmapFromYuvImage(yuvImage, width, height);

		return bitmap;
	}
	
	private static YuvImage CreateYUVFromByteArray(byte[] imageBuffer, int width, int height)
	{
		return new YuvImage(imageBuffer, ImageFormat.NV21, width, height, null);
	}
	
	private static Bitmap CreateBitmapFromYuvImage(YuvImage image, int width, int height)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		image.compressToJpeg(new Rect(0, 0, width, height), 50, out);
		byte[] imageByte = out.toByteArray();
		
		Bitmap b = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
		
		return Bitmap.createScaledBitmap(b, 400, 400, false);
	}
	
	public static Bitmap RotateImage(Bitmap image, float rotation)
	{
		Matrix matrix = new Matrix();
		
		if(rotation == 90 || rotation == 180)
		{
			return image;
		}
		
		if(rotation == 0)
		{
			matrix.postRotate(90);
		}
		
		if(rotation == 270)
		{
			matrix.postRotate(180);
		}

		Bitmap rotatedBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
		return rotatedBitmap;
	}
	
	public static String DetectTextFromImage(Bitmap image, Context context)
	{
		FileManager fm = new FileManager(context);
		
		TessBaseAPI baseAPI = new TessBaseAPI();
		baseAPI.init(fm.getAppRootDir().getPath(), "deu");
		baseAPI.setImage(image);
		String recognizedTextFromImage = baseAPI.getUTF8Text();
		baseAPI.end();
		// Throws Illegal state exception ??
		//baseAPI.clear();
				
		return recognizedTextFromImage;
	}
}