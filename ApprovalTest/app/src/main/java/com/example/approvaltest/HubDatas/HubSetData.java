/*package com.example.approvaltest.HubDatas;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import Models.Measure;

public class HubSetData {
	
	
	private static void WriteData(String ip, String port, String username, String password, String endpoint, String collection, Measure measure)
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
		JSONObject measureJO = new JSONObject();
		measureJO.put("odata.type",	"OpenResKit.DomainModel.Measure");
		measureJO.put("Name", measure.getName());
		measureJO.put("Description", measure.getDescription());
		measureJO.put("Progress", measure.getProgress());
		measureJO.put("Priority", measure.getPriority());
		measureJO.put("DueDate", measure.getDueDate());
		measureJO.put("EntryDate", measure.getEntryDate());
		measureJO.put("ResponsibleSubject", measure.getResponsibleSubject());
		
		 
		//Umwandlung in eine HTTP StringEntity
		StringEntity stringEntity =	new	StringEntity(measureJO.toString(),HTTP.UTF_8);
		stringEntity.setContentType("application/json");
		 
		//HTTP Request vorbereiten
		HttpPost request = 	null;
		//OpenResKitHub Verbindungsparameter
		request = new HttpPost("http://"+ ip +":"+ port +"/"+ endpoint +"/"+ collection);
		
		//Zum Neuanlegen einer Entität erwartet der  OData Host die Http Methode PUT
		request.setHeader("X-HTTP-Method-Override",	"PUT");
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
	*/
	
	


