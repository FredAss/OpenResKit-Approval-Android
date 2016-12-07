package htw.plantar.models;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Plant implements Serializable
{
	@JsonProperty("Id")
	private int Id = 0;
	
	@JsonProperty
	private int internalId;
	
	@JsonProperty("Name")
	private String Name = "";
	
	@JsonProperty("Description")
	private String Description = "";
	
	@JsonProperty("Number")
	private String Number = "";
	
	@JsonProperty("Position")
	private String Position = "";
	
	@JsonProperty("PlantImageSource")
	private PlantImageSource PlantImageSource = null;
	
	@JsonProperty("AttachedDocuments")
	private List<Document> AttachedDocuments = null;
	
	@JsonIgnoreProperties("Permissions")
	private List<Permission> Permissions = null;
	
	@JsonProperty("Substances")
	private List<Substance> Substances = null;
	
	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public Plant() 
	{
		super();
	}
	
	public Plant(int id, String name, String number, String position, String description, PlantImageSource plantImageSource, List<Document> attachedDocuments, List<Permission> permissions, List<Substance> substances) 
	{
		super();
		Id = id;
		Name = name;
		Number = number;
		Position = position;
		Description = description;
		PlantImageSource = plantImageSource;
		AttachedDocuments = attachedDocuments;
		Permissions = permissions;
		Substances = substances;
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
	
	public String getDescription() 
	{
		return Description;
	}

	public void setDescription(String description) 
	{
		Description = description;
	}

	public String getNumber() 
	{
		return Number;
	}

	public void setNumber(String number) 
	{
		Number = number;
	}

	public int getInternalId() 
	{
		return internalId;
	}

	public void setInternalId(int internalID) 
	{
		this.internalId = internalID;
	}

	public PlantImageSource getPlantImageSource() 
	{
		return PlantImageSource;
	}

	public void setPlantImageSource(PlantImageSource plantImageSource) 
	{
		PlantImageSource = plantImageSource;
	}

	public List<Document> getAttachedDocuments() 
	{
		return AttachedDocuments;
	}

	public void setAttachedDocuments(List<Document> attachedDocuments) 
	{
		AttachedDocuments = attachedDocuments;
	}

	public List<Permission> getPermissions() 
	{
		return Permissions;
	}

	public void setPermissions(List<Permission> permissions) 
	{
		Permissions = permissions;
	}

	public List<Substance> getSubstances() 
	{
		return Substances;
	}

	public void setSubstances(List<Substance> substances) 
	{
		Substances = substances;
	}
	
	
}
