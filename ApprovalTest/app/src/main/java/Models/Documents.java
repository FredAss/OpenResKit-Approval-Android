package Models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Documents implements Serializable
{

	@JsonProperty("Id")
	private int Id = 0;
	
	@JsonProperty("Name")
	private String Name = "";
	
	@JsonProperty("DocumentSource")
	private DocumentSource DocumentSource = null;
	
	public Documents() 
	{
		super();
	}

	public Documents(int id, String name, DocumentSource documentSource) {
		super();
		Id = id;
		Name = name;
		DocumentSource = documentSource;
	}

	public int getId() 
	{
		return Id;
	}

	public void setId(int id) 
	{
		Id = id;
	}

	public String getName() 
	{
		return Name;
	}

	public void setName(String name) 
	{
		Name = name;
	}

	public DocumentSource getDocumentSource() 
	{
		return DocumentSource;
	}

	public void setDocumentSource(DocumentSource documentSource) 
	{
		DocumentSource = documentSource;
	}
	
	
	
	
}

