/**
 * 
 */
package mx.com.everis.architecture.restfb.custom;

import com.restfb.Facebook;

/**
 * @author malmanza
 *
 */
public class LinkAccountButton extends AccountButton{

	@Facebook
	private String url;


	public LinkAccountButton(String webUrl, String type) {
		super(type);
		this.url = webUrl;
	}

}
