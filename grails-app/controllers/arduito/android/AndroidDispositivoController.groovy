package arduito.android

import grails.converters.JSON

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
}
