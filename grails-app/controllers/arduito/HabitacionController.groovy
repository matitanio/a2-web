package arduito

import grails.plugins.springsecurity.Secured

import java.awt.image.BufferedImage

import javax.imageio.ImageIO
import javax.media.j3d.View;

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator
import org.imgscalr.Scalr


@Secured(["ROLE_ARDUITO","IS_AUTHENTICATED_FULLY"])
class HabitacionController {

	def springSecurityService
	def grailsApplication
	def habitacionService
	def notificacionService
	def controlAccesoService


	def actulizarAccesos(Long id){

		def mensaje
		try{
			controlAccesoService.actualizarAccesosHabitacion(id)
			mensaje = 'Los Accesos se actualizaron correctamente'
		}catch(Exception e){
		
			 mensaje = 'Hubo un error al tratar de actualizar los accesos'
		}
		
		flash.message = mensaje
		redirect action:'list'
	}

	def create(){
		redirect(action:'nuevaHabitacion')
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[habitacionInstanceList: Habitacion.list(params), habitacionInstanceTotal: Habitacion.count()]
	}

	def edit(Long id){

		redirect(action:'nuevaHabitacion',params:[edit:true,id:id])
	}
	def nuevaHabitacionFlow = {
		onStart{
			return doStep('doInicializar',delegate,[flow:flow])
		}
		paso1{
			on("siguiente") {Paso1Command paso1Command ->
				return doStep('doPaso1',delegate,[flow:flow,paso1Command:paso1Command])
			}.to("paso2")
			on('resumen'){Paso1Command paso1Command ->
				return doStep('doPaso1',delegate,[flow:flow,paso1Command:paso1Command])
			}.to('paso7')
			on('guardar'){Paso1Command paso1Command ->
				return doStep('doPaso1',delegate,[flow:flow,paso1Command:paso1Command])
			}.to('guardar')
			on('jump').to{params.to}
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
			on('editarSensor'){
				return doStep('doEditarSensor',delegate,[flow:flow,uuid:params.uuid])
			}.to('paso2')
			on('cambiarEstadoSensor'){
				def sensor = flow.sensores.find{it.id == params.long('id')} 
				sensor.estado = !params.boolean('estado')
			}.to('paso2')
			on('atras').to('paso1')
			on('resumen').to('paso7')
			on('guardar').to('guardar')
			on('jump').to{params.to}
		}
		paso3{

			on('agregarCamara'){
				return doStep('doAgregarCamara',delegate,[flow:flow])
			}.to('paso3')

			on('eliminarCamara'){
				
				doEliminarCamara(flow)
				
			}.to('paso3')
			on('siguiente'){
				if(params.ip.replaceAll(" ", "") != ""){
					return doStep('doAgregarCamara',delegate,[flow:flow])
				}
			}.to('paso4')
			on('atras').to('paso2')
			on('resumen').to('paso7')
			on('guardar').to('guardar')
			on('jump').to{params.to}
		}
		paso4{

			on('siguiente'){

				return doStep('doPaso4',delegate,[flow:flow])
			}.to('paso5')
			on('atras').to('paso3')
			on('resumen').to('paso7')
			on('guardar').to('guardar')
			on('jump').to{params.to}
		}

		paso5{

			on('siguiente'){
				buscarNotificablesDeLaCuenta(flow)
				return doStep('doPaso5',delegate,[flow:flow])
			}.to('paso6')
			on('atras').to('paso4')
			on('resumen'){
				return doStep('doPaso5',delegate,[flow:flow])
			}.to('paso7')
			on('guardar'){return doStep('doPaso5',delegate,[flow:flow])}.to('guardar')
			on('jump').to{params.to}
		}
		paso6{
			on('siguiente'){
				return doStep('doPaso6',delegate,[flow:flow])
			}.to('paso7')
			on('resumen').to('paso7')
			on('atras'){
				return doStep('doPaso6',delegate,[flow:flow])
			}.to('paso5')
			on('guardar'){return doStep('doPaso6',delegate,[flow:flow])}.to('guardar')
			on('jump').to{params.to}
		}
		paso7{
			on('atras'){flow.resumen = true}.to('paso6')
			on('general'){flow.resumen = true}.to('paso1')
			on('sensores'){flow.resumen = true}.to('paso2')
			on('camaras'){flow.resumen = true}.to('paso3')
			on('plano'){flow.resumen = true}.to('paso5')
			on('notificaciones'){flow.resumen = true}.to('paso6')
			on('terminar'){doSave(flow)}.to('finalizar')
			on('guardar'){doGuardar(flow)}.to('finalizar')
			on('jump').to{params.to}
		}
		guardar{
			action{
				doGuardar(flow)
				finalizar()
			}
			on('finalizar').to('finalizar')
		}
		finalizar{ redirect (action: "list") }
	}
	
