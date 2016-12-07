package Models;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConditionInspection {
	
	@JsonProperty("Id")
	private int Id;
	
	@JsonProperty("AuxillaryConditionId")
	private int AuxillaryConditionId;
	
	@JsonProperty("InspectionId")
	private int InspectionId;
	
	@JsonProperty("Status")
	private Boolean Status;
	
	@JsonProperty("Description")
	private String Description;
	
	@JsonProperty("Measures")
	private List<Measure> Measures;
	
	@JsonProperty("EntryDate")
	private Date EntryDate;

	
	
	
	
	public ConditionInspection() {
		super();
	}

	public ConditionInspection(int id, int auxiliaryConditionId,
			int inspectionId, Boolean status, String description,
			List<Measure> measures, Date entryDate) {
		super();
		Id = id;
		AuxillaryConditionId = auxiliaryConditionId;
		InspectionId = inspectionId;
		Status = status;
		Description = description;
		Measures = measures;
		EntryDate = entryDate;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getAuxiliaryConditionId() {
		return AuxillaryConditionId;
	}

	public void setAuxiliaryConditionId(int auxiliaryConditionId) {
		AuxillaryConditionId = auxiliaryConditionId;
	}

	public int getInspectionId() {
		return InspectionId;
	}

	public void setInspectionId(int inspectionId) {
		InspectionId = inspectionId;
	}

	public Boolean getStatus() {
		return Status;
	}

	public void setStatus(Boolean status) {
		Status = status;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public List<Measure> getMeasures() {
		return Measures;
	}

	public void setMeasures(List<Measure> measures) {
		Measures = measures;
	}

	public Date getEntryDate() {
		return EntryDate;
	}

	public void setEntryDate(Date entryDate) {
		EntryDate = entryDate;
	}
	
	
	
	
	
	




   

}
