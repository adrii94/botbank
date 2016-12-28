package mx.com.everis.architecture.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.ws.rs.core.Request;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.restfb.Connection;

//import org.omg.CORBA.Environment;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import com.restfb.types.send.Bubble;
import com.restfb.types.send.ButtonTemplatePayload;
import com.restfb.types.send.GenericTemplatePayload;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.PostbackButton;
import com.restfb.types.send.QuickReply;
import com.restfb.types.send.TemplateAttachment;
import com.restfb.types.send.WebButton;
import com.restfb.types.send.WebviewHeightEnum;

import mx.com.everis.architecture.service.IFacebookMessage;
import mx.com.everis.architecture.utils.AnalyzerPhrases;
import mx.com.everis.architecture.utils.ValidateDataType;
import mx.com.everis.dao.IFacebookUsersDao;
import mx.com.everis.pojo.EstatusRegistro;
import mx.com.everis.pojo.MessageInfo;
import mx.com.everis.pojo.UsuarioFB;
import mx.com.everis.pojo.BankClient;
import mx.com.everis.pojo.Cuenta;

@Service
@PropertySource("classpath:/mensajes.properties")
public class FacebookMessageImpl implements IFacebookMessage {

	@Autowired
	private IFacebookUsersDao facebookUsersDao;
	@Autowired
	Environment env;

