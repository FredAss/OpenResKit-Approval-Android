package Models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class AuxillaryCondition implements Serializable {

//Initialize
	
@JsonProperty("Id")
private int Id;

@JsonProperty("Condition")
private String Condition;

@JsonProperty("Reference")
private String Reference;

@JsonProperty("InEffect")
private String InEffect;

@JsonProperty("ConditionInspections")
private List<ConditionInspection> ConditionInspections;

public int getId() {
	return Id;
}

public void setId(int id) {
	Id = id;
}

public String getCondition() {
	return Condition;
}

public void setCondition(String condition) {
	Condition = condition;
}

public String getReference() {
	return Reference;
}

public void setReference(String reference) {
	Reference = reference;
}

public String getInEffect() {
	return InEffect;
}

public void setInEffect(String inEffect) {
	InEffect = inEffect;
}

public List<ConditionInspection> getConditionInspection() {
	return ConditionInspections;
}

public void setConditionInspection(List<ConditionInspection> conditionInspection) {
	ConditionInspections = conditionInspection;
}


}
