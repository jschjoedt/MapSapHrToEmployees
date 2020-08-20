package dk.radius.catalystone.mapping.util;

import java.text.ParseException;

import dk.radius.catalystone.mapping.employees.DO_EMPLOYEE;
import dk.radius.catalystone.mapping.employees.DO_EMPLOYEES;
import dk.radius.catalystone.mapping.employees.DO_FIELD;
import dk.radius.catalystone.mapping.idoc.DO_E1PITYP;
import dk.radius.catalystone.mapping.idoc.DO_HRMD_A07;

public class Mapper {

	/**
	 * Start mapping SAP IDoc data to CatalystOne employee data.
	 * @param hrmd_a07
	 * @return
	 * @throws ParseException 
	 */
	public static DO_EMPLOYEES start(DO_HRMD_A07 hrmd_a07) throws ParseException {
		DO_EMPLOYEES employees = new DO_EMPLOYEES();
		DO_EMPLOYEE employee = new DO_EMPLOYEE();

		// As per agreement with Radius, one IDoc ONLY has one employee
		// This is easy to change as the "E1PLOGI" IDoc segment will correspond to a new employee in a 1..* scenario
		employees.EMPLOYEE.add(employee);
		for (DO_E1PITYP type : hrmd_a07.IDOC.E1PLOGI.E1PITYP) {

			if (type.INFTY.equals("0000") && type.E1P0000 != null) {
				employee = mapInfoType0000(employee, type);
				
			} else if (type.INFTY.equals("0001") && type.E1P0001 != null) {
				employee = mapInfoType0001(employee, type);
				
			} else if (type.INFTY.equals("0002") && type.E1P0002 != null) {
				employee = mapInfoType0002(employee, type);
				
			} else if (type.INFTY.equals("0006") && type.E1P0006 != null) {
				employee = mapInfoType0006(employee, type);
				
			} else if (type.INFTY.equals("0007") && type.E1P0007 != null) {
				employee = mapInfoType0007(employee, type);
				
			} else if (type.INFTY.equals("0009") && type.E1P0009 != null) {
				employee = mapInfoType0009(employee, type);
				
			} else if (type.INFTY.equals("0016") && type.E1P0016 != null) {
				employee = mapInfoType0016(employee, type);
				
			} else if (type.INFTY.equals("0041") && type.E1P0041 != null) {
				employee = mapInfoType0041(employee, type);
				
			} else if (type.INFTY.equals("0105") && type.E1P0105 != null) {
				employee = mapInfoType0105(employee, type);
			}
		}

		// Return fully mapped employee object
		return employees;
	}
	
	
	private static DO_EMPLOYEE mapInfoType0016(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("1099", type.E1P0016.CTTYP, employee);
		employee = createFildInfo("1133", type.E1P0016.CTEDT, employee);
		
		return employee;
	}


	private static DO_EMPLOYEE mapInfoType0041(DO_EMPLOYEE employee, DO_E1PITYP type) throws ParseException {
		employee = createFildInfo("37", Util.formatDate(type.E1P0041.DAT02), employee);
		employee = createFildInfo("38", Util.formatDate(type.E1P0041.DAT05), employee);
		employee = createFildInfo("1091", Util.formatDate(type.E1P0041.DAT06), employee);
		
		return employee;
	}


	private static DO_EMPLOYEE mapInfoType0009(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("1034", type.E1P0009.BANKL, employee);
		employee = createFildInfo("1036", type.E1P0009.BANKN, employee);
		employee = createFildInfo("1030", type.E1P0009.BETRG, employee);
		
		return employee;
	}


	private static DO_EMPLOYEE mapInfoType0007(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("40", type.E1P0007.EMPCT, employee);
		employee = createFildInfo("51", type.E1P0007.WOSTD, employee);
		
		return employee;
	}


