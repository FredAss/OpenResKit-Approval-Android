package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MapSource 
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
