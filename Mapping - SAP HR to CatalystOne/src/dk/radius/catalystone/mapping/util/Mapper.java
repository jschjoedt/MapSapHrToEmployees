package dk.radius.catalystone.mapping.util;

import dk.radius.catalystone.mapping.employees.DO_EMPLOYEE;
import dk.radius.catalystone.mapping.employees.DO_EMPLOYEES;
import dk.radius.catalystone.mapping.employees.DO_FIELD;
import dk.radius.catalystone.mapping.idoc.DO_E1PITYP;
import dk.radius.catalystone.mapping.idoc.DO_HRMD_A07;

public class Mapper {
	public static DO_EMPLOYEES start(DO_HRMD_A07 hrmd_a07) {
		DO_EMPLOYEES employees = new DO_EMPLOYEES();
		DO_EMPLOYEE employee = new DO_EMPLOYEE();

		employees.EMPLOYEE.add(employee);
		for (DO_E1PITYP type : hrmd_a07.IDOC.E1PLOGI.E1PITYP) {

			if (type.INFTY.equals("0000") && type.E1P0000 != null) {
				employee = mapInfoType0000(employee, type);

			} else if (type.INFTY.equals("0001") && type.E1P0001 != null) {
				employee = mapInfoType0001(employee, type);

			} else if (type.INFTY.equals("0002") && type.E1P0002 != null) {
				employee = mapInfoType0002(employee, type);

			} else if (type.INFTY.equals("0105") && type.E1P0105 != null) {
				employee = mapInfoType0105(employee, type);
			}
		}

		return employees;
	}
	
	private static DO_EMPLOYEE mapInfoType0105(DO_EMPLOYEE employee, DO_E1PITYP type) {
		if (type.SUBTY.equals("9950")) {
			employee.GUID = type.E1P0105.get(0).USRID_LONG;
		} else if (type.SUBTY.equals("0010")) {
			employee = createFildInfo("7", type.E1P0105.get(0).USRID_LONG, employee);
		}

		return employee;
	}

	private static DO_EMPLOYEE mapInfoType0002(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("1092", type.E1P0002.INITS, employee);
		employee = createFildInfo("2", type.E1P0002.NACHN, employee);
		employee = createFildInfo("26", type.E1P0002.NACH2, employee);
		employee = createFildInfo("3", type.E1P0002.VORNA, employee);
		employee = createFildInfo("19", type.E1P0002.GESCH, employee);
		employee = createFildInfo("21", type.E1P0002.GBDAT, employee);
		employee = createFildInfo("1089", type.E1P0002.NATIO, employee);
		return employee;
	}

	private static DO_EMPLOYEE mapInfoType0001(DO_EMPLOYEE employee, DO_E1PITYP type) {
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
		return employee;
	}

	private static DO_EMPLOYEE mapInfoType0000(DO_EMPLOYEE employee, DO_E1PITYP type) {
		employee = createFildInfo("1131", type.E1P0000.PERNR, employee);
		employee = createFildInfo("1029", type.E1P0000.MASSN, employee);
		return employee;
	}


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
