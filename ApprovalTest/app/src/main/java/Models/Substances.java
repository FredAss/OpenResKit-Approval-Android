package Models;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Substances implements Serializable
{
	@JsonProperty("Id")
	private int Id = 0;
	
	@JsonProperty("Name")
	private String Name = "";
	
	@JsonProperty("Description")
	private String Description = "";
	
	@JsonProperty("Category")
	private int Category = 0;
	
	@JsonProperty("Type")
	private int Type = 0;
	
	@JsonProperty("DangerTypes")
	private String DangerTypes = "";
	
	@JsonProperty("Action")
	private String Action = "";
	
	@JsonProperty("RiskPotential")
	private int RiskPotential = 0;

	
	
	public Substances() 
	{
		super();
	}

	public Substances(int id, String name, String description, int category, int type, String dangerTypes, String action, int riskPotential) 
	{
		super();
		Id = id;
		Name = name;
		Description = description;
		Category = category;
		Type = type;
		DangerTypes = dangerTypes;
		Action = action;
		RiskPotential = riskPotential;
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

	public int getCategory() 
	{
		return Category;
	}

	public void setCategory(int category) 
	{
		Category = category;
	}

	public int getType() 
	{
		return Type;
	}

	public void setType(int type) 
	{
		Type = type;
	}

	public String getDangerTypes() 
	{
		return DangerTypes;
	}

	public void setDangerTypes(String dangerTypes) 
	{
		DangerTypes = dangerTypes;
	}

	public String getAction() 
	{
		return Action;
	}

	public void setAction(String action) 
	{
		Action = action;
	}

	public int getRiskPotential() 
	{
		return RiskPotential;
	}

	public void setRiskPotential(int riskPotential) 
	{
		RiskPotential = riskPotential;
	}
	
	
}
