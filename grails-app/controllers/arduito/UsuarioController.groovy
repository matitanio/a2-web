package arduito

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException

import arduito.Cuenta;
import arduito.Perfil;
import arduito.Usuario;
import arduito.UsuarioPerfil;


@Secured(["ROLE_ADMIN","IS_AUTHENTICATED_FULLY"])
class UsuarioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def springSecurityService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [usuarioInstanceList: Usuario.buscarTodos(params), usuarioInstanceTotal: Usuario.contarTodos()]
    }

    def create() {
        [usuarioInstance: new Usuario(params)]
    }

    def save() {
        def usuarioInstance = new Usuario(params)
		def cuenta = params['cuenta.id']?Cuenta.get(params.long('cuenta.id')):springSecurityService.currentUser.cuenta
		usuarioInstance.cuenta = cuenta
        if (!usuarioInstance.save(flush: true)) {
			
			
            render(view: "create", model: [usuarioInstance: usuarioInstance])
            return
        }
		UsuarioPerfil.create(usuarioInstance, Perfil.get(params.long('rol')))
        flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
        redirect(action: "show", id: usuarioInstance.id)
    }

    def show(Long id) {
        def usuarioInstance = Usuario.get(id)
        if (!usuarioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "list")
            return
        }

        [usuarioInstance: usuarioInstance]
    }

    def edit(Long id) {
        def usuarioInstance = Usuario.get(id)
        if (!usuarioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "list")
            return
        }

        [usuarioInstance: usuarioInstance]
    }

    def update(Long id, Long version) {
        def usuarioInstance = Usuario.get(id)
		def oldPassword = usuarioInstance.password
        if (!usuarioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (usuarioInstance.version > version) {
                usuarioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'usuario.label', default: 'Usuario')] as Object[],
                          "Another user has updated this Usuario while you were editing")
                render(view: "edit", model: [usuarioInstance: usuarioInstance])
                return
            }
        }

        usuarioInstance.properties = params
		if(params.password == ''){
			usuarioInstance = oldPassword
		}

        if (!usuarioInstance.save(flush: true)) {
			
            render(view: "edit", model: [usuarioInstance: usuarioInstance])
            return
        }
		actualizarRoles(usuarioInstance)
        flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
        redirect(action: "show", id: usuarioInstance.id)
    }

    def delete(Long id) {
        def usuarioInstance = Usuario.get(id)
        if (!usuarioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "list")
            return
        }

        try {
            usuarioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "show", id: id)
        }
    }
	
	private actualizarRoles(usuario){
		
		UsuarioPerfil.removeAll(usuario)
		params.list('rol').each{unPerfil ->
			
			UsuarioPerfil.create(usuario,Perfil.get(unPerfil))
		}
	}
}
