package mx.com.everis.architecture.rest.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mx.com.everis.architecture.service.IFacebookMessage;
import mx.com.everis.dao.IFacebookUsersDao;
import mx.com.everis.pojo.ResponseSendMessage;
import mx.com.everis.pojo.BankClient;

@Controller
@RequestMapping("/facebook")
@PropertySource("classpath:/app_info.properties")

public class FacebookController {

	@Autowired
	IFacebookMessage facebookMessage;

	@Autowired
	IFacebookUsersDao facebookUsersDao;
	
	@Autowired
	private Environment env;

//	public String pageAccessToken = "EAAYuKceGdYwBAN1y8J4vp9Vsi5ZAUUFTmHso4qvoqoliXNGCBW2qFV5sYdXPG8Kl3eTGQCJdDKWtpesXPxJ9ThZBQpbhATKZADMH1PHYZBLPGNEJ25vqwI6WE4aZAQBXCopmDrqLo4CyZAn54pSc1tZClDWG9ZBZCcxx8WjZCmLIi2zAOKBGeBBEbg";

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public ResponseEntity<ResponseSendMessage> sendMessage (@RequestParam(value="sender") String sender, @RequestParam(value="mensaje")String mensaje){
		ResponseEntity<ResponseSendMessage> response;
		boolean resp = facebookMessage.sendMessage(sender, mensaje, env.getProperty("page_token"));
		ResponseSendMessage responseSend;
		if ( resp ){
			responseSend = new ResponseSendMessage();
			responseSend.setEstatus("OK");
			responseSend.setMensaje(mensaje);
			responseSend.setDestinatarios(new ArrayList<BankClient>());
			for (String idBankClient : facebookUsersDao.getAllUsersRegistered().keySet()) {
				BankClient usuarioBank =  facebookUsersDao.getAllUsersRegistered().get(idBankClient);
				if("-1".equals(sender)){
					responseSend.getDestinatarios().add(usuarioBank);
				}else if(usuarioBank.getIdFaceebook().equals(sender)){
					responseSend.getDestinatarios().add(usuarioBank);	
				}
			}
			response = new ResponseEntity<ResponseSendMessage>(responseSend, HttpStatus.OK);
		}else{
			response = new ResponseEntity<ResponseSendMessage>(HttpStatus.CONFLICT);
		}
		return response;
	}
}
