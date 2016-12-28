package mx.com.everis.architecture.service;

public interface IFacebookMessage {
	public void serveMessage(String sender, String message, String pageToken);
	public boolean sendMessage(String sender, String message, String pageToken);
}
