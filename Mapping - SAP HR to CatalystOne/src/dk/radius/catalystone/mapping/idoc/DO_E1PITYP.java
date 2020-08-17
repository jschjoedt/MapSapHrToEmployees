package dk.radius.catalystone.mapping.idoc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DO_E1PITYP {
	public String INFTY;
	public String SUBTY;
	
	@XmlElement(name = "E1P0000")
	public DO_E1P0000 E1P0000;
	
	@XmlElement(name = "E1P0001")
	public DO_E1P0001 E1P0001;
	
	@XmlElement(name = "E1P0002")
	public DO_E1P0002 E1P0002;
	
	@XmlElement(name = "E1P0006")
	public List<DO_E1P0006> E1P0006;
	
	@XmlElement(name = "E1P0105")
	public List<DO_E1P0105> E1P0105;
		
	public DO_E1PITYP() {
		E1P0105 = new ArrayList<DO_E1P0105>();
	}
}
