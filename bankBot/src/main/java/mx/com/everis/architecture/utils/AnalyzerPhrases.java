package mx.com.everis.architecture.utils;

public class AnalyzerPhrases {
	public static String REQUEST_BALANCE = "Balance";
	public static String REQUEST_NOT_FOUND = "Not found";
	public static String REQUEST_HELP = "Help";
	public static String REQUEST_PIN = "PIN";
	public static String REQUEST_CUENTA = "VER CUENTAS";
	public static String REQUEST_BORRAR = "BORRAR CUENTA";
	public static String REQUEST_BASICA = "BASICA";
	public static String REQUEST_AHORRO = "AHORRO";
	
	
	private static String[] WORDS_BALANCE = { "SALDO", "CONSUMO", "BALANCE" };

	private static String[] WORDS_HELP = { "AYUDA", "HELP", "AYÚDAME" };
	
	private static String[] WORDS_CUENTA = { "VER CUENTAS", "CUENTAS"};
	
	private static String[] WORDS_BORRAR = { "BORRAR CUENTA", "QUITAR UNA CUENTA"};
	
	private static String[] WORDS_BASICA = { "CUENTA BASICA", "BASICA"};
	
	private static String[] WORDS_AHORRO = { "CUENTA AHORRO", "AHORRO"};

	public static String getValue(String phrase) {
		for (String word : WORDS_HELP) {
			if (phrase.toUpperCase().contains(word)) {
				return AnalyzerPhrases.REQUEST_HELP;
			}
		}
		for (String word : WORDS_BALANCE) {
			if (phrase.toUpperCase().contains(word)) {
				return AnalyzerPhrases.REQUEST_BALANCE;
			}
		}
		for (String word : WORDS_CUENTA) {
			if (phrase.toUpperCase().contains(word)) {
				return AnalyzerPhrases.REQUEST_CUENTA;
			}
		}
		for (String word : WORDS_BORRAR) {
			if (phrase.toUpperCase().contains(word)) {
				return AnalyzerPhrases.REQUEST_BORRAR;
			}
		}
		for (String word : WORDS_BASICA) {
			if (phrase.toUpperCase().contains(word)) {
				return AnalyzerPhrases.REQUEST_BASICA;
			}
		}
		for (String word : WORDS_AHORRO) {
			if (phrase.toUpperCase().contains(word)) {
				return AnalyzerPhrases.REQUEST_AHORRO;
			}
		}
		
		if(ValidateDataType.isDigit(phrase) && phrase.length() == 4){
			return AnalyzerPhrases.REQUEST_PIN;
		}
		
		return AnalyzerPhrases.REQUEST_NOT_FOUND;
	}
}
