package dk.radius.catalystone.mapping.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Util {

	/**
	 * Format a "yyyymmdd" date to a "yyyy-mm-dd" date  (SAP to CatalystOne)
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(String date) throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyymmdd");
		SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		String formattedDate = targetFormat.format(sourceFormat.parse(date));
		
		return formattedDate;
	}
}
