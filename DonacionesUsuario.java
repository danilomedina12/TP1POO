package sistemaCPM;

import java.time.*;

public class DonacionesUsuario {
	private static int nextId = 1;
	
	int id;
	int cantidad;
	String ubicacion;
	LocalDate fechaDisponibilidad;
	int usuarioDonante;
	
	public DonacionesUsuario(int cantidad, String ubicacion, LocalDate fechaDisponibilidad, int usuarioDonante){
		this.id = nextId++;
		this.cantidad = cantidad; 
		this.ubicacion = ubicacion;
		this.fechaDisponibilidad = fechaDisponibilidad;
		this.usuarioDonante = usuarioDonante;
		}
	
	public void actualizarCantidad(int nuevaCantidad) {
		this.cantidad = nuevaCantidad;
	}
	
	public int obtenerCantidad() {
		return this.cantidad;
	}

	public LocalDate obtenerFechaDisponibilidad() {
		return this.fechaDisponibilidad;
	}

	public String obtenerUbicacion() {
		return this.ubicacion;
	}
	public int obtenerId() {
		return this.id;
	}
}
