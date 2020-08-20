package dk.radius.catalystone.mapping.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Util {

	public static String formatDate(String date) throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyymmdd");
		SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		String formattedDate = targetFormat.format(sourceFormat.parse(date));
		
		return formattedDate;
	}
}
