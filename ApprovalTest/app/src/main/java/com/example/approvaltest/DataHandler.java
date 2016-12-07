package com.example.approvaltest;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class DataHandler {
	//Class for get and set Data on the intern file system
	
	public final static String SETTINGS_FILEMANE = "settings.approval";
	
	
	
	public final static String MYDATE_FILENAME = "mydate.approval";	
	public final static String HASCHANGED_FILENAME ="haschanged.approval";
			
// Constructor
public DataHandler() {
	super();

}

Context context;
//Object needs to be serializable
	public void saveFileWObject(Object myobject, String filename, Context ctx) {
		FileOutputStream fos;
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(myobject);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object getObjectFromFile(String filename, Context ctx) {
		FileInputStream fis;
		Object obj = null;
		try {
			fis = ctx.openFileInput(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}


}
