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
	def notificacionService

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
				
				def cuenta = Cuenta.get(flow.paso1Command.cuenta)
				flow.usuariosNotificables = notificacionService.buscarUsuarios(cuenta)
				flow.dispositivosNotificables = notificacionService.buscarDispositivos(cuenta)
				
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
	
	private buscarTodosNotificables(cuenta){
		def todos = notificacionService.buscarTodos(Cuenta.get(cuenta))
		todos
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
				parameters.flow.rfid = [notificables:[]]
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
		println paso2Command.valorMaximo
		def sensores = flow.sensores
		if(!sensores){
			flow.sensores  = []
		}
		if(paso2Command.validate()){
			def sensor = Sensor.get(paso2Command.tipo.toLong())
			flow.sensores.add([tipo:paso2Command.tipo,min:paso2Command.valorMinimo,max:paso2Command.valorMaximo,
						warning:[comparador:paso2Command.comparador,valorAlerta:paso2Command.valorAlerta],
						uuid:new Date().time,nombre:sensor.tipo,notificables:[]])
			flow.paso2Command = null
			success()
		}else{
			flow.paso2Command = paso2Command
			println flow.paso2Command.valorMaximo
			error()
		}
	}

	private doEliminarSensor = {parameters ->

		def flow = parameters.flow
		def index = 0
		def sensor = flow.sensores.eachWithIndex{it,i ->
			if(it.uuid.toString() == params.uuid)
				index = i
		}

		flow.sensores.remove(index)
		return success()
	}

	private doAgregarCamara = {parameters ->

		def flow = parameters.flow
		def camaras = flow.camaras
		flow.ipError = null
		
		if(!camaras)
			flow.camaras = []

		if(ipCamaraDuplicada(params.ip,flow.camaras)){
			flow.ipError = 'Ya existe es ip en esta haitacion'
			error()
		}else if(!InetAddressValidator.getInstance().isValid(params.ip)){
			flow.ipError = 'Debe ser una ip valida'
			error()
		}else{
			def uuid = UUID.randomUUID()
			flow.camaras << [ip:params.ip,uuid:uuid,notificables:[]]
			success()
		}
	}
	
	private ipCamaraDuplicada(ip,camaras){
		
		camaras.find{
			ip == it.ip
		} != null
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
		numero nullable:false,blank:false
		ip validator: {value->
			if(!InetAddressValidator.getInstance().isValid(value)) return['ipNovalida']
		}
	}
}

class Paso2Command implements Serializable{

	Long tipo
	Double valorMaximo
	Double valorMinimo
	Double valorAlerta
	String comparador

	static constraints = {
		tipo nullable:false
		valorMaximo nullable:false,validator:{val,obj ->
			
			if(val<obj.valorMinimo)
				return 'maximo.menor.minimo'
		}
		valorMinimo nullable:false,blank:false,validator:{val,obj ->
			
			if(val>obj.valorMaximo)
				return 'minimo.mayor.maximo'
		}
		valorAlerta nullable:true,validator:{val,obj ->
			
			def resultado = null
			if(val){
				if(val < obj.valorMinimo){
				
					resultado = 'alerta.menor.minimo'
				}
				if(val > obj.valorMaximo){
					resultado = 'alerta.mayor.maximo'
				}
			}
				
			resultado
		}
		comparador validator:{val,obj ->
				if(val != '-1'){
					if(!obj.valorAlerta)
						return 'warning.sin.valor'
				}else{
					if(obj.valorAlerta)
						return 'warning.con.valor.sin.comparador'
					
				}
		}
	}
}