package dk.radius.catalystone.mapping.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;

import javax.xml.bind.JAXBException;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

import dk.radius.catalystone.mapping.employees.DO_EMPLOYEES;
import dk.radius.catalystone.mapping.idoc.DO_HRMD_A07;
import dk.radius.catalystone.mapping.util.Xml;

public class Orchestrator extends AbstractTransformation {
	
	private static final String TEST_INPUT_PARAM = "testInput";
	private static String test_input_value;
	
	
	/**
	 * Test stub for local testing of Java mapping.
	 * @param args
	 */
	public static void main(String[] args) {
		Instant startTime = Instant.now();
		
		try {
			// Get and validate runtime parameters
			handleInputParameters(args);
			
			// Load XML test file
			InputStream is = Xml.load(test_input_value);
			
			// Set output
			OutputStream os = new FileOutputStream(new File("testData/employees.xml"));
			
			// Execute mapping logic
			Orchestrator o = new Orchestrator();
			o.execute(is, os);
		} catch (Exception e) {
			System.err.println("Oh noooes..." + e.getMessage());
		} finally {
			Instant endTime = Instant.now();
			
			System.out.println("# Total runtime: " + Duration.between(startTime, endTime).toMillis() + " ms #");
		}
	}

	
	/**
	 * TEST: Extract and validate runtime parameters.
	 * @param args
	 * @throws InvalidParameterException
	 */
	private static void handleInputParameters(String[] args) throws InvalidParameterException {
		// Exctract runtime parameters
		extractInputParameters(args);
		validateInputParameters();
	}

	
	/**
	 * TEST: Validate runtime parameters.
	 * @throws InvalidParameterException
	 */
	private static void validateInputParameters() throws InvalidParameterException {
		if (test_input_value == null) {
			throw new InvalidParameterException("Missing \"testInput\" parameter in run configurations");
		}		
	}

	
	/**
	 * TEST: Extract runtime parameters.
	 * @param args
	 */
	private static void extractInputParameters(String[] args) {
		for (String s : args) {
			if (s.contains(TEST_INPUT_PARAM)) {
				test_input_value = s.split("=")[1];
			}
		}
	}

	
	/**
	 * Execute Java mapping logic.
	 * @param in
	 * @param out
	 * @throws JAXBException
	 */
	public void execute(InputStream in, OutputStream out) throws JAXBException {
		// Serialize IDoc XML data into corresponding pojo's
		DO_HRMD_A07 hrmd_a07 = Xml.serialize(in);
		
		// Map SAP IDoc input to CatalystOne output
		DO_EMPLOYEES employees = Xml.map(hrmd_a07);
		
		// De-serialize mapped employee pojo's into CatalystOne output 
		Xml.deserialize(employees, out);
	}
	
	
	/**
	 * Main mapping method inherited and overwritten from AbstractTransformation.class.
	 */
	@Override
	public void transform(TransformationInput in, TransformationOutput out) throws StreamTransformationException {
		try {
			// Call main mapping method
			this.execute(in.getInputPayload().getInputStream(), out.getOutputPayload().getOutputStream());
		} catch (JAXBException e) {
			throw new StreamTransformationException(e.getMessage());
		}		
	}
}
