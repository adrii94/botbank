package mx.com.everis.architecture.restfb.custom;

import com.restfb.Facebook;

public class MessagingAccountLink {
	@Facebook
	private Sender sender = new Sender();
	@Facebook
	private Recipient recipient = new Recipient();
	
	@Facebook
	private Long timestamp;
	
	@Facebook("account_linking")
	private AccountLinking accountLinking = new AccountLinking();

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public AccountLinking getAccountLinking() {
		return accountLinking;
	}

	public void setAccountLinking(AccountLinking accountLinking) {
		this.accountLinking = accountLinking;
	}
	
	
	
}
