package arduito

class DispositivoMovil extends Notificable implements Serializable  {

	
	String pin
	String key
	String codigoRegistro
	static belongsTo = [owner:Usuario]
	transient grailsApplication
	transient androidGcmService
	
	Estado estado = Estado.SIN_VERIFICAR
	static constraints = {
		pin unique:true,nullable:true
		codigoRegistro nullable:true
		key nullable:true
		owner nullable:true
	}

	 def notificar(mensaje) {
		
		def apiKey = grailsApplication.config.android.gcm.api.key
		
		if(estado == Estado.VERIFICADO){
			androidGcmService.sendMessage(mensaje, [key],
			"", apiKey)
		}else{
			log.error('El dispositvo [' + this.id + '] no ha sido verificado aun')
		}
		
    }
	
	def getDescripcion(){
		'Dispositivo Movil de ' + owner.username
	}
}

enum Estado {
	SIN_VERIFICAR,VERIFICANDO,VERIFICADO
}
