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
					def uuid = UUID.randomUUID()
					def sensor = Sensor.get(paso2Command.tipo.toLong())
					sensores << [tipo:paso2Command.tipo,min:paso2Command.valorMinimo,max:paso2Command.valorMaximo,uuid:uuid,nombre:sensor.descripcion]
					println sensores
					success()
				}else{
					flow.paso2Command = paso2Command
					error()
				}
			}.to('paso2')
			on('eliminarSensor'){

				params.uuid
				def sensor = flow.sensores.find{
					it.uuid = params.uuid
				}

				flow.sensores.remove(sensor)
			}.to('paso2')
			on("siguiente"){success()}.to('paso3')
		}
		paso3{

			on('agregarCamara'){

				def camaras = flow.camaras
		
				if(!camaras)
					flow.camaras = []

				if(flow.camaras.contains(params.ip)){
					flash.ipError = 'Ya existe es ip en esta haitacion'
					error()
				}else if(!InetAddressValidator.getInstance().isValid(params.ip)){
					flash.ipError = 'Debe ser una ip valida'
					error()
				}else{
						flow.camaras << params.ip
						sucsess()
				}
			}.to('paso3')

			on('eliminarCamara'){
				
				flow.camaras.remove(params.ip)
			}.to('paso3')
			on('siguiente').to('paso4')
		}
		paso4{
			
			on('siguiente').to('paso5')
		}
		
		paso5{
			
			on('siguiente').to('paso5')
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
	String valorMaximo
	String valorMinimo

	static constraints = {
		tipo nullable:false
		valorMaximo nullable:false
		valorMinimo nullable:false
	}
}