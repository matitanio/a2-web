package arduito

class DispositivoMovil implements Serializable {

	
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
}

enum Estado {
	SIN_VERIFICAR,VERIFICANDO,VERIFICADO
}
