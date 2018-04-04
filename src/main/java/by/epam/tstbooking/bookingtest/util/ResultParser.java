package by.epam.tstbooking.bookingtest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultParser {

	private final static String RESULT_PARSING_REGEXP = "\\d+( \\d+)?";

	public static int getIntResult(String resultText) {
		int result = 0;
		Pattern pattern = Pattern.compile(RESULT_PARSING_REGEXP);
		Matcher matcher = pattern.matcher(resultText);
		if (matcher.find()) {
			result = Integer.parseInt(matcher.group().replaceAll(" ", ""));
		}
		return result;
	}
}
