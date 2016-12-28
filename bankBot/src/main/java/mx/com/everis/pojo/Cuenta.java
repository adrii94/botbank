package mx.com.everis.pojo;

public class Cuenta {
	private String cuenta;
	private double saldo;
	private String tipoCuenta;
	
	
	public Cuenta(){
		tipoCuenta = "basica";
	}
	
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double d) {
		this.saldo = d;
	}


	public String getTipoCuenta() {
		return tipoCuenta;
	}


	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
}
