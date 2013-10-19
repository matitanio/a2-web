package a2.web

import arduito.Habitacion
import arduito.LectorRfeed;



class ActualizarAccesosJob {
	
	def controlAccesoService
    static triggers = {
      simple repeatInterval: (10l*60l*1000l) // Se ejecuta cada 10 minutos
    }

    def execute() {
        
		def lectores = LectorRfeed.list()
		
		lectores.each{unlector ->
			try{
				controlAccesoService.actualizarAccesosHabitacion(unlector.habitacion.id)
				log.info('Actualizando los accesos de la habitacion con id[' + unlector.habitacion.id + ']')
				
			}catch(Exception e){
				log.error('Error al actualizar los accesos de la habitacion con id['+ unlector.habitacion.id + '] ',e)
			}
		}
    }
}
