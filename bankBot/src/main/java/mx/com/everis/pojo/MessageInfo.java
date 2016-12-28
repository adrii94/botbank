package mx.com.everis.pojo;

public class MessageInfo {
	private String label;
	private String text;
	
	public MessageInfo(String label, String text){
		this.label = label;
		this.text = text;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
