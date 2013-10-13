package arduito.arduino

import groovy.sql.Sql
import arduito.Habitacion
import arduito.RegistroAcceso
import arduito.RegistroSensor
import arduito.ResultadoMedicion
import arduito.SensorHabitacion
import arduito.TarjetaAcceso

/**
 * RegistroService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegistroService {

    static transactional = true
	def sessionFactory
	static String formatoFecha = "dd/MM/yyyy hh:mm:ss"

    def registrarMedicion(sensorId,valorMedido,resultado,fecha) {

		def sensor = SensorHabitacion.get(sensorId as Long)
		def registro = new RegistroSensor(fechaCreacion:Date.parse(formatoFecha, fecha),
											sensor:sensor,
											valorRegistrado:valorMedido as Float,
											resultadoRegistro:resultado as ResultadoMedicion)
		registro.save(flush:true)
    }
	
	def registrarAcceso(habitacionId,tarjetaId,resultado,fecha){
		
		def habitacion = Habitacion.get(habitacionId)
		def tarjeta = TarjetaAcceso.get(tarjetaId)
		def registro = new RegistroAcceso(habitacion:habitacion,tarjeta:tarjeta,resultado:resultado,fecha:Date.parse(formatoFecha, fecha))
		registro.save(flush:true)
	}
	
	def buscarUltimasNotificaciones(pin,cantidad=10) {
		
		def criteria = SensorHabitacion.createCriteria()
		def collector = [:]
		def sensores = criteria.list{
			createAlias('notificables','n')
			eq('n.pin',pin)
		}
		
		def sql = new Sql(sessionFactory.currentSession.connection())
		
		def rows = sql.rows( crearSqlUltimasNotificaciones(sensores.collect{it.id}.join(','),cantidad)).each{ row ->
			def unaFilaAgrupada = collector.get(row.idSensor)
			if(!unaFilaAgrupada){
				unaFilaAgrupada = [warning:0,fueraRango:0]
			}
			if(row.resultado == ResultadoMedicion.WARNING.toString()){
				
				unaFilaAgrupada.warning = row.cantidad
			}else{
				unaFilaAgrupada.fueraRango = row.cantidad
			}
			unaFilaAgrupada.id = row.idSensor
			unaFilaAgrupada.tipo = row.tipoSensor
			unaFilaAgrupada.numeroHabitacion = row.numeroHabitacion
			unaFilaAgrupada.direccion = row.direccion
			
			collector.put(row.idSensor, unaFilaAgrupada)
		}
		
		[sensores: collector.collect{
			it.value
		}]
		
	}
	
	private crearSqlUltimasNotificaciones(sensores,cantidad){
		
		
		def sql = """select top $cantidad
						count(rs.id) cantidad,
					    sh.id idSensor,
						rs.resultado_registro resultado,
						s.tipo tipoSensor,
						h.numero numeroHabitacion,
						e.direccion direccion
					from registro_sensor rs
					inner join sensor_habitacion sh on sh.id = rs.sensor_id
					inner join sensor s on s.id = sh.sensor_id
					inner join habitacion h on h.id = sh.habitacion_id
					inner join edificio e on e.id = h.edificio_id
					where 
						rs.resultado_registro <> 'OK'
						and sh.id in ($sensores)
					group by
						rs.resultado_registro,
					        sh.id,
						s.tipo,
						h.numero,
						e.direccion"""
		
		sql.toString()
		
	}

}
