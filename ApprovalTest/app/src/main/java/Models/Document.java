package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document 
{
	@JsonProperty("Id")
	private int id;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("DocumentSource")
	private DocumentSource documentSource;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DocumentSource getDocumentSource() {
		return documentSource;
	}

	public void setDocumentSource(DocumentSource documentSource) {
		this.documentSource = documentSource;
	}
}
