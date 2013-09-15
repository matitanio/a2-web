package arduito.rest.api

import grails.converters.JSON
import arduito.annotations.TienePermisos


class AccesosApiController {


	def rfeedService
	
	def todosLosAccesos(){
		
		render rfeedService.buscarTodosAccesos(params.pin) as JSON
	}
	
	@TienePermisos(componente=ComponentesValidables.ACCESOS)
	def ultimosAccesos(Long id){
		
		render rfeedService.buscarUltimosAccesos(id,10) as JSON
	}
	
}
