package mx.com.everis.architecture.restfb.custom;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

public class AccountLinkObject {
	@Facebook
	private String object;

	@Facebook("entry")
	private List<EntryLinkAccount> entryList = new ArrayList<EntryLinkAccount>();

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public List<EntryLinkAccount> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<EntryLinkAccount> entryList) {
		this.entryList = entryList;
	}
	
	
}
