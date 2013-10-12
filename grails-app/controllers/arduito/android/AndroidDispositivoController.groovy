package arduito.android

import grails.converters.JSON
import arduito.DispositivoMovil

/**
 * AndroidDispositivoController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AndroidDispositivoController {


	def dispositivoMovilService
	
	def registrar() {
		
		dispositivoMovilService.registrar(params.pin,params.gcmkey)
		render 'ok'
	}
	
	def validar(){
		
		render dispositivoMovilService.validar(params.pinValidacion,params.usuario) as JSON
	}
	
	def probarNotificacion(){
		
		def dispositivo = DispositivoMovil.findByPin(params.pin)
		def resultado
		
		if(dispositivo){
			dispositivoMovilService.enviarAlertaPrueba(dispositivo.id)
			resultado = [resultado:'ok']
		}else{
			log.error('No existe El dispositivo Movil con el pin[' + params.pin + ']')
			resultado = [resultado:'no',mensaje:'No existe un dispositivo movil con el pin enviado como parametro']
		}
		
		render resultado as JSON
		
		
	}
}