	private doEliminarCamara(flow){
		
		def index = 0
		flow.camaras.eachWithIndex{it,i ->
			if(it.ip == params.ip){
				index = i
				if(it.id)
					flow.camarasEliminados = it.id
			}
		}
		flow.camaras.remove(index)
		
	}
	private buscarTodosNotificables(cuenta){
		def todos = notificacionService.buscarTodos(Cuenta.get(cuenta))
		todos
	}

	private doSave(flow){

		def parametros = crearParametrosCreacion(flow)
		habitacionService.crear(parametros)
	}
	private doGuardar(flow){
		
		def parametros = crearParametrosCreacion(flow)
		parametros.sensoresEliminados = flow.sensoresEliminados
		parametros.camarasEliminados = flow.camarasEliminados
		parametros.id = flow.habitacionId
		habitacionService.editar(parametros)
	}
	
	private crearParametrosCreacion(flow){
		def parametros = [:]
		parametros.cuenta = flow.paso1Command.cuenta
		parametros.edificio = flow.paso1Command.edificio
		parametros.ip = flow.paso1Command.ip
		parametros.piso = flow.paso1Command.piso
		parametros.numero = flow.paso1Command.numero
		parametros.sensores = flow.sensores
		parametros.camaras = flow.camaras
		parametros.plano = flow.urlPlanoRelativa
		parametros.rfid = [contiene:flow.paso1Command.rfid,notificables:flow.rfid.notificables]
		
		parametros
	}
	private doStep(stepNumber,delegate,parameters){

		def stepClosure = this."$stepNumber"
		stepClosure.delegate = delegate
		return stepClosure.call(parameters)
	}

	private doInicializar = { parameters ->

		def flow = parameters.flow
		flow.rfid = [notificables:[]]
		if(params.edit){
			flow.resumen = true
			flow.edit = true
			flow.ubicacion = true
			def habitacion = Habitacion.get(params.long('id'))
			flow.habitacionId = habitacion.id
			crearParametrosPaso1(flow,habitacion)
			crearParametrosPaso2(flow,habitacion)
			crearParametrosPaso3(flow,habitacion)
			crearParametrosPaso4(flow,habitacion)
			crearParametrosPaso6(flow,habitacion)
		}else{
			flow.resumen = false
		}
	}
	private crearParametrosPaso1(flow,habitacion){
		def paso1Command = new Paso1Command(
				cuenta:habitacion.edificio.owner.id,
				edificio:habitacion.edificio.id,
				numero:habitacion.numero,
				piso:habitacion.piso,
				ip:habitacion.ipHabitacion,
				rfid:habitacion.rfeed?true:false)
		
		flow.paso1Command = paso1Command
	}
	private crearParametrosPaso2(flow,habitacion){
		flow.sensores = []
		flow.sensoresEliminados = []
		habitacion.sensores.each{unSensor ->
			flow.sensores.add([tipo:unSensor.sensor.id,min:unSensor.valorMinimo,max:unSensor.valorMaximo,
				ubicacion:"${unSensor.coordenadaY}:${unSensor.coordenadaX}".toString(),
				warning:[comparador:unSensor.warning?.comparador,valorAlerta:unSensor.warning?.valorWarning],
				uuid:new Date().time,nombre:unSensor.sensor.tipo,notificables:unSensor.notificables.collect{it.id as String},id:unSensor.id,
				estado:unSensor.instalado])
		}
		
	}
	private crearParametrosPaso3(flow,habitacion){
		
		flow.camaras = []
		flow.camarasEliminados = []
		habitacion.camaras.each{unaCamara ->
			def uuid = UUID.randomUUID()
			flow.camaras << [ip:unaCamara.ip,id:unaCamara.id,uuid:uuid,
							notificables:unaCamara.notificables.collect{it.id as String},
							ubicacion:"${unaCamara.coordenadaY}:${unaCamara.coordenadaX}"]
		}
		
	}
	
	private crearParametrosPaso4(flow,habitacion){
		flow.imagen=true
		flow.ubicacion=true
	}
	
	private crearParametrosPaso6(flow,habitacion){
		
		buscarNotificablesDeLaCuenta(flow)
		if(flow.paso1Command.rfid){
			flow.rfid = [:]
			def  n = habitacion.rfeed.notificables.collect{ it.id as String}
			flow.rfid.notificables = n
			
		}
	}

