package mx.com.everis.db;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import mx.com.everis.pojo.EstatusRegistro;
import mx.com.everis.pojo.BankClient;
import mx.com.everis.pojo.Cuenta;

@Repository
public class DataBase {
	
	private Map<String,BankClient> usuariosBDBank;
	private Map<String,BankClient> usuarios;
	
	public DataBase(){
		usuarios = new TreeMap<String, BankClient>();
		loadInicial();
	}
	
	
	public void addUser(BankClient usuario){
		usuarios.put(usuario.getIdFaceebook(), usuario);
	}
	
	public BankClient searchByIdFacebook(String idFacebook){
		return usuarios.get(idFacebook);
	}
	
	public void loadInicial(){
		usuariosBDBank = new TreeMap<String, BankClient>();
		
		BankClient usuario1 = new BankClient();
		usuario1.setNombre("Moisés Francisco Almanza Aquino");
		usuariosBDBank.put("5510684279", usuario1);
		
		BankClient usuario2 = new BankClient();
		usuario2.setNombre("Alberto Otero García");
		usuariosBDBank.put("34685948824", usuario2);
		
		BankClient usuario3 = new BankClient();
		usuario3.setNombre("Sergio Velarde Mendiola");
		usuariosBDBank.put("5537347540", usuario3);
		
		BankClient usuario4 = new BankClient();
		usuario4.setNombre("Hugo Ignacio Tafolla Salgado");
		usuario4.setEstatusRegistro(EstatusRegistro.COMPLETO);
		usuario4.setIdFaceebook("171528946582184");
		Cuenta cuenta1 = new Cuenta();
		cuenta1.setCuenta("123456");
		cuenta1.setSaldo(123.23);
		cuenta1.setTipoCuenta("ahorro");
		usuario4.añadirCuenta(cuenta1);
		usuario4.setPIN("0000");
		usuario4.setAuth(false);
		usuariosBDBank.put("5526998857", usuario4);
		
//		usuarios.put("5510684279", usuario4);
		
	}
	
	public void deleteAllUsers(){
		this.usuarios.clear();
	}
	
	public void deleteUser(String idFacebook){
		this.usuarios.remove(idFacebook);
	}


	public Map<String, BankClient> getUsuariosBDTelcel() {
		return usuariosBDBank;
	}


	public void setUsuariosBDTelcel(Map<String, BankClient> usuariosBDBank) {
		this.usuariosBDBank = usuariosBDBank;
	}


	public Map<String, BankClient> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(Map<String, BankClient> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
