package mx.com.everis.dao;

import java.util.Map;

import mx.com.everis.pojo.BankClient;

public interface IFacebookUsersDao {
	public boolean registerUser(BankClient usuario);
	public BankClient getUserByIdFacebook(String idFacebook);
	public void deleteUsersRegistered();
	public Map<String,BankClient> getAllUsersRegistered();
	public void deleteUserById(String idFacebook);
}
