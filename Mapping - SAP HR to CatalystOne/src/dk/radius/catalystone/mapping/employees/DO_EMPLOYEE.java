package dk.radius.catalystone.mapping.employees;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DO_EMPLOYEE {	
	public String GUID;
	
	@XmlElement(name = "FIELD")
	public List<DO_FIELD> FIELD;
	
	public DO_EMPLOYEE() {
		FIELD = new ArrayList<DO_FIELD>();
	}
}
