package htw.plantar.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class PlantImageSource implements Serializable
{
	@JsonProperty("Id")
	private int Id = 0;
	
	@JsonProperty("BinarySource")
	private byte[] BinarySource;

	public PlantImageSource() 
	{
		super();
	}

	public PlantImageSource(int id, byte[] binarySource) 
	{
		super();
		Id = id;
		BinarySource = binarySource;
	}

	public int getId() 
	{
		return Id;
	}

	public void setId(int id) 
	{
		Id = id;
	}

	public byte[] getBinarySource() 
	{
		return BinarySource;
	}

	public void setBinarySource(byte[] binarySource) 
	{
		BinarySource = binarySource;
	}
}
