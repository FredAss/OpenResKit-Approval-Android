package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Permission implements Serializable 
{

	
	
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Permission(int id, String name, String description,
			boolean inEffect, int permissionKind, String fileNumber,
			Date dateOfAppliaction,
			List<AuxillaryCondition> auxillaryconditions,
			Date startOfApplication, Date endOfPermission,
			List<Documents> attachedDocuments, List<Models.Plants> plants) {
		super();
		Id = id;
		Name = name;
		Description = description;
		InEffect = inEffect;
		PermissionKind = permissionKind;
		FileNumber = fileNumber;
		DateOfApplication = dateOfAppliaction;
		Auxillaryconditions = auxillaryconditions;
		StartOfApplication = startOfApplication;
		EndOfPermission = endOfPermission;
		AttachedDocuments = attachedDocuments;
		Plants = plants;
	}

	@JsonProperty("Id")
	private int Id = 0;	

	@JsonProperty("Name")
	private String Name = "";
	
	@JsonProperty("Description")
	private String Description = "";
	
	@JsonProperty("InEffect")
	private boolean InEffect;
	
	@JsonProperty("PermissionKind")
	private int PermissionKind = 0;
	
	@JsonProperty("FileNumber")
	private String FileNumber = "";
	
	@JsonProperty("DateOfApplication")
	private Date DateOfApplication;
	
	@JsonProperty("AuxillaryConditions")
	private List<AuxillaryCondition> Auxillaryconditions;
	
	@JsonProperty("StartOfPermission")
	private Date StartOfApplication;
	
	@JsonProperty("EndOfPermission")
	private Date EndOfPermission;
	
	@JsonIgnoreProperties("AttachedDocuments")
	private List<Documents> AttachedDocuments;
	
	@JsonProperty("Plants")
	private List<Plants> Plants;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public boolean isInEffect() {
		return InEffect;
	}

	public void setInEffect(boolean inEffect) {
		InEffect = inEffect;
	}

	public int getPermissionKind() {
		return PermissionKind;
	}

	public void setPermissionKind(int permissionKind) {
		PermissionKind = permissionKind;
	}

	public String getFileNumber() {
		return FileNumber;
	}

	public void setFileNumber(String fileNumber) {
		FileNumber = fileNumber;
	}

	public Date getDateOfAppliaction() {
		return DateOfApplication;
	}

	public void setDateOfAppliaction(Date dateOfAppliaction) {
		DateOfApplication = dateOfAppliaction;
	}

	public Date getStartOfApplication() {
		return StartOfApplication;
	}

	public void setStartOfApplication(Date startOfApplication) {
		StartOfApplication = startOfApplication;
	}

	public Date getEndOfPermission() {
		return EndOfPermission;
	}

	public void setEndOfPermission(Date endOfPermission) {
		EndOfPermission = endOfPermission;
	}

	public List<Documents> getAttachedDocuments() {
		return AttachedDocuments;
	}

	public void setAttachedDocuments(List<Documents> attachedDocuments) {
		AttachedDocuments = attachedDocuments;
	}

	public List<Plants> getPlants() {
		return Plants;
	}

	public void setPlants(List<Plants> plants) {
		Plants = plants;
	}
	
	public List<AuxillaryCondition> getAuxillaryconditions() {
		return Auxillaryconditions;
	}

	public void setAuxillaryconditions(List<AuxillaryCondition> auxillaryconditions) {
		this.Auxillaryconditions = auxillaryconditions;
	}

	
}