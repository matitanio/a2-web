package arduito

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException

import arduito.Cuenta;
import arduito.Edificio;

@Secured(["ROLE_ADMIN","IS_AUTHENTICATED_FULLY"])
class EdificioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def edificioService
	def springSecurityService
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [edificioInstanceList: edificioService.buscarTodos(params), edificioInstanceTotal: edificioService.contarTodos()]
    }

    def create() {
        [edificioInstance: new Edificio(params)]
    }

    def save() {
        def edificioInstance = new Edificio(params)
		edificioInstance.owner = params['owner.id']?Cuenta.get(params.long('owner.id')):springSecurityService.currentUser.cuenta
        if (!edificioInstance.save(flush: true)) {
            render(view: "create", model: [edificioInstance: edificioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'edificio.label', default: 'Edificio'), edificioInstance.id])
        redirect(action: "show", id: edificioInstance.id)
    }

    def show(Long id) {
        def edificioInstance = Edificio.get(id)
        if (!edificioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'edificio.label', default: 'Edificio'), id])
            redirect(action: "list")
            return
        }

        [edificioInstance: edificioInstance]
    }

    def edit(Long id) {
        def edificioInstance = Edificio.get(id)
        if (!edificioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'edificio.label', default: 'Edificio'), id])
            redirect(action: "list")
            return
        }

        [edificioInstance: edificioInstance]
    }

    def update(Long id, Long version) {
        def edificioInstance = Edificio.get(id)
        if (!edificioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'edificio.label', default: 'Edificio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (edificioInstance.version > version) {
                edificioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'edificio.label', default: 'Edificio')] as Object[],
                          "Another user has updated this Edificio while you were editing")
                render(view: "edit", model: [edificioInstance: edificioInstance])
                return
            }
        }

        edificioInstance.properties = params

        if (!edificioInstance.save(flush: true)) {
            render(view: "edit", model: [edificioInstance: edificioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'edificio.label', default: 'Edificio'), edificioInstance.id])
        redirect(action: "show", id: edificioInstance.id)
    }

    def delete(Long id) {
        def edificioInstance = Edificio.get(id)
        if (!edificioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'edificio.label', default: 'Edificio'), id])
            redirect(action: "list")
            return
        }

        try {
            edificioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'edificio.label', default: 'Edificio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'edificio.label', default: 'Edificio'), id])
            redirect(action: "show", id: id)
        }
    }
}
