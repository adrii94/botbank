/**
 * 
 */
package mx.com.everis.architecture.restfb.custom;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.send.TemplatePayload;

/**
 * @author malmanza
 *
 */
public class LinkAccountPayload extends TemplatePayload {
	@Facebook
	private List<LinkAccountBubble> elements;

	public LinkAccountPayload() {
		setTemplateType("generic");
	}

	public boolean addBubble(LinkAccountBubble bubble) {
		if (elements == null) {
			elements = new ArrayList<LinkAccountBubble>();
		}

		return elements.add(bubble);
	}
}
