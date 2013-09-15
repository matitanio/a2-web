package filters.api

import arduito.annotations.TienePermisos
import arduito.rest.api.SensoresApiController

/**
 * RestFilters
 * A filters class is used to execute code before and after a controller action is executed and also after a view is rendered
 */
class RestFilters {

	def validadorPermisosService
	def filters = {
		restApi(uri:'/api/**') {
			before = {

				println 'hola'
				if(esLLamadaValida(controllerName,actionName,params)){
					return true
				}else{
					render 'sin permisos'
					return false
				}
			}
			after = { Map model ->
			}
			afterView = { Exception e ->
			}
		}
	}

	def esLLamadaValida(controllerName,actionName,params){
		
		def resultado = true
		def controllerClass = controllerClasses[controllerName]
		def metodo = controllerClass.methods.find{ it =~ /.*$actionName/ }
		
		if(metodo.isAnnotationPresent(TienePermisos.class)){
			def componente = metodo.getAnnotation(TienePermisos.class).componente()
			resultado = validadorPermisosService.validar(componente,params.id,params.pin)
			
		}
		resultado
	}

	def controllerClasses = [
		sensoresApi:SensoresApiController.class
	]
}
