package dk.radius.catalystone.mapping.employees;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class DO_FIELD {
	@XmlAttribute(name = "id")
	public String id;
	
	@XmlElement(name = "DATA")
	public DO_DATA DATA;
	
	public DO_FIELD() {
		DATA = new DO_DATA();
	}
}
