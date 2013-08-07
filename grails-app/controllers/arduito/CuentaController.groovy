package arduito

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException

/**
 * CuentaController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(["ROLE_ARDUITO","IS_AUTHENTICATED_FULLY"])
class CuentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [cuentaInstanceList: Cuenta.list(params), cuentaInstanceTotal: Cuenta.count()]
    }

    def create() {
        [cuentaInstance: new Cuenta(params)]
    }

    def save() {
        def cuentaInstance = new Cuenta(params)
        if (!cuentaInstance.save(flush: true)) {
            render(view: "create", model: [cuentaInstance: cuentaInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), cuentaInstance.id])
        redirect(action: "show", id: cuentaInstance.id)
    }

    def show() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
            redirect(action: "list")
            return
        }

        [cuentaInstance: cuentaInstance]
    }

    def edit() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
            redirect(action: "list")
            return
        }

        [cuentaInstance: cuentaInstance]
    }

    def update() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (cuentaInstance.version > version) {
                cuentaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cuenta.label', default: 'Cuenta')] as Object[],
                          "Another user has updated this Cuenta while you were editing")
                render(view: "edit", model: [cuentaInstance: cuentaInstance])
                return
            }
        }

        cuentaInstance.properties = params

        if (!cuentaInstance.save(flush: true)) {
            render(view: "edit", model: [cuentaInstance: cuentaInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), cuentaInstance.id])
        redirect(action: "show", id: cuentaInstance.id)
    }

    def delete() {
        def cuentaInstance = Cuenta.get(params.id)
        if (!cuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
            redirect(action: "list")
            return
        }

        try {
            cuentaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
