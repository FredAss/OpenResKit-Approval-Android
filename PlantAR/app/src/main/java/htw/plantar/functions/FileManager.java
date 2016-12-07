package htw.plantar.functions;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.plantar.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileManager 
{
	private Context m_Context;
	private static ObjectMapper m_ObjectMapper;
	
	public FileManager(Context context)
	{
		m_Context = context;
		m_ObjectMapper = new ObjectMapper();
	}
	
	public <T> List<T> GetObjectsFromFile(Class<T> classType, String fileName)
	{
		ArrayList<T> objects = new ArrayList<T>();
		String objectJSON = LoadFromExternal(fileName + ".JSON");
		
		if(objectJSON != null)
		{
			try
			{
				JSONArray objectJSONArray = new JSONArray(objectJSON);
				
				for(int i = 0; i < objectJSONArray.length(); i++)
				{
					JSONObject plantObject = objectJSONArray.getJSONObject(i);
					T object = m_ObjectMapper.readValue(plantObject.toString(), classType);
					objects.add(object);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return objects;
	}
	
	private String LoadFromExternal(String fileName) 
	{
		String res = null;
		File file = new File(getAppRootDir(), fileName);
		
		if(!file.exists())
		{
			Log.e("", "file " +file.getAbsolutePath()+ " not found");
			return null;
		}
		
		FileInputStream fis = null;
		BufferedReader inputReader = null;
		
		try 
		{
			fis = new FileInputStream(file);
			inputReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			StringBuilder strBuilder = new StringBuilder();
			String line;
			
			while ((line = inputReader.readLine()) != null) 
			{
				strBuilder.append(line + "\n");
			}
			
			res = strBuilder.toString();
		} 
		catch(Throwable e)
		{
			if(fis!=null)
			{
				try 
				{
					fis.close();
				} 
				catch (IOException ignored) 
				{
					
				}
			}
			
			if(inputReader!= null)
			{
				try 
				{
					inputReader.close();
				} 
				catch (IOException ignored) 
				{
					
				}
			}
		}
		return res;
	}
	
	public File getAppRootDir()
	{
		File appRootDir;
		boolean externalStorageAvailable;
		boolean externalStorageWriteable;
		String state = Environment.getExternalStorageState();
		
		if(Environment.MEDIA_MOUNTED.equals(state))
		{
			externalStorageAvailable = externalStorageWriteable = true;
		}
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
		{
			externalStorageAvailable = true;
			externalStorageWriteable = false;
		} 
		else 
		{
			externalStorageAvailable = externalStorageWriteable = false;
		}
		
		if (externalStorageAvailable && externalStorageWriteable) 
		{
			appRootDir = m_Context.getExternalFilesDir(null);
		} 
		else
		{
			appRootDir = m_Context.getDir("appRootDir", Context.MODE_PRIVATE);
		}
		
		if (!appRootDir.exists()) 
		{
			appRootDir.mkdir();
		}
		
		return appRootDir;
	}
		
	public <T> void SaveObjectsToFile(List<T> objects, String fileName)
	{
		SaveToExternal(SerializeObject(objects), fileName + ".JSON");
	}
	
	private <T> String SerializeObject(List<T> objects)
	{
		String str = null;
		try
		{
			str = m_ObjectMapper.writerWithType(new TypeReference<List<T>>(){}).writeValueAsString(objects);
		}
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return str;
	}
	
	private void SaveToExternal(String content, String fileName) 
	{
		FileOutputStream fos = null;
		Writer out = null;
		
		try 
		{
			File file = new File(getAppRootDir(), fileName);
			fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos, "UTF-8");

			out.write(content);
			out.flush();
		} 
		catch (Throwable e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			if(fos!=null)
			{
				try 
				{
					fos.close();
				} 
				catch (IOException ignored) {}
			}
			if(out!= null)
			{
				try 
				{
					out.close();
				} 
				catch (IOException ignored) 
				{
					
				}
			}
		}
	}
	
	public void SaveDocumentToExternal(byte[] content, String fileName)
	{
		FileOutputStream fos = null;
		Writer out = null;
		
		try 
		{
			File file = new File(getAppRootDir(), fileName);
			fos = new FileOutputStream(file);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bos.write(content);
			bos.writeTo(fos);
			bos.flush();
			bos.close();
			
		} 
		catch (Throwable e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			if(fos!=null)
			{
				try 
				{
					fos.close();
				} 
				catch (IOException ignored) {}
			}
			if(out!= null)
			{
				try 
				{
					out.close();
				} 
				catch (IOException ignored) 
				{
					
				}
			}
		}
	}
		
	public void DeleteDocument(String fileName)
	{
		File a = new File(getAppRootDir() + "/" + fileName);
		a.delete();
	}
	
	
	public void DeleteFiles()
	{
		File directory = getAppRootDir();
		File[] files = directory.listFiles();
		
		for (File file : files) 
		{
			file.delete();
		}
		
		Toast.makeText(m_Context, m_Context.getResources().getString(R.string.deleted_data_string), Toast.LENGTH_SHORT).show();
	}
	
	public void CreateNeededFiles()
	{
		File tessFile = new File(getAppRootDir().getPath() + "/tessdata");
		
		if(!tessFile.exists())
		{
			CreateTessdataFile();
		}
	}
	
	
	private void CreateTessdataFile()
	{
		try
		{
			File tessdataFile = new File(getAppRootDir().getPath() + "/tessdata");
			tessdataFile.mkdirs();
		
			CopyAssets();
		}
		catch(Exception ex)
		{
			
		}	
	}
	
	private void CopyAssets()
	{
		AssetManager assetManager = m_Context.getAssets();
		String[] files = null;
		
		try
		{
			files = assetManager.list("");
		}
		catch(IOException ex)
		{
			Toast.makeText(m_Context, m_Context.getResources().getString(R.string.error_missing_files), Toast.LENGTH_SHORT).show();
		}
		
		for(String filename : files)
		{
			InputStream in = null;
			OutputStream out = null;
			
			try
			{
				in = assetManager.open(filename);
				File outFile= new File(getAppRootDir().getPath() + "/tessdata", filename);
				out = new FileOutputStream(outFile);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			}
			catch(IOException ex)
			{
				
			}
		}
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int read;
		while((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
	}
}