	@Override
	public void serveMessage(String sender, String message, String pageToken) {
		boolean autenticado = false;
		FacebookClient facebookClient = new DefaultFacebookClient(pageToken, Version.VERSION_2_6);
		if (facebookUsersDao.getUserByIdFacebook(sender) == null) {
			String qry = "https://graph.facebook.com/v2.8/" + sender + "?access_token=" + pageToken;//
			URL url;
			try {
				url = new URL(qry);
				ObjectMapper mapper = new ObjectMapper();
				UsuarioFB userFB = mapper.readValue(url, UsuarioFB.class);
				System.out.println(userFB.getFirst_name());
				// Proceso de registro
				BankClient usuario = new BankClient();
				usuario.setIdFaceebook(sender);
				usuario.pushMessage(message);
				usuario.setNombre(userFB.getFirst_name());
				usuario.setEstatusRegistro(EstatusRegistro.REG_PIN);
				
				facebookUsersDao.registerUser(usuario); // se agrega a la lista
														// de
														// usuarios con estatus
														// = 0
														// (inicio), se envía
														// mensaje inicial.

				sendSimpleMessage(sender, env.getProperty("mensaje_bienvenida_1") + usuario.getNombre()
						+ env.getProperty("mensaje_bienvenida"), pageToken);
				sendSimpleMessage(sender, env.getProperty("introducir_pin"), pageToken);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (facebookUsersDao.getUserByIdFacebook(sender)
				.getEstatusRegistro() == EstatusRegistro.REG_PIN) {
			if ( ValidateDataType.isDigit(message)) {
				BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
				temp.setPIN(message);
				sendSimpleMessage(sender, env.getProperty("pin_registrado") + message + ". " + env.getProperty("nuevo_pin"), pageToken);
				temp.setEstatusRegistro(EstatusRegistro.REG_CUENTA);
			}else{
				sendSimpleMessage(sender, env.getProperty("mensaje_datos_invalidos"), pageToken);
			}

		} else if (facebookUsersDao.getUserByIdFacebook(sender)
				.getEstatusRegistro() == EstatusRegistro.DELETE_CONFIRM) {
			if (message.toUpperCase().equals("SI")) {
				facebookUsersDao.deleteUserById(sender);
				sendSimpleMessage(sender, env.getProperty("texto_despedida"), pageToken);
			} else {
				BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
				temp.setEstatusRegistro(EstatusRegistro.COMPLETO);
			}

		}else if( facebookUsersDao.getUserByIdFacebook(sender).getEstatusRegistro() == EstatusRegistro.REG_CUENTA){
			if ( ValidateDataType.isDigit(message)) {
				BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
				temp.setEstatusRegistro(EstatusRegistro.REG_CONFIRM);
				Cuenta cuenta1 = new Cuenta();
				cuenta1.setCuenta(message);
				cuenta1.setSaldo(0);
				temp.añadirCuenta(cuenta1);
			}else{
				sendSimpleMessage(sender, env.getProperty("mensaje_datos_invalidos").concat("\n").
						concat(env.getProperty("registro_pregunta_a")), pageToken);
			}
			
		}else if (facebookUsersDao.getUserByIdFacebook(sender).getEstatusRegistro() == EstatusRegistro.REG_CONFIRM) {
			if (ValidateDataType.isDigit(message)) {
				BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
				temp.setEstatusRegistro(EstatusRegistro.COMPLETO);
				sendSimpleMessage(sender, temp.getNombre() + " " + env.getProperty("registro_pregunta_d"), pageToken);
				sendSimpleMessage(sender, env.getProperty("registro_pregunta_d_2"), pageToken);

			} else {
				sendSimpleMessage(sender, env.getProperty("mensaje_datos_invalidos"), pageToken);
			}
		} else if (facebookUsersDao.getUserByIdFacebook(sender).getEstatusRegistro() == EstatusRegistro.COMPLETO) {
			if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_BALANCE)) {
				
				// TODO Mostrar Mock
				
				MessageInfo messages[] = { new MessageInfo("Balance actual", "536,56 €") };
				sendGenericMessage(sender, pageToken, messages);
				
				BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
				sendSimpleMessage(sender, temp.getNombre() + " " + env.getProperty("mensaje_postRespuesta"), pageToken);

			} else {
				sendSimpleMessage(sender, env.getProperty("texto_no_reconocido"), pageToken);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				sendMenuPostPregunta(sender, pageToken);
			}
		}else if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_PIN)) {
			
			BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
			if (temp.getPIN() == message){
				temp.setAuth(true);
				sendMenuCuenta(sender, pageToken);
			}else
				sendSimpleMessage(sender, env.getProperty("pin_incorrecto"), pageToken);
			
		}else if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_CUENTA)) {
			sendSimpleMessage(sender, env.getProperty("introducir_pin"), pageToken);

		}else if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_BORRAR)) {
			// TODO Pedir al usuario que meta la cuenta a borrar
			BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
			/*if (temp.getNumeroCuenta().size() > 1) {
				String text = "";
			
				if (ValidateDataType.isDigit(text)) {
					temp.borrarCuenta(text);
				} else {
					sendSimpleMessage(sender, env.getProperty("mensaje_datos_invalidos"), pageToken);
				}
			}else {
				sendSimpleMessage(sender, env.getProperty("solo_una_cuenta"), pageToken);
				sendMenuPrincipal(sender, pageToken);
			}*/
		}else if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_BASICA)) {

			BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
			if (temp.isAuth() == true){
				temp.getNumeroCuenta("basica");
				// TODO Mostrar mock
				
				MessageInfo messages[] = { new MessageInfo("Saldo actual cuenta básica", "300,40 €") };
				sendGenericMessage(sender, pageToken, messages);
				
				temp.setAuth(false);
			}else
				sendSimpleMessage(sender, env.getProperty("introducir_pin"), pageToken);
				
			
		}else if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_AHORRO)) {

			BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
			if (temp.isAuth() == true){
				temp.getNumeroCuenta("ahorro");
				// TODO Mostrar mock
				
				MessageInfo messages[] = { new MessageInfo("Saldo actual cuenta ahorro", "3.039,98 €") };
				sendGenericMessage(sender, pageToken, messages);
				
				temp.setAuth(false);
			}else
				sendSimpleMessage(sender, env.getProperty("introducir_pin"), pageToken);
				
		}else if (message != null && AnalyzerPhrases.getValue(message).equals(AnalyzerPhrases.REQUEST_HELP)) {
			sendMenuPrincipal(sender, pageToken);
		}
	}

	public void sendMenuCuenta (String sender, String pageToken){
		System.out.println("Menú tipo de cuenta");
		GenericTemplatePayload payload = new GenericTemplatePayload();
		
		ButtonTemplatePayload button = new ButtonTemplatePayload("¿Qué tipo de cuenta quieres consultar?");
		
		BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
		for (int i = 0; i < temp.getCuentas().size(); i++){
			if (temp.getCuentas().get(i).getTipoCuenta() == "ahorro"){
				PostbackButton buttonAhorro = new PostbackButton("AHORRO", "AHORRO");
				button.addButton(buttonAhorro);
			} else if (temp.getCuentas().get(i).getTipoCuenta() == "basica"){
				PostbackButton buttonBasica = new PostbackButton("BASICA", "BASICA");
				button.addButton(buttonBasica);
			}
		}
		
		TemplateAttachment tA = new TemplateAttachment(button);
		Message msg = new Message(tA);

		IdMessageRecipient recipient = new IdMessageRecipient(sender);

		FacebookClient pageClient = new DefaultFacebookClient(pageToken, Version.VERSION_2_6);

		pageClient.publish("me/messages", FacebookType.class, Parameter.with("recipient", recipient), // the
																										// id
																										// or
																										// phone
																										// recipient
				Parameter.with("message", msg)); // one of the messages from
												// above
	
	}

	public void sendMenuPostPregunta(String sender, String pageToken) {
		System.out.println("Opciones para menu general");
		GenericTemplatePayload payload = new GenericTemplatePayload();

		ButtonTemplatePayload button = new ButtonTemplatePayload("¿En qué más te puedo ayudar? 👇");
		PostbackButton buttonBalance = new PostbackButton("BALANCE", "BALANCE");
		button.addButton(buttonBalance);
		
		PostbackButton buttonVerCuentas = new PostbackButton("VER CUENTAS", "VERCUENTAS");
		button.addButton(buttonVerCuentas);
		
		/*PostbackButton buttonCuenta = new PostbackButton("AÑADIR CUENTA", "AÑADIRCUENTA");
		button.addButton(buttonCuenta);
		
		BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
		if (temp.getCuentas().size() > 1){
			PostbackButton buttonBorrar = new PostbackButton("BORRAR CUENTA", "BORRARCUENTA");
			button.addButton(buttonBorrar);
		}*/
		
		PostbackButton buttonAyuda = new PostbackButton("AYUDA", "AYUDA");
		button.addButton(buttonAyuda);
				
		TemplateAttachment tA = new TemplateAttachment(button);
		Message msg = new Message(tA);

		IdMessageRecipient recipient = new IdMessageRecipient(sender);

		FacebookClient pageClient = new DefaultFacebookClient(pageToken, Version.VERSION_2_6);

		pageClient.publish("me/messages", FacebookType.class, Parameter.with("recipient", recipient), // the
																										// id
																										// or
																										// phone
																										// recipient
				Parameter.with("message", msg)); // one of the messages from
												// above
	}
	
	public void sendMenuPrincipal(String sender, String pageToken) {
		System.out.println("Opciones para menu general");
		GenericTemplatePayload payload = new GenericTemplatePayload();

		ButtonTemplatePayload button = new ButtonTemplatePayload("\uD83E\uDD16 ¡Hola "+facebookUsersDao.getUserByIdFacebook(sender).getNombre()+"! Soy tu asistente personalizado. ¿en qué te puedo ayudar? 👇");
		PostbackButton buttonBalance = new PostbackButton("BALANCE", "BALANCE");
		button.addButton(buttonBalance);
		
		PostbackButton buttonVerCuentas = new PostbackButton("VER CUENTAS", "VERCUENTAS");
		button.addButton(buttonVerCuentas);
		
		/*PostbackButton buttonCuenta = new PostbackButton("AÑADIR CUENTA", "AÑADIRCUENTA");
		button.addButton(buttonCuenta);
		
		BankClient temp = facebookUsersDao.getUserByIdFacebook(sender);
		if (temp.getCuentas().size() > 1){
			PostbackButton buttonBorrar = new PostbackButton("BORRAR CUENTA", "BORRARCUENTA");
			button.addButton(buttonBorrar);
		}*/
		
		PostbackButton buttonAyuda = new PostbackButton("AYUDA", "AYUDA");
		button.addButton(buttonAyuda);
		
		TemplateAttachment tA = new TemplateAttachment(button);
		Message msg = new Message(tA);

		IdMessageRecipient recipient = new IdMessageRecipient(sender);

		FacebookClient pageClient = new DefaultFacebookClient(pageToken, Version.VERSION_2_6);

		pageClient.publish("me/messages", FacebookType.class, Parameter.with("recipient", recipient), // the
																										// id
																										// or
																										// phone
																										// recipient
				Parameter.with("message", msg)); // one of the messages from
													// above
	}

	public void sendSimpleMessage(String sender, String message, String pageToken) {
		IdMessageRecipient recipient = new IdMessageRecipient(sender);
		Message simpleTextMessage = new Message(message);
		FacebookClient pageClient = new DefaultFacebookClient(pageToken, Version.VERSION_2_6);

		pageClient.publish("me/messages", FacebookType.class, Parameter.with("recipient", recipient), // the
																										// id
																										// or
																										// phone
																										// recipient
				Parameter.with("message", simpleTextMessage)); // one of the
																// messages from
																// above
	}

	public void sendGenericMessage(String sender, String pageToken, MessageInfo... messages) {
		GenericTemplatePayload payload = new GenericTemplatePayload();
		for (MessageInfo message : messages) {
			Bubble bubble = new Bubble(message.getLabel());
			bubble.setSubtitle(message.getText());
			payload.addBubble(bubble);
		}

		TemplateAttachment tA = new TemplateAttachment(payload);
		Message msg = new Message(tA);

		IdMessageRecipient recipient = new IdMessageRecipient(sender);

		FacebookClient pageClient = new DefaultFacebookClient(pageToken, Version.VERSION_2_6);

		pageClient.publish("me/messages", FacebookType.class, Parameter.with("recipient", recipient), // the
																										// id
																										// or
																										// phone
																										// recipient
				Parameter.with("message", msg)); // one of the messages from
													// above
	}

	@Override
	public boolean sendMessage(String sender, String message, String pageToken) {
		boolean resp = true;
		try {
			if (sender.equals(
					"-1")) {/*
							 * En caso de que la seleccion sea -1, el mensaje se
							 * enviará a todos los usuarios registrados
							 */
				for (String idBankClient : facebookUsersDao.getAllUsersRegistered().keySet()) {
					BankClient usuarioTelcel = facebookUsersDao.getAllUsersRegistered().get(idBankClient);
					this.sendSimpleMessage(usuarioTelcel.getIdFaceebook(), message, pageToken);
				}
			} else {
				this.sendSimpleMessage(sender, message, pageToken);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			resp = false;
		}
		return resp;
	}
}
