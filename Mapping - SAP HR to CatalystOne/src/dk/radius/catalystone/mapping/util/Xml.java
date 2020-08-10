package dk.radius.catalystone.mapping.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dk.radius.catalystone.mapping.employees.DO_EMPLOYEES;
import dk.radius.catalystone.mapping.idoc.DO_HRMD_A07;

public class Xml {

	public static InputStream load(String fileName) throws FileNotFoundException {
		InputStream xml = new FileInputStream(fileName);

		return xml;
	}

	public static DO_HRMD_A07 serialize(InputStream xml) throws JAXBException  {		
		JAXBContext jaxbContext = JAXBContext.newInstance(DO_HRMD_A07.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		DO_HRMD_A07 hrmd_a07 = (DO_HRMD_A07) jaxbUnmarshaller.unmarshal(xml);

		return hrmd_a07;
	}

	public static DO_EMPLOYEES map(DO_HRMD_A07 hrmd_a07) {
		DO_EMPLOYEES employees = Mapper.start(hrmd_a07);

		return employees;
	}

	public static void deserialize(DO_EMPLOYEES employees, OutputStream os) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(DO_EMPLOYEES.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		jaxbMarshaller.marshal(employees, os);

	}
}
