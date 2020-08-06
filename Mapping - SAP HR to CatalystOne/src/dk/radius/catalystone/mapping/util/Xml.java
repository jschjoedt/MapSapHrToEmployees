package dk.radius.catalystone.mapping.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dk.radius.catalystone.mapping.employees.DO_EMPLOYEE;
import dk.radius.catalystone.mapping.employees.DO_EMPLOYEES;
import dk.radius.catalystone.mapping.employees.DO_FIELD;
import dk.radius.catalystone.mapping.idoc.DO_E1PITYP;
import dk.radius.catalystone.mapping.idoc.DO_HRMD_A07;

public class Xml {

	public static InputStream load(String fileName) throws FileNotFoundException {
		InputStream xml = new FileInputStream(fileName);

		return xml;
	}

	public static DO_HRMD_A07 parse(InputStream xml) throws JAXBException  {		
		JAXBContext jaxbContext = JAXBContext.newInstance(DO_HRMD_A07.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		DO_HRMD_A07 hrmd_a07 = (DO_HRMD_A07) jaxbUnmarshaller.unmarshal(xml);

		return hrmd_a07;
	}

	public static DO_EMPLOYEES map(DO_HRMD_A07 hrmd_a07) {
		DO_EMPLOYEES employees = new DO_EMPLOYEES();
		DO_EMPLOYEE employee = new DO_EMPLOYEE();

		employees.EMPLOYEE.add(employee);
		for (DO_E1PITYP type : hrmd_a07.IDOC.E1PLOGI.E1PITYP) {

			if (type.INFTY.equals("0000")) {
				employee = createFildInfo("1131", type.E1P0000.PERNR, employee);
				employee = createFildInfo("1029", type.E1P0000.MASSN, employee);
				
			} else if (type.INFTY.equals("0001")) {
				employee = createFildInfo("1105", type.E1P0001.KOSTL, employee);
				employee = createFildInfo("8", type.E1P0001.ORGEH, employee);
				employee = createFildInfo("1098", type.E1P0001.PLANS, employee);
				employee = createFildInfo("1086", type.E1P0001.ZZTRACK, employee);
				employee = createFildInfo("1071", type.E1P0001.BUKRS, employee);
				employee = createFildInfo("1132", type.E1P0001.WERKS, employee);
				employee = createFildInfo("??", type.E1P0001.ZZPLEVEL, employee);
				employee = createFildInfo("1086", type.E1P0001.ZZCLEVEL, employee);
				employee = createFildInfo("1100", type.E1P0001.PERSG, employee);
				employee = createFildInfo("1101", type.E1P0001.PERSK, employee);
			
			} else if (type.INFTY.equals("0002")) {
				employee = createFildInfo("1092", type.E1P0002.INITS, employee);
				employee = createFildInfo("2", type.E1P0002.NACHN, employee);
				employee = createFildInfo("26", type.E1P0002.NACH2, employee);
				employee = createFildInfo("3", type.E1P0002.VORNA, employee);
				employee = createFildInfo("19", type.E1P0002.GESCH, employee);
				employee = createFildInfo("21", type.E1P0002.GBDAT, employee);
				employee = createFildInfo("1089", type.E1P0002.NATIO, employee);
				
			} else if (type.INFTY.equals("0105") && type.SUBTY.equals("9950")) {
				employee.GUID = type.E1P0105.get(0).USRID_LONG;
			} else if (type.INFTY.equals("0105") && type.SUBTY.equals("0010")) {
				//employee = createFildInfo("7", type.E1P0105.get(0).USRID_LONG, employee);
			}
		}

		return employees;
	}


	public static DO_EMPLOYEE createFildInfo(String id, String value, DO_EMPLOYEE employee) {
		if (value != null) {
			DO_FIELD field = new DO_FIELD();
			field.id = id;

			field.DATA.VALUE = value;

			employee.FIELD.add(field);
		}

		return employee;
	}

	public static void write(DO_EMPLOYEES employees, OutputStream os) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(DO_EMPLOYEES.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		jaxbMarshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		
		jaxbMarshaller.marshal(employees, os);

	}
}
