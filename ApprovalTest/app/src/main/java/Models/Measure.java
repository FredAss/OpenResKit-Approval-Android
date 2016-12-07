package Models;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Measure {
	
	@JsonProperty("Id")
	private int Id;
	
	private boolean internalid;
		
	@JsonProperty("Name")
	private String Name;
	
	@JsonProperty("Description")
	private String Description;
	
	@JsonProperty("Progress")
	private int Progress;
	
	@JsonProperty("Priority")
	private int Priority;
	
	@JsonProperty("ResponsibleSubject")
	private int ResponsibleSubject;
	
	@JsonProperty("ConditionInspection")
	private int ConditionInspection;
	
	@JsonProperty("AttachedDocuments")
	private List<Documents> AttachedDocuments;
	
	@JsonProperty("DueDate")
	private Date DueDate;
	
	@JsonProperty("EntryDate")
	private Date EntryDate;
	
	

	public Measure() {
		super();
	}

	public Measure(int id, String name, String description, int progress,
			int priority, int responsibleSubject,
			List<Documents> attachedDocuments, Date dueDate, Date entryDate, int conditionInspection) {
		super();
		Id = id;
		Name = name;
		Description = description;
		Progress = progress;
		Priority = priority;
		ResponsibleSubject = responsibleSubject;
		AttachedDocuments = attachedDocuments;
		DueDate = dueDate;
		EntryDate = entryDate;
	    ConditionInspection = conditionInspection;
	    internalid = false;
	}

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

	public int getProgress() {
		return Progress;
	}

	public void setProgress(int progress) {
		Progress = progress;
	}

	public int getPriority() {
		return Priority;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}

	public int getResponsibleSubject() {
		return ResponsibleSubject;
	}

	public void setResponsibleSubject(int responsibleSubject) {
		ResponsibleSubject = responsibleSubject;
	}

	public List<Documents> getAttachedDocuments() {
		return AttachedDocuments;
	}

	public void setAttachedDocuments(List<Documents> attachedDocuments) {
		AttachedDocuments = attachedDocuments;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}

	public Date getEntryDate() {
		return EntryDate;
	}

	public void setEntryDate(Date entryDate) {
		EntryDate = entryDate;
	}

	public int getConditionInspection() {
		return ConditionInspection;
	}

	public void setConditionInspection(int conditionInspection) {
		ConditionInspection = conditionInspection;
	}

	public boolean isInternalid() {
		return internalid;
	}

	public void setInternalid(boolean internalid) {
		this.internalid = internalid;
	}
	
	
	
	
	
	
	
	


}
