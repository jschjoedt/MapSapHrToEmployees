package dk.radius.catalystone.mapping.idoc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HRMD_A07")
public class DO_HRMD_A07 {
	
	@XmlElement(name = "IDOC")
	public DO_IDOC IDOC;

}
