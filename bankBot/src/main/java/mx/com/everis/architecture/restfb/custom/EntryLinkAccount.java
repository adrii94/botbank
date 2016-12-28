package mx.com.everis.architecture.restfb.custom;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.webhook.messaging.MessagingItem;

public class EntryLinkAccount {
	@Facebook
	private String id;

	@Facebook("time")
	private Long rawTime;


	@Facebook
	private List<MessagingAccountLink> messaging = new ArrayList<MessagingAccountLink>();


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Long getRawTime() {
		return rawTime;
	}


	public void setRawTime(Long rawTime) {
		this.rawTime = rawTime;
	}


	public List<MessagingAccountLink> getMessaging() {
		return messaging;
	}


	public void setMessaging(List<MessagingAccountLink> messaging) {
		this.messaging = messaging;
	}
	
	
}
