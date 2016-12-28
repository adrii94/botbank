package mx.com.everis.architecture.rest.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.com.everis.dao.IFacebookUsersDao;
import mx.com.everis.pojo.BankClient;

@Controller
@RequestMapping("/data")
public class DataController {

	@Autowired
	IFacebookUsersDao facebookUsersDao;
	
	@RequestMapping(value = "/friendsList", method = RequestMethod.GET)
	public @ResponseBody Collection<BankClient> getAllFriends (){
		return facebookUsersDao.getAllUsersRegistered().values();
	}
}
