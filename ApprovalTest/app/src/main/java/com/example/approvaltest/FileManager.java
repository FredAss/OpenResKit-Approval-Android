package com.example.approvaltest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

import Models.AuxillaryCondition;
import Models.Inspection;
import Models.Permission;
import Models.ResponsibleSubject;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public ArrayList<Inspection> GetInspects(String filename)
	{
		ArrayList<Inspection> objects = new ArrayList<Inspection>();
		try {
		
		String objectJSON = LoadFromExternal(filename + ".JSON");
		JSONArray objectJSONArray = new JSONArray(objectJSON);
		//Convert to a List				
				for(int i=0; i < objectJSONArray.length(); i++)
				{		
					
					JSONObject myobj = objectJSONArray.getJSONObject(i);
					Inspection object = m_ObjectMapper.readValue(myobj.toString(), Inspection.class);
					objects.add(object);
					
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return objects;
	}
	
	
	public ArrayList<AuxillaryCondition> GetAux(String filename)
	{
		ArrayList<AuxillaryCondition> objects = new ArrayList<AuxillaryCondition>();
		try {
		
		String objectJSON = LoadFromExternal(filename + ".JSON");
		JSONArray objectJSONArray = new JSONArray(objectJSON);
		//Convert to a List				
				for(int i=0; i < objectJSONArray.length(); i++)
				{		
					
					JSONObject myobj = objectJSONArray.getJSONObject(i);
					AuxillaryCondition object = m_ObjectMapper.readValue(myobj.toString(), AuxillaryCondition.class);
					objects.add(object);
					
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return objects;
	}
	
	public ArrayList<Permission> GetPermission(String filename)
	{
		ArrayList<Permission> objects = new ArrayList<Permission>();
		try {
		
		String objectJSON = LoadFromExternal(filename + ".JSON");
		JSONArray objectJSONArray = new JSONArray(objectJSON);
		//Convert to a List				
				for(int i=0; i < objectJSONArray.length(); i++)
				{		
					
					JSONObject myobj = objectJSONArray.getJSONObject(i);
					Permission object = m_ObjectMapper.readValue(myobj.toString(), Permission.class);
					objects.add(object);
					
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return objects;
	}
	
	public ArrayList<ResponsibleSubject> GetRepo(String filename)
	{
		ArrayList<ResponsibleSubject> objects = new ArrayList<ResponsibleSubject>();
		try {
		
		String objectJSON = LoadFromExternal(filename + ".JSON");
		JSONArray objectJSONArray = new JSONArray(objectJSON);
		//Convert to a List				
				for(int i=0; i < objectJSONArray.length(); i++)
				{		
					
					JSONObject myobj = objectJSONArray.getJSONObject(i);
					ResponsibleSubject object = m_ObjectMapper.readValue(myobj.toString(), ResponsibleSubject.class);
					objects.add(object);
					
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return objects;
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
					JSONObject TObject = objectJSONArray.getJSONObject(i);
					T object = m_ObjectMapper.readValue(TObject.toString(), classType);
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
		
	public void SaveInspects(List<Inspection> objects, String filename)
	{
		String str = null;
		
		try
		{
			//m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Inspection.class);
			str = m_ObjectMapper.writerWithType(m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Inspection.class)).writeValueAsString(objects);
			//new TypeReference<ArrayList<Inspection>>(){}).writeValueAsString(objects);
			
			
		}
		
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		SaveToExternal(str, filename+".JSON");
		
		//ArrayList<Inspection> check = GetInspects(filename);
	}
	
	public void SaveAux(ArrayList<AuxillaryCondition> objects, String filename)
	{
		String str = null;
		
		try
		{
			//m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Inspection.class);
			str = m_ObjectMapper.writerWithType(m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, AuxillaryCondition.class)).writeValueAsString(objects);//new TypeReference<ArrayList<Inspection>>(){}).writeValueAsString(objects);
		}
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		SaveToExternal(str, filename+".JSON");
		
		//ArrayList<Inspection> check = GetInspects(filename);
	}
	
	
	public void SavePermission(ArrayList<Permission> objects, String filename)
	{
		String str = null;
		
		try
		{
			//m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Inspection.class);
			str = m_ObjectMapper.writerWithType(m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Permission.class)).writeValueAsString(objects);//new TypeReference<ArrayList<Inspection>>(){}).writeValueAsString(objects);
		}
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		SaveToExternal(str, filename+".JSON");
		
		//ArrayList<Inspection> check = GetInspects(filename);
	}
	
	public void SaveRepo(ArrayList<ResponsibleSubject> objects, String filename)
	{
		String str = null;
		
		try
		{
			//m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Inspection.class);
			str = m_ObjectMapper.writerWithType(m_ObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ResponsibleSubject.class)).writeValueAsString(objects);//new TypeReference<ArrayList<Inspection>>(){}).writeValueAsString(objects);
		}
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		SaveToExternal(str, filename+".JSON");
		
		//ArrayList<Inspection> check = GetInspects(filename);
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
