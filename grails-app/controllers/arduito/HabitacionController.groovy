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
	def habitacionService

	def create(){
		redirect(action:'nuevaHabitacion')
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[habitacionInstanceList: Habitacion.list(params), habitacionInstanceTotal: Habitacion.count()]
	}

	def nuevaHabitacionFlow = {
		onStart{ flow.resumen = false }
		paso1{
			on("siguiente") {Paso1Command paso1Command ->
				return doStep('doPaso1',delegate,[flow:flow,paso1Command:paso1Command])
			}.to("paso2")
			on('resumen'){Paso1Command paso1Command ->
				return doStep('doPaso1',delegate,[flow:flow,paso1Command:paso1Command])
			}.to('paso7')
		}
		paso2{

			on('agregarSensor'){Paso2Command paso2Command ->
				return doStep('doAgregarSensor',delegate,[flow:flow,paso2Command:paso2Command])
			}.to('paso2')
			on('eliminarSensor'){

				return doStep('doEliminarSensor',delegate,[flow:flow,params:params])
			}.to('paso2')
			on("siguiente"){Paso2Command paso2Command ->
				if(paso2Command.tipo)
					return doStep('doAgregarSensor',delegate,[flow:flow,paso2Command:paso2Command])
				else{
					success()
				}
			}.to('paso3')
			on('atras').to('paso1')
			on('resumen').to('paso7')
		}
		paso3{

			on('agregarCamara'){
				return doStep('doAgregarCamara',delegate,[flow:flow])
			}.to('paso3')

			on('eliminarCamara'){
				def camara = flow.camaras.find{
					it.ip == params.ip
				}
				flow.camaras.remove(camara)
			}.to('paso3')
			on('siguiente'){
				if(params.ip.replaceAll(" ", "") != ""){
					return doStep('doAgregarCamara',delegate,[flow:flow])
				}
			}.to('paso4')
			on('atras').to('paso2')
			on('resumen').to('paso7')
		}
		paso4{

			on('siguiente'){

				return doStep('doPaso4',delegate,[flow:flow])
			}.to('paso5')
			on('atras').to('paso3')
			on('resumen').to('paso7')
		}

		paso5{

			on('siguiente'){
				flow.dispositivos = DispositivoMovil.createCriteria().list{
					owner{
						eq('cuenta',Cuenta.get(flow.paso1Command.cuenta))
					}
				}
				return doStep('doPaso5',delegate,[flow:flow])
			}.to('paso6')
			on('atras').to('paso4')
			on('resumen'){
				return doStep('doPaso5',delegate,[flow:flow])
			}.to('paso7')
		}
		paso6{
			on('siguiente'){
				return doStep('doPaso6',delegate,[flow:flow])
			}.to('paso7')
			on('resumen').to('paso7')
			on('atras'){
				return doStep('doPaso6',delegate,[flow:flow])
			}.to('paso5')
		}
		paso7{
			on('atras'){flow.resumen = true}.to('paso6')
			on('general'){flow.resumen = true}.to('paso1')
			on('sensores'){flow.resumen = true}.to('paso2')
			on('camaras'){flow.resumen = true}.to('paso3')
			on('plano'){flow.resumen = true}.to('paso5')
			on('notificaciones'){flow.resumen = true}.to('paso6')
			on('terminar'){doSave(flow)}.to('finalizar')
		}
		finalizar{
			redirect (action: "list")
		}
	}
	def doSave(flow){
		
		def parametros = [:]
		parametros.cuenta = flow.paso1Command.cuenta
		parametros.edificio = flow.paso1Command.edificio
		parametros.ip = flow.paso1Command.ip
		parametros.piso = flow.paso1Command.piso
		parametros.numero = flow.paso1Command.numero
		parametros.rfid = flow.paso1Command.rfid
		parametros.sensores = flow.sensores
		parametros.camaras = flow.camaras
		parametros.plano = flow.urlPlanoAbsoluta
		parametros.rfid = [contiene:flow.paso1Command.rfid,notificables:flow.rfid.notificables]
		habitacionService.crear(parametros)
	}
	
	def doStep(stepNumber,delegate,parameters){

		def stepClosure = this."$stepNumber"
		stepClosure.delegate = delegate
		return stepClosure.call(parameters)
	}
	private doPaso1 = {parameters->

		parameters.flow.paso1Command = parameters.paso1Command
		if(parameters.paso1Command.validate()){
			def sensoresValores = [:]
			Sensor.list().collect{
				sensoresValores.put(it.id, [valorMaximo:it.valorMaximo,valorMinimo:it.valorMinimo])
			}
			if(parameters.flow.paso1Command){
				parameters.flow = [notificables:[]]
			}
			parameters.flow.sensoresValores = sensoresValores
			return success()
		}
		else{
			return error()
		}
	}

	private doAgregarSensor = {parameters ->

		def flow = parameters.flow
		def paso2Command = parameters.paso2Command

		def sensores = flow.sensores
		if(!sensores){
			sensores = []
			flow.sensores  = sensores
		}
		if(paso2Command.validate()){
			def uuid = UUID.randomUUID()
			def sensor = Sensor.get(paso2Command.tipo.toLong())
			sensores << [tipo:paso2Command.tipo,min:paso2Command.valorMinimo,max:paso2Command.valorMaximo,
						warning:[comparador:paso2Command.comparador,valorAlerta:paso2Command.valorAlerta],
						uuid:uuid,nombre:sensor.tipo,notificables:[]]
			success()
		}else{
			flow.paso2Command = paso2Command
			error()
		}
	}

	private doEliminarSensor = {parameters ->

		def flow = parameters.flow
		params.uuid
		def sensor = flow.sensores.find{
			it.uuid = params.uuid
		}

		flow.sensores.remove(sensor)
		return success()
	}

	private doAgregarCamara = {parameters ->

		def flow = parameters.flow
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
	}

	private doPaso4 = {parameters ->

		def flow = parameters.flow
		def file = request.getFile('file')
		if(flow.imagen && file.bytes.size() == 0){
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
				flow.imagen=true
				success()
			}
		}
	}

	private doPaso5 = {parameters ->
		def flow = parameters.flow
		def cantidadSensoressUbicados = agregarPosicionSensores(flow)
		def cantidadCamarasUbicados = agregarPosicionCamara(flow)
		flow.ubicacion=true

		success()
	}

	private doPaso6 = { parameters ->
		
		def flow = parameters.flow

		buscarNotificables(flow.sensores)
		buscarNotificables(flow.camaras)
		flow.rfid.notificables = params.list("dispositivos-rfid")
		success()
	}
	
	private buscarNotificables(listaParaAgregarNotificable){
		
		listaParaAgregarNotificable.each{

			it.notificables = params.list("dispositivos-${it.uuid}")
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
	String numero
	String piso
	String ip
	Boolean rfid

	static constraints = {
		cuenta nullable:false
		edificio nullable:false
		ip validator: {value->
			if(!InetAddressValidator.getInstance().isValid(value)) return['ipNovalida']
		}
	}
}

class Paso2Command implements Serializable{

	Long tipo
	String valorMaximo
	String valorMinimo
	String valorAlerta
	String comparador

	static constraints = {
		tipo nullable:false
		valorMaximo nullable:false
		valorMinimo nullable:false,validator:{val,obj ->
			
			val<obj.valorMaximo
		}
		valorAlerta validator:{val,obj ->
			
			if(val.replaceAll(" ","") != "")
				val > obj.valorMinimo && val < obj.valorMaximo
			
		}
		comparador validator:{val,obj ->
			println val
				if(val != '-1'){
					obj.valorAlerta.replaceAll(" ","") != ""
				}
		}
	}
}