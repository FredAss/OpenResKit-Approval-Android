package com.example.approvaltest.HubDatas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.approvaltest.DataHandler;
import com.example.approvaltest.FileCorrecter;
import com.example.approvaltest.FileManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Models.ConditionInspection;
import Models.Inspection;
import Models.Measure;
import Models.Settings;

public class HubGetData extends AsyncTask<Void, Void, ArrayList<Inspection> >{
	// Initialize
	Models.Settings mysettings;
	JSONArray myarray;
	Context myctx;
	public ArrayList<Inspection> mylist = new ArrayList<Inspection>();
	private static ObjectMapper mymapper = new ObjectMapper();
	
	

	public HubGetData(Context ctx) {
		myctx = ctx;

	}
	
	public void WriteDataToHub()
	{
		new WriteDataInspection().execute((Void[]) null);
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
			HttpConnectionParams.setConnectionTimeout(httpParams, 60000);

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
	protected ArrayList<Inspection> doInBackground(Void... params) {
		mysettings = (Settings) new DataHandler().getObjectFromFile(
				DataHandler.SETTINGS_FILEMANE, myctx);
				
		String collection = "ScheduledTasks/OpenResKit.DomainModel.Approval_Inspection";
		String expand = "OpenResKit.DomainModel.Approval_Inspection/ConditionInspections/EntryDate,OpenResKit.DomainModel.Approval_Inspection/ConditionInspections/Measures,EntryResponsibleSubject,RelatedSeries,RelatedSeries/Begin,RelatedSeries/End,RelatedSeries/RepeatUntilDate,RelatedSeries/SeriesColor,AppointmentResponsibleSubject,DueDate,DueDate/Begin,DueDate/End,EntryDate,EntryDate/Begin,EntryDate/End";
		String filter = null;
		
		try {
		//Get the Array	
		myarray = getJSONArrayFromOdata(mysettings.getURL(), mysettings.getServerport(), mysettings.getUsername(), 
				mysettings.getPassword(), "OpenResKitHub", collection, expand, filter);
		
		
		//Convert to a List				
		for(int i=0; i < myarray.length(); i++)
		{		
			
			JSONObject myobj = myarray.getJSONObject(i);
			Inspection object = mymapper.readValue(myobj.toString(), Inspection.class);
			mylist.add(object);
			
		}
	
		} catch (Exception e) {		
			
		e.printStackTrace();
		}
		return mylist;
	}
	
	public class WriteDataInspection extends AsyncTask<Void, Void, Integer>
	{

		@Override
		protected Integer doInBackground(Void... params) {
						// TODO Auto-generated method stub
		ArrayList<Inspection> mylist1  = new ArrayList<Inspection>();
		mylist1 = new FileManager(myctx).GetInspects("myinspection");
		mylist = new FileCorrecter(myctx, mylist1).correctthelist();
			
		Settings mysettings2 = (Settings)new DataHandler().getObjectFromFile(
				DataHandler.SETTINGS_FILEMANE, myctx);
		
			for (Inspection inspection : mylist1) {
				try
				{
					inspection.setProgress(2);
					ConditionInspection ci = inspection.getConditionInspections().get(0);
					ci.getMeasures().add(new Measure(3, "T", "T", 1, 1, 1, null, null, null, 1));
					
					WriteData(mysettings2.getURL(), mysettings2.getServerport(), mysettings2.getUsername(), mysettings2.getPassword(), "OpenResKitHub", "ScheduledTasks", inspection);
				}
				catch(Exception ex){
					
				}
			}
			
			
			
			
			return 1;
		}
		
	}
	
	
	private static void WriteData(String ip, String port, String username, String password, String endpoint, String collection, Inspection inspection)
	{
		try{
		
		//Http Client Setup
		HttpResponse response;
		HttpParams httpParams = new	BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
		httpParams.setBooleanParameter("http.protocol.expect-continue",false);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		 
		//Erstellen eines JSONObject aus einem POJO
		JSONObject inspectionJO = new JSONObject();
		inspectionJO.put("odata.type",	"OpenResKit.DomainModel.Approval_Inspection");
		inspectionJO.put("Id", inspection.getId());
		inspectionJO.put("ConditionInspections", inspection.getConditionInspections());
		inspectionJO.put("Progress", inspection.getProgress());
		inspectionJO.put("DueDate", inspection.getDueDate());
		inspectionJO.put("EntryResponsibleSubject", inspection.getEntryResponsibleSubject());
		inspectionJO.put("RelatedSeries", inspection.getRelatedSeries());
		
		 
		//Umwandlung in eine HTTP StringEntity
		StringEntity stringEntity =	new	StringEntity(inspectionJO.toString(),HTTP.UTF_8);
		stringEntity.setContentType("application/json");
		 
		//HTTP Request vorbereiten
		HttpPost request = 	null;
		//OpenResKitHub Verbindungsparameter
		request = new HttpPost("http://"+ ip +":"+ port +"/"+ endpoint +"/"+ collection);
		
		//Zum Neuanlegen einer Entität erwartet der  OData Host die Http Methode PUT
		request.setHeader("X-HTTP-Method-Override",	"MERGE");
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type","application/json;odata=verbose");
		//Http Basic Auth Credentials
		request.addHeader(BasicScheme.authenticate(	new	UsernamePasswordCredentials(username, password), "UTF-8",false));
		request.setEntity(stringEntity);
		 
		//Http Request ausführen
		response = httpClient.execute(request);
		HttpEntity responseEntity = response.getEntity();
		if(responseEntity != null) 
		{	    
		//die Antwort enthält die neu erstellte Entität des Odata Servers inklusive der neu vergebenen Id
	 
		String jsonText = EntityUtils.toString(responseEntity, HTTP.UTF_8);
		 
		JSONObject answer = new	JSONObject(jsonText);
		System.out.print(answer);
		}
		}
		catch(Exception ex)
		{
		}
		}
}
