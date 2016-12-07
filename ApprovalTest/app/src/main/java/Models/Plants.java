package Models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Plants implements Serializable {

	//Initialize the Variables 
	@JsonProperty("Id")
	private int Id=0;	

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
	
	@JsonIgnoreProperties("AttachedDocuments")
	private List<Documents> AttachedDocuments = null;
	
	@JsonProperty("Permissions")
	private List<Models.Permission> Permissions = null;
	
	@JsonIgnoreProperties("Substances")
	private List<Substances> Substances = null;
	
	
	
	public List<Substances> getSubstances() {
		return Substances;
	}

	public void setSubstances(List<Substances> substances) {
		Substances = substances;
	}

	public Plants() 
	{
		super();
	}
	
	public Plants(int id, String name, String number, String position, String description, PlantImageSource plantImageSource, List<Documents> attachedDocuments, List<Permission> permissions, List<Substances> substances) 
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
	
	
	public int getId(){			
		return Id;			
	}

	public void setId(int Id){
		this.Id = Id;
	}
		
	public String getName(){			
		return Name;			
	}

	public void setName(String Name){
		this.Name = Name;
	}
	
	public String getDescription(){			
		return Description;			
	}

	public void setDescription(String Description){
		this.Description = Description;
	}
	
	public String getNumber(){			
		return Number;			
	}

	public void setNumber(String Number){
		this.Number = Number;
	}
	
	public String getPosition(){			
		return Position;			
	}

	public void setPosition(String Position){
		this.Position = Position;
	}	

	public PlantImageSource getPlantImageSource() 
	{
		return PlantImageSource;
	}

	public void setPlantImageSource(PlantImageSource plantImageSource) 
	{
		PlantImageSource = plantImageSource;
	}

	public List<Documents> getAttachedDocuments() 
	{
		return AttachedDocuments;
	}

	public void setAttachedDocuments(List<Documents> attachedDocuments) 
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
	


}
