package mx.com.everis.architecture.restfb.custom;

import com.restfb.Facebook;

public class Recipient {
	@Facebook
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
