package Models;

public class ConAux 
{
	private AuxillaryCondition aux;
	private ConditionInspection con;
	private int inspectionnumber;
	
	public ConAux(AuxillaryCondition aux, ConditionInspection con, int nb) 
	{
		this.aux = aux;
		this.con = con;
		this.inspectionnumber = nb;
	}

	public AuxillaryCondition getAux() {
		return aux;
	}

	public void setAux(AuxillaryCondition aux) {
		this.aux = aux;
	}

	public int getInspectionnumber() {
		return inspectionnumber;
	}

	public void setInspectionnumber(int inspectionnumber) {
		this.inspectionnumber = inspectionnumber;
	}

	public ConditionInspection getCon() {
		return con;
	}

	public void setCon(ConditionInspection con) {
		this.con = con;
	}
	
	
}
