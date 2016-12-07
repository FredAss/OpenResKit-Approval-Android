package htw.plantar.models;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;

@SuppressWarnings("serial")
public class Permission implements Serializable 
{
	private int Id = 0;
	private String Name = "";
	private String Description = "";
	private boolean InEffect;
	private int PermissionKind = 0;
	private String FileNumber = "";
	private DateTime DateOfAppliaction;
	private DateTime StartOfApplication;
	private DateTime EndOfPermission;
	private List<Document> AttachedDocuments;
	private List<Plant> Plants;
}
