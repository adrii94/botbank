package mx.com.everis.architecture.utils;

public class ValidateDataType {
	private static final String PATTERN_DIGIT = "^\\d+$";
	
	public static boolean isDigit(String message){
		return message.matches(PATTERN_DIGIT);
	}
}
