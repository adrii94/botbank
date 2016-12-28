package mx.com.everis.architecture.restfb.custom;

import com.restfb.Facebook;

public class AccountLinking {
	
	@Facebook
	private String status;
	
	@Facebook("authorization_code")
	private String authorizationCode;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	
}
