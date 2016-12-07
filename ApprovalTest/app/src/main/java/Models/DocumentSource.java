package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentSource 
{
	@JsonProperty("Id")
	private int id;

	@JsonProperty("BinarySource")
	private byte[] binarySource;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getBinarySource() {
		return binarySource;
	}

	public void setBinarySource(byte[] binarySource) {
		this.binarySource = binarySource;
	}
}
