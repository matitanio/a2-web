package arduito

import org.springframework.dao.DataIntegrityViolationException

/**
 * SensorController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class SensorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sensorInstanceList: Sensor.list(params), sensorInstanceTotal: Sensor.count()]
    }

    def create() {
        [sensorInstance: new Sensor(params)]
    }

    def save() {
        def sensorInstance = new Sensor(params)
        if (!sensorInstance.save(flush: true)) {
            render(view: "create", model: [sensorInstance: sensorInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'sensor.label', default: 'Sensor'), sensorInstance.id])
        redirect(action: "show", id: sensorInstance.id)
    }

    def show() {
        def sensorInstance = Sensor.get(params.id)
        if (!sensorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'sensor.label', default: 'Sensor'), params.id])
            redirect(action: "list")
            return
        }

        [sensorInstance: sensorInstance]
    }

    def edit() {
        def sensorInstance = Sensor.get(params.id)
        if (!sensorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'sensor.label', default: 'Sensor'), params.id])
            redirect(action: "list")
            return
        }

        [sensorInstance: sensorInstance]
    }

    def update() {
        def sensorInstance = Sensor.get(params.id)
        if (!sensorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'sensor.label', default: 'Sensor'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (sensorInstance.version > version) {
                sensorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'sensor.label', default: 'Sensor')] as Object[],
                          "Another user has updated this Sensor while you were editing")
                render(view: "edit", model: [sensorInstance: sensorInstance])
                return
            }
        }

        sensorInstance.properties = params

        if (!sensorInstance.save(flush: true)) {
            render(view: "edit", model: [sensorInstance: sensorInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'sensor.label', default: 'Sensor'), sensorInstance.id])
        redirect(action: "show", id: sensorInstance.id)
    }

    def delete() {
        def sensorInstance = Sensor.get(params.id)
        if (!sensorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'sensor.label', default: 'Sensor'), params.id])
            redirect(action: "list")
            return
        }

        try {
            sensorInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'sensor.label', default: 'Sensor'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'sensor.label', default: 'Sensor'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
