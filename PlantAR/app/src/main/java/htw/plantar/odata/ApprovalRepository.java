package htw.plantar.odata;

import com.example.plantar.R;
import htw.plantar.functions.FileManager;
import htw.plantar.models.Plant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

@Singleton
public class ApprovalRepository 
{
	private Activity m_Context;
	public List<Plant> m_Plants;
	private SharedPreferences m_Preferences;
	
	@Inject
	public ApprovalRepository(Activity ctx)
	{
		m_Preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		m_Context = ctx;		
		LoadDataFromFiles();
	}
	
	private void LoadDataFromFiles()
	{
		FileManager fm = new FileManager(m_Context);
		m_Plants = fm.GetObjectsFromFile(Plant.class, "Plants");
	}
	
	private boolean IsOnline()
	{
		ConnectivityManager cm = (ConnectivityManager)m_Context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnectedOrConnecting())
		{
			return true;
		}
		return false;
	}
	
	public void DeleteData()
	{
		FileManager fm = new FileManager(m_Context);
		fm.DeleteFiles();
		
		m_Plants.clear();
	}
	
	public void GetDataFromOdataService(Activity _start)
	{
		if(IsOnline())
		{
			new GetData(m_Context).execute((Void[]) null);
		}
	}
	
	private class GetData extends AsyncTask<Void, Void, Boolean>
	{
		private ProgressDialog m_ProgressDialog;
		private Context m_Context;
		private ObjectMapper m_ObjectMapper;
		
		public GetData(Context context)
		{
			super();
			m_Context = context;
			m_ProgressDialog = new ProgressDialog(m_Context);
			m_ObjectMapper = new ObjectMapper();
		}
		
		protected void onPreExecute()
		{
			this.m_ProgressDialog.setMessage(m_Context.getResources().getString(R.string.loading_data_string));
			this.m_ProgressDialog.show();
		}

		protected void onPostExecute(Boolean result) 
		{
			super.onPostExecute(result);

			SaveObjectsToFile();
			
			if (m_ProgressDialog.isShowing()) 
			{
				m_ProgressDialog.dismiss();
			}
		}

		private void SaveObjectsToFile()
		{
			FileManager fm = new FileManager(m_Context);
			fm.SaveObjectsToFile(m_Plants, "Plants");
		}
		
		@Override
		protected Boolean doInBackground(Void... params) 
		{
			m_Plants = new ArrayList<Plant>();
			String defaultIP = m_Preferences.getString("default_url", "none");
			String port = m_Preferences.getString("default_port", "none");
			String username = m_Preferences.getString("auth_user", "none");
			String password = m_Preferences.getString("auth_password", "none");
			
			try
			{
				JSONArray personJSONArray = getJSONArrayFromOdata(defaultIP, port, username, password, "OpenResKitHub", "Plants", "PlantImageSource,AttachedDocuments,AttachedDocuments/DocumentSource,Substances", null);
			
				for(int i = 0; i < personJSONArray.length(); i++)
				{
					JSONObject personJSON = personJSONArray.getJSONObject(i);
					Plant plant = m_ObjectMapper.readValue(personJSON.toString(), Plant.class);
					plant.setInternalId(plant.getId());
					m_Plants.add(plant);
				}
			
			}
			catch(final Exception ex)
			{
				((Activity) m_Context).runOnUiThread(new Runnable() {

					public void run() {
						Toast.makeText(m_Context, m_Context.getResources().getString(R.string.error_occuring_string) + ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

					}
				});
				ex.printStackTrace();
			}
			
			return true;
		}
		
		private JSONArray getJSONArrayFromOdata(String ip, String port, String username, String password, String endpoint, String collection, String expand, String filter) throws Exception
		{
			JSONArray returnJSONArray = null;
			String jsonText = null;
			String uriString = null;
			try {
				HttpParams httpParams = new BasicHttpParams();
				HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
				httpParams.setBooleanParameter("http.protocol.expect-continue", false);
				HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
				
				if(filter == null && expand == null)
				{
					uriString = "http://"+ip+":"+ port +"/" + endpoint +"/"+ collection +"/?$format=json";
				}
			
				if (filter == null && expand != null) 
				{
					uriString = "http://"+ip+":"+ port +"/" + endpoint +"/"+ collection +"/?$format=json&$expand=" + expand;
				} 
				else
				{
					uriString = "http://"+ ip +":"+ port +"/" + endpoint +"/"+ collection +"/?$format=json&$expand=" + expand + "&$filter="+ filter;
				}
				
				
				HttpGet request = new HttpGet(uriString);
				request.setHeader("Accept", "application/json");
				request.setHeader("Content-type", "application/json");
				request.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(username, password), "UTF-8", false));
				HttpClient httpClient = new DefaultHttpClient(httpParams);

				HttpResponse response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode() == 200){
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream instream = entity.getContent();
						jsonText = convertStreamToString(instream);
						instream.close();
					}
					returnJSONArray  = new JSONObject(jsonText).getJSONArray("value");
				}
				else if (response.getStatusLine().getStatusCode() == 403) 
				{
					Exception e1 = new AuthenticationException(m_Context.getResources().getString(R.string.pw_user_error_string));
					throw e1; 
				}
			}
			catch (Exception e) 
			{
				throw e;
			}
			return returnJSONArray;
		}
		
		private String convertStreamToString(InputStream is) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			StringBuilder sb = new StringBuilder();

			String line;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}
}
