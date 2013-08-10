package arduito

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator


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