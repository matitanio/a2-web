package arduito

import org.springframework.dao.DataIntegrityViolationException

/**
 * DispositivoMovilController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class DispositivoMovilController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def dispositivoMovilService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
		
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def dispositivos = dispositivoMovilService.buscarTodos(params)
		[dispositivoMovilInstanceList: dispositivos, dispositivoMovilInstanceTotal: dispositivos.totalCount]
    }

    def create() {
        [dispositivoMovilInstance: new DispositivoMovil(params)]
    }

    def save() {
        def dispositivoMovilInstance = new DispositivoMovil(params)
        if (!dispositivoMovilInstance.save(flush: true)) {
            render(view: "create", model: [dispositivoMovilInstance: dispositivoMovilInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), dispositivoMovilInstance.id])
        redirect(action: "show", id: dispositivoMovilInstance.id)
    }

    def show() {
        def dispositivoMovilInstance = DispositivoMovil.get(params.id)
        if (!dispositivoMovilInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), params.id])
            redirect(action: "list")
            return
        }

        [dispositivoMovilInstance: dispositivoMovilInstance]
    }

    def edit() {
        def dispositivoMovilInstance = DispositivoMovil.get(params.id)
        if (!dispositivoMovilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), params.id])
            redirect(action: "list")
            return
        }

        [dispositivoMovilInstance: dispositivoMovilInstance]
    }

    def update() {
        def dispositivoMovilInstance = DispositivoMovil.get(params.id)
        if (!dispositivoMovilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dispositivoMovilInstance.version > version) {
                dispositivoMovilInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil')] as Object[],
                          "Another user has updated this DispositivoMovil while you were editing")
                render(view: "edit", model: [dispositivoMovilInstance: dispositivoMovilInstance])
                return
            }
        }

        dispositivoMovilInstance.properties = params

        if (!dispositivoMovilInstance.save(flush: true)) {
            render(view: "edit", model: [dispositivoMovilInstance: dispositivoMovilInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), dispositivoMovilInstance.id])
        redirect(action: "show", id: dispositivoMovilInstance.id)
    }

    def delete() {
        def dispositivoMovilInstance = DispositivoMovil.get(params.id)
        if (!dispositivoMovilInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), params.id])
            redirect(action: "list")
            return
        }

        try {
            dispositivoMovilInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dispositivoMovil.label', default: 'DispositivoMovil'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	def usuariosPorcuenta(Long id){
		
		def cuenta = Cuenta.get(id)
		def usuarios = Usuario.findAllByCuenta(cuenta)
		
		render view:'_usuarios',model:[usuarios:usuarios]
	}
	
	def verificar(Long id){
		
		def dispositivo = DispositivoMovil.get(id)
		dispositivo.estado = Estado.VERIFICANDO
		dispositivo.save()
		redirect(action: "show", id: params.id)
	}
}
