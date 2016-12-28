package mx.com.everis.pojo;

public enum EstatusRegistro {
	INICIO(0),REG_NOMBRE(1), REG_CUENTA(2),  REG_PIN(3), REG_CONFIRM(4), COMPLETO(5), DELETE_CONFIRM(6);
	
	private int estatus;
	
	EstatusRegistro(int estatus){
		this.estatus = estatus;
	}
	
	public int getValue(){
		return this.estatus;
	}
}
