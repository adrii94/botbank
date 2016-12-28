package mx.com.everis.architecture.restfb.custom;

import com.restfb.Facebook;

public class AccountButton {

	@Facebook
	private String type;
	
	public static String TYPE_LINK = "account_link";
	public static String TYPE_UNLINK = "account_unlink";


	public AccountButton(String type) {
		setType(type);
	}


	protected void setType(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}
	
	
}
