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
	private static final String TEST_OUTPUT_PARAM = "testOutput";
	private static String test_input_param_value;
	private static String test_output_param_value;
	
	
	/**
	 * Test stub for local testing of Java mapping.
	 * @param args
	 */
	public static void main(String[] args) {
		Instant startTime = Instant.now();
		
		try {
			System.out.println("# Running in local test mode...");
			// Get and validate runtime parameters
			handleInputParameters(args);
			
			// Load XML test file
			InputStream is = Xml.load(test_input_param_value);
			
			// Set output
			OutputStream os = new FileOutputStream(new File(test_output_param_value));
			
			// Execute mapping logic
			System.out.println("# ==> Mapping: Start");
			Orchestrator o = new Orchestrator();
			o.execute(is, os);
		} catch (Exception e) {
			System.err.println("Oh noooes..." + e.getMessage());
		} finally {
			System.out.println("# <== Mapping: End");
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
		// Extract runtime parameters
		extractInputParameters(args);
		
		// Validate runtime parameters
		validateInputParameters();
	}

	
	/**
	 * TEST: Validate runtime parameters.
	 * @throws InvalidParameterException
	 */
	private static void validateInputParameters() throws InvalidParameterException {
		String errorMessage = "";
		System.out.println("Validating runtime parameters...");
		
		if (test_input_param_value == null) {
			errorMessage = "Missing \"" + TEST_INPUT_PARAM + "\" parameter in run configurations";	
		} else if(test_output_param_value == null) {
			errorMessage = "Missing \"" + TEST_OUTPUT_PARAM + "\" parameter in run configurations";	
		}
		
		// Throw validation error if any message has been set
		if(errorMessage.length() > 0) {
			throw new InvalidParameterException(errorMessage);
		}
	}

	
	/**
	 * TEST: Extract runtime parameters.
	 * @param args
	 */
	private static void extractInputParameters(String[] args) {
		System.out.println("Extracting runtime parameters...");
		for (String s : args) {
			if (s.contains(TEST_INPUT_PARAM)) {
				test_input_param_value = s.split("=")[1];
				System.out.println("Input data will be read from: " + test_input_param_value);
			} else if(s.contains(TEST_OUTPUT_PARAM)) {
				test_output_param_value = s.split("=")[1];
				System.out.println("Output data will be written to: " + test_output_param_value);
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
