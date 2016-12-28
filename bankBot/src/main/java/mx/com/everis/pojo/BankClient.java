package mx.com.everis.pojo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BankClient {
	private String nombre;
	private String idFaceebook;
	private ArrayList<Cuenta> cuentas;
	private String PIN;
	private boolean auth;
	private EstatusRegistro estatusRegistro; // 0, inicio | 1, ingreso cuenta | 2, ingreso celular
	
	private final int MAX_MESSAGES = 10;
	
	
	private Queue<String> mensajes;
	
	public BankClient(){
		mensajes = new LinkedList<String>();
		estatusRegistro = EstatusRegistro.REG_PIN;
		cuentas = new ArrayList<Cuenta>();
		auth = false;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdFaceebook() {
		return idFaceebook;
	}
	public void setIdFaceebook(String idFaceebook) {
		this.idFaceebook = idFaceebook;
	}
	
	
	public EstatusRegistro getEstatusRegistro() {
		return estatusRegistro;
	}

	public void setEstatusRegistro(EstatusRegistro estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	public Queue<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Queue<String> mensajes) {
		this.mensajes = mensajes;
	}

	public boolean pushMessage(String message){
		if ( this.mensajes.size() >= MAX_MESSAGES ){
			this.mensajes.poll();
		}
		return this.mensajes.offer(message);
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String PIN) {
		this.PIN = PIN;
	}

	public Cuenta getNumeroCuenta(String tipo) {
		int i = 0;
		boolean encontrado = false;
		while (i < cuentas.size() && !encontrado){
			if (cuentas.get(i).getTipoCuenta() == tipo)
				encontrado = true;
		}
		return cuentas.get(i);
	}
	
	public void añadirCuenta (Cuenta cuenta){
		this.cuentas.add(cuenta);
	}
	
	public boolean borrarCuenta (String cuenta){
		boolean encontrado = false;
		int i = 0;
		while (i < cuentas.size() && !encontrado){
			if (cuentas.get(i).equals(cuenta)){
				encontrado = true;
			}
		}
		return encontrado;
	}
	
	public ArrayList<Cuenta> getCuentas(){
		return cuentas;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}
}
