package Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="odata.type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inspection extends ScheduledTask 
{


@JsonProperty("ConditionInspections")	
private List<ConditionInspection> ConditionInspections;

public Inspection() {
	super();
	}

public Inspection(List<ConditionInspection> conditionInspections) {
	super();
	ConditionInspections = conditionInspections;

}

public List<ConditionInspection> getConditionInspections() {
	return ConditionInspections;
}

public void setConditionInspection(List<ConditionInspection> conditionInspections) {
	ConditionInspections = conditionInspections;
	}


}
