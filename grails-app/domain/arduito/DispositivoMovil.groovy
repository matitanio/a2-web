package arduito

class DispositivoMovil extends Notificable implements Serializable  {

	
	String pin
	String key
	String codigoRegistro
	static belongsTo = [owner:Usuario]
	
	Estado estado = Estado.SIN_VERIFICAR
	static constraints = {
		pin unique:true,nullable:true
		codigoRegistro nullable:true
		key nullable:true
		owner nullable:true
	}

	def notificar() {
		// TODO Auto-generated method stub
		return null;
	}	
	
}

enum Estado {
	SIN_VERIFICAR,VERIFICANDO,VERIFICADO
}
