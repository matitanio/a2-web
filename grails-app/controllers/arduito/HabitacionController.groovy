package arduito

import grails.plugins.springsecurity.Secured

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator
import org.imgscalr.Scalr


@Secured(["ROLE_ARDUITO","IS_AUTHENTICATED_FULLY"])
class HabitacionController {

	def springSecurityService
	def grailsApplication
	
	def create(){
		redirect(action:'nuevaHabitacion')
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[habitacionInstanceList: Habitacion.list(params), habitacionInstanceTotal: Habitacion.count()]
	}

	def nuevaHabitacionFlow = {
		onStart{
			flow.resumen = false
		}
		paso1{
			on("siguiente") {Paso1Command paso1Command ->
				flow.paso1Command = paso1Command
				if(paso1Command.validate()){
					def sensoresValores = [:]
					Sensor.list().collect{
						sensoresValores.put(it.id, [valorMaximo:it.valorMaximo,valorMinimo:it.valorMinimo])
					}
					
					flow.sensoresValores = sensoresValores
					success()
				}
				else{
					error()
				}
			}.to("paso2")
			on('resumen').to('paso7')
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
					sensores << [tipo:paso2Command.tipo,min:paso2Command.valorMinimo,max:paso2Command.valorMaximo,
								uuid:uuid,nombre:sensor.tipo,notificables:[]]
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
			on('atras').to('paso1')
			on('resumen').to('paso7')
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
						def uuid = UUID.randomUUID()
						flow.camaras << [ip:params.ip,uuid:uuid,notificables:[]]
						success()
				}
			}.to('paso3')

			on('eliminarCamara'){
				
				flow.camaras.remove(params.ip)
			}.to('paso3')
			on('siguiente').to('paso4')
			on('atras').to('paso2')
			on('resumen').to('paso7')
		}
		paso4{
			
			on('siguiente'){
				
				def file = request.getFile('file')
				if(flow.resumen && file.bytes.size() == 0){
					success()
				}else{
					def validTypes = [
						'image/jpeg',
						'image/gif',
						'image/png'
					]
					if(!validTypes.contains(file.contentType)){
						
						flash.message = 'No es un tipo de imagen valido'
						error()
					}else{
					
						def nombreArchivo = '/' + new Date().time
						def base = 'tmp/habitacion'
						def urlRelativa =  base + nombreArchivo
						//@negrada
						def imageIn = ImageIO.read(file.inputStream);
						BufferedImage scaledImage = Scalr.resize(imageIn, 600,600);
						def absoluta = grailsApplication.parentContext.getResource(base).file.toString() + nombreArchivo
						ImageIO.write(scaledImage, "jpg", new File(absoluta))
						flow.urlPlanoAbsoluta = absoluta
						flow.urlPlanoRelativa = urlRelativa
						success()
					}
			}
					
			}.to('paso5')
			on('atras').to('paso3')
			on('resumen').to('paso7')
		}
		
		paso5{
			
			on('siguiente'){
				flow.rfid = [notificables:[]]
				flow.dispositivos = DispositivoMovil.createCriteria().list{
					owner{
						eq('cuenta',Cuenta.get(flow.paso1Command.cuenta))
					}
				}
				def cantidadSensoressUbicados = agregarPosicionSensores(flow)
				def cantidadCamarasUbicados = agregarPosicionCamara(flow)
				flow.ubicacion=true
			}.to('paso6')
			on('atras').to('paso4')
			on('resumen'){
				def cantidadSensoressUbicados = agregarPosicionSensores(flow)
				def cantidadCamarasUbicados = agregarPosicionCamara(flow)
			}.to('paso7')
			
		}
		paso6{
			on('siguiente'){
				flow.resumen = true
				flow.sensores.each{
					
					it.notificables = params.list("dispositivos-${it.uuid}")
				}
				
				flow.camaras.each{
					
					it.notificables = params.list("dispositivos-${it.uuid}")
				}
				
				flow.rfid.notificables = params.list("dispositivos-rfid")
				
			}.to('paso7')
			on('resumen').to('paso7')
			on('atras'){
				
				flow.sensores.each{
					
					it.notificables = params.list("dispositivos-${it.uuid}")
				}
				
				flow.camaras.each{
					
					it.notificables = params.list("dispositivos-${it.uuid}")
				}
				
				flow.rfid.notificables = params.list("dispositivos-rfid")
			}.to('paso5')
			on('agregarNotificableSensor'){
			
				def sensorId = params['sensor-uuid']
				def sensor = flow.sensores.find{
					it.uuid = sensorId
				} 	
				sensor.notificables = params.list('notificables')
			}.to('paso6')
			
		}
		paso7{
			on('atras').to('paso6')
			on('general').to('paso1')
			on('sensores').to('paso2')
			on('camaras').to('paso3')
			on('plano').to('paso5')
			on('notificaciones').to('paso6')
			
		}	
		
	}
	
	
	def agregarPosicionSensores(flow){
		
		int validadorCantidadSensores = recorrerListaUbicables(flow.sensores)
		
		validadorCantidadSensores
	}
	
	def agregarPosicionCamara(flow){
		
		int validadorCantidadCamaras = recorrerListaUbicables(flow.camaras)
		validadorCantidadCamaras
	}
	
	def recorrerListaUbicables(lista){
		def validador = 0
		
		lista.each{
			def ubicacion = it.uuid.toString()
			if(ubicacion != '-1:-1'){
				it.ubicacion = params[ubicacion]
				validador++
			} 
		}
		
		validador
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
	Boolean rfid

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