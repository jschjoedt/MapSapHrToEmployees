package dk.radius.catalystone.mapping.employees;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EMPLOYEES")
public class DO_EMPLOYEES {
	@XmlElement(name = "EMPLOYEE")
	public List<DO_EMPLOYEE> EMPLOYEE;
	
	public DO_EMPLOYEES() {
		EMPLOYEE = new ArrayList<DO_EMPLOYEE>();
	}
}