	private doPaso1 = {parameters->

		parameters.flow.paso1Command = parameters.paso1Command
		if(parameters.paso1Command.validate()){
			def sensoresValores = [:]
			Sensor.list().collect{
				sensoresValores.put(it.id, [valorMaximo:it.valorMaximo,valorMinimo:it.valorMinimo,unidades:it.unidades])
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
			flow.sensores  = []
		}
		if(paso2Command.validate()){
			agregarSensor(flow,paso2Command)
			
			flow.paso2Command = null
			success()
		}else{
			flow.paso2Command = paso2Command
			error()
		}
	}
	
	private agregarSensor(flow,paso2Command){
		
		def sensor = Sensor.get(paso2Command.tipo.toLong())
		//estoy editando valores
		if(paso2Command.uuid){
			def sensorHabitacion = flow.sensores.find{
				it.uuid.toString() == paso2Command.uuid.toString()
			}
			sensorHabitacion.min = paso2Command.valorMinimo
			sensorHabitacion.max = paso2Command.valorMaximo
			sensorHabitacion.warning = [comparador:paso2Command.comparador,valorAlerta:paso2Command.valorAlerta]
		}else{
		
		flow.sensores.add([tipo:paso2Command.tipo,min:paso2Command.valorMinimo,max:paso2Command.valorMaximo,
			warning:[comparador:paso2Command.comparador,valorAlerta:paso2Command.valorAlerta],
			uuid:new Date().time,nombre:sensor.tipo,notificables:[],estado:false])
		}
	}
	
	private doEditarSensor = { parametros ->
		
		def flow = parametros.flow
		def uuid = parametros.uuid

		def sensor = flow.sensores.find{
			it.uuid.toString() == uuid.toString()
		}
		
		flow.paso2Command = new Paso2Command(tipo:sensor.tipo,
											valorMaximo:sensor.max,
											valorMinimo:sensor.min,
											valorAlerta:sensor.warning?.valorAlerta,
											comparador:sensor.warning?.comparador,
											id:sensor.id,
											uuid:uuid)
		
		
		
		success()
		
	}
	
	private doEliminarSensor = {parameters ->

		def flow = parameters.flow
		def index = 0
		def sensor = flow.sensores.eachWithIndex{it,i ->
			if(it.uuid.toString() == params.uuid){
				index = i
				if(it.id){
					flow.sensoresEliminados << it.id
				}
			}
		}

		flow.sensores.remove(index)
		flow.paso2Command = null
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

		camaras.find{ ip == it.ip } != null
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

	
	private buscarNotificablesDeLaCuenta(flow){
		
		def cuenta = Cuenta.get(flow.paso1Command.cuenta)
		flow.usuariosNotificables = notificacionService.buscarUsuarios(cuenta)
		flow.dispositivosNotificables = notificacionService.buscarDispositivos(cuenta)
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
		def notificablesRfid = params.list("dispositivos-rfid")
		if(notificablesRfid){
			flow.rfid = [notificables:[]]
			flow.rfid.notificables = notificablesRfid
		}
		
		 
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
	
	def cambiarEstado(){
		
		def habitacion = Habitacion.get(params.long('id'))
		
		if(params.boolean('estado'))
			habitacion.desActivar()
		else
			habitacion.activar()
			
			
		redirect action:'list'
	}
	
	def editarAccesos(Long id){
		
		render view:'tarjetas',model:[habitacion:Habitacion.get(id),error:params.error!=null?params.error:false]
	}
	
	def nuevaTarjeta(Long id,String acceso){
		
		def mensaje
		def tarjeta = new TarjetaAcceso(acceso:acceso)
		tarjeta.save()
		def error = false
		if(!tarjeta.hasErrors()){
			def habitacion = Habitacion.get(id)
			habitacion.rfeed.addToTarjetasConAcceso(tarjeta)
			habitacion.save()
			mensaje='Tarjeta agregada con exito'
		}else{
			error = true
			mensaje='No Fue posible agregar la tarjeta'
		}
		
		flash.message = mensaje
		println error
		redirect action:'editarAccesos',params:[id:id,error:error]
	}
	
	def eliminarTarjeta(Long id,Long tarjetaId){
		
		def tarjeta = TarjetaAcceso.get(tarjetaId)
		def habitacion = Habitacion.get(id)
		habitacion.rfeed.removeFromTarjetasConAcceso(tarjeta)
		tarjeta.delete()
		habitacion.save()
		
		redirect action:'editarAccesos',params:[id:id]
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
	Long id
	String uuid 

	static constraints = {
		tipo nullable:false
		id nullable:true
		uuid nullable:true
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