	/**
	 * Map info type 0006.
	 * @param employee
	 * @param type
	 * @return
	 */
	private static DO_EMPLOYEE mapInfoType0006(DO_EMPLOYEE employee, DO_E1PITYP type) {
		if (type.SUBTY.equals("1")) {
			employee = createFildInfo("1006", type.E1P0006.get(0).STRAS, employee);
			employee = createFildInfo("1008", type.E1P0006.get(0).ORT01, employee);
			employee = createFildInfo("1007", type.E1P0006.get(0).PSTLZ, employee);
			employee = createFildInfo("24", type.E1P0006.get(0).LAND1, employee);
			employee = createFildInfo("1003", type.E1P0006.get(0).TELNR, employee);
			
		} else if (type.SUBTY.equals("2")) {
			// 1. Emergency contact
			employee = createFildInfo("1010", type.E1P0006.get(0).NAME2, employee);
			employee = createFildInfo("1011", type.E1P0006.get(0).INDRL, employee);
			employee = createFildInfo("1012", type.E1P0006.get(0).TELNR, employee);
			
			//TODO: 2. Emergency contact when test data is present
		}
		
		return employee;
	}

	
	/**
	 * Map info type 0105.
	 * @param employee
	 * @param type
	 * @return
	 */
	private static DO_EMPLOYEE mapInfoType0105(DO_EMPLOYEE employee, DO_E1PITYP type) {
		if (type.SUBTY.equals("9950")) {
			employee.GUID = type.E1P0105.get(0).USRID_LONG;
			if (employee.GUID == null) {
				employee.GUID = "GUID missing!";
			}
		} else if (type.SUBTY.equals("MAIL")) {
			employee = createFildInfo("7", type.E1P0105.get(0).USRID, employee);
		}

		return employee;
	}

	
	/**
	 * Map info type 0002.
	 * @param employee
	 * @param type
	 * @return
	 * @throws ParseException 
	 */
	private static DO_EMPLOYEE mapInfoType0002(DO_EMPLOYEE employee, DO_E1PITYP type) throws ParseException {
		employee = createFildInfo("1092", type.E1P0002.INITS, employee);
		employee = createFildInfo("2", type.E1P0002.NACHN, employee);
		employee = createFildInfo("26", type.E1P0002.NACH2, employee);
		employee = createFildInfo("3", type.E1P0002.VORNA, employee);
		employee = createFildInfo("19", type.E1P0002.GESCH, employee);
		employee = createFildInfo("21", Util.formatDate(type.E1P0002.GBDAT), employee);
		employee = createFildInfo("1089", type.E1P0002.NATIO, employee);
		return employee;
	}

	
	/**
	 * Map info type 0001.
	 * @param employee
	 * @param type
	 * @return
	 */
	private static DO_EMPLOYEE mapInfoType0001(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("1105", type.E1P0001.KOSTL, employee);
		employee = createFildInfo("8", "Teknisk Support", employee); 		//TODO: Remove hardcoding employee = createFildInfo("8", type.E1P0001.ORGEH, employee);
		employee = createFildInfo("1098", type.E1P0001.PLANS, employee);
		employee = createFildInfo("1086", type.E1P0001.ZZTRACK, employee);
		employee = createFildInfo("1071", type.E1P0001.BUKRS, employee);
		employee = createFildInfo("14", type.E1P0001.WERKS, employee);
		employee = createFildInfo("??", type.E1P0001.ZZPLEVEL, employee);
		employee = createFildInfo("1086", type.E1P0001.ZZCLEVEL, employee);
		employee = createFildInfo("1100", type.E1P0001.PERSG, employee);
		employee = createFildInfo("1101", type.E1P0001.PERSK, employee);
		return employee;
	}

	
	/**
	 * Map info type 0000.
	 * @param employee
	 * @param type
	 * @return
	 */
	private static DO_EMPLOYEE mapInfoType0000(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("1131", type.E1P0000.PERNR, employee);
		//TODO: Wrong value in MASSN employee = createFildInfo("1029", type.E1P0000.MASSN, employee);
		return employee;
	}


	/**
	 * Create dynamic "FIELD" element for CatalystOne output
	 * @param id			CatalystOne id to determine data value (e.g. FirstName = 3, LastName = 2)
	 * @param value			Value of provided CatalystOne id
	 * @param employee		Current employee being mapped
	 * @return
	 */
	private static DO_EMPLOYEE createFildInfo(String id, String value, DO_EMPLOYEE employee) {
		if (value != null) {
			DO_FIELD field = new DO_FIELD();
			field.id = id;

			field.DATA.VALUE = value;

			employee.FIELD.add(field);
		}

		return employee;
	}
	
}
