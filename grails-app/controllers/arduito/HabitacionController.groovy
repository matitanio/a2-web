package arduito

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator


@Secured(["ROLE_ARDUITO","IS_AUTHENTICATED_FULLY"])
class HabitacionController {

	def springSecurityService
	
	def create(){
		redirect(action:'nuevaHabitacion')
	}
	
	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[habitacionInstanceList: Habitacion.list(params), habitacionInstanceTotal: Habitacion.count()]
	}
	
	def nuevaHabitacionFlow = {
		paso1{
			on("siguiente") {Paso1Command paso1Command ->
				flow.paso1Command = paso1Command
				if(paso1Command.validate()){
					success()
				}
				else{
					println paso1Command.errors
					error()
				}
				
					
			}.to("paso2")
		}
		paso2{
			
			on('agregarSensor'){Paso2Command paso2Command ->
				
				def sensores = flow.sensores
				if(!sensores){
					sensores = []
					flow.sensores  = sensores
				}
				if(paso2Command.validate()){
					
					sensores << [tipo:paso2Command.tipo,min:paso2Command.min,max:paso2Command.max]
					println sensores
					success()
				}else{
					flow.paso2Command = paso2Command
					error()
				}
				 
			}.to('paso2')
			
		}
		paso3{
			
		}
		
	}
	
	
	def edificiosPorCuenta(Long id){
	
		
		def edificios = Edificio.findAllByOwner(Cuenta.get(id))
		
		render template:'edificios',model:[edificios:edificios]
	}
	
}

class Paso1Command implements Serializable{
	
	Long cuenta
	Long edificio
	String ip
	
	static constraints = {
		cuenta nullable:false
		edificio nullable:false
		ip validator: {value->
			println 'holaaaaaaa' + value
			if(!InetAddressValidator.getInstance().isValid(value)) return['ipNovalida']
		}
	}
}	
	
class Paso2Command implements Serializable{
	
	Long tipo
	Long valorMaximo
	String valorMinimo
	
	static constraints = {
		tipo nullable:false
		valorMaximo nullable:false
		valorMinimo nullable:false
	}
}