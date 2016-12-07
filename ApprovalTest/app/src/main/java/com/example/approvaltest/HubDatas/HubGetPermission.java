package com.example.approvaltest.HubDatas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.approvaltest.DataHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Models.Permission;
import Models.Settings;

public class HubGetPermission extends AsyncTask<Void, Void, ArrayList<Permission> >{
	// Initialize
	Models.Settings mysettings;
	JSONArray myarray;
	Context myctx;
	public ArrayList<Permission> mylist = new ArrayList<Permission>();
	private static ObjectMapper mymapper = new ObjectMapper();
	
	

	public HubGetPermission(Context ctx) {
		myctx = ctx;

	}
	//Get Connected to the Hub
	public JSONArray getJSONArrayFromOdata(String ip, String port, String username, String password, String endpoint, String collection, String expand, String filter) throws Exception 
	{
		JSONArray returnJSONArray = null;
		String jsonText = null;
		String uriString = null;

		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

			httpParams.setBooleanParameter("http.protocol.expect-continue",
					false);
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);

			if (filter == null) {
				if (expand == null) {
					uriString = "http://" + ip + ":" + port + "/" + endpoint
							+ "/" + collection + "/?$format=json";
				} else {
					uriString = "http://" + ip + ":" + port + "/" + endpoint
							+ "/" + collection + "/?$format=json&$expand="
							+ expand;
				}
			} else {
				if (expand == null) {
					uriString = "http://" + ip + ":" + port + "/" + endpoint
							+ "/" + collection + "/?$format=json&$filter="
							+ filter;
				} else {

					uriString = "http://" + ip + ":" + port + "/" + endpoint
							+ "/" + collection + "/?$format=json&$expand="
							+ expand + "&$filter=" + filter;
				}

			}

			HttpGet request = new HttpGet(uriString);

			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");

			request.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(username, password),
					"UTF-8", false));
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					//jsonText = instream.toString();
					jsonText = ConvertStreamToString(instream);
					instream.close();
				}

				
				
				returnJSONArray = new JSONObject(jsonText)
						.getJSONArray("value");

			} else if (response.getStatusLine().getStatusCode() == 403) {
				Exception e1 = new AuthenticationException(
						"Der Benutzername oder das Passwort für die Authentifizierung am OData Service sind nicht korrekt");
				throw e1;
			}
		}

		catch (Exception e)
		{

			throw e;

		}

		return returnJSONArray;			
	}
	
	private String ConvertStreamToString(InputStream stream) throws Exception
	{
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder strBuilder = new StringBuilder();
		
		String inputStr;
		
		while((inputStr = streamReader.readLine()) != null)
		{
			strBuilder.append(inputStr);	
		}
		
		return strBuilder.toString();	
	}

	@SuppressLint("NewApi")
	@Override
	protected ArrayList<Permission> doInBackground(Void... params) {
		mysettings = (Settings) new DataHandler().getObjectFromFile(
				DataHandler.SETTINGS_FILEMANE, myctx);
				
		String collection = "Permissions";
		String expand = "Plants,Plants/PlantImageSource,AuxillaryConditions,AuxillaryConditions/ConditionInspections";
		String filter = null;
		
		try {
		//Get the Array	
		myarray = getJSONArrayFromOdata(mysettings.getURL(), mysettings.getServerport(), mysettings.getUsername(), 
				mysettings.getPassword(), "OpenResKitHub", collection, expand, filter);
		
		
		//Convert to a List				
		for(int i=0; i < myarray.length(); i++)
		{		
			
			JSONObject myobj = myarray.getJSONObject(i);
			Permission object = mymapper.readValue(myobj.toString(), Permission.class);
			mylist.add(object);
			
		}
	
		} catch (Exception e) {		
			
		e.printStackTrace();
		}
		return mylist;
	}
}
