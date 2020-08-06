package dk.radius.catalystone.mapping.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

import dk.radius.catalystone.mapping.employees.DO_EMPLOYEES;
import dk.radius.catalystone.mapping.idoc.DO_HRMD_A07;
import dk.radius.catalystone.mapping.util.Xml;

public class Orchestrator extends AbstractTransformation {

	public static final String IDOC_FILE = "testData/HRMD_A07.xml";
	
	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		InputStream is = Xml.load(IDOC_FILE);
		OutputStream os = new FileOutputStream(new File("testData/employees.xml"));
		
		Orchestrator o = new Orchestrator();
		o.execute(is, os);
	}

	public void execute(InputStream in, OutputStream out) throws JAXBException {
		DO_HRMD_A07 hrmd_a07 = Xml.parse(in);
		DO_EMPLOYEES employees = Xml.map(hrmd_a07);
		Xml.write(employees, out);
	}
	
	@Override
	public void transform(TransformationInput in, TransformationOutput out) throws StreamTransformationException {
		try {
			this.execute(in.getInputPayload().getInputStream(), out.getOutputPayload().getOutputStream());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
