package mx.com.everis.architecture.restfb.custom;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

public class LinkAccountBubble {

	  @Facebook
	  private String title;

	  @Facebook("item_url")
	  private String itemUrl;

	  @Facebook("image_url")
	  private String imageUrl;

	  @Facebook
	  private String subtitle;

	  @Facebook
	  private List<AccountButton> buttons;

	  public LinkAccountBubble(String title) {
	    this.title = title;
	  }

	  public boolean addButton(AccountButton button) {
	    if (buttons == null) {
	      buttons = new ArrayList<AccountButton>();
	    }

	    return buttons.add(button);
	  }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	  
	  
}
