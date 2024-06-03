package Modelo;

import java.time.*;

public class DonacionesUsuario {
	private static int nextId = 1;
	
	private int id;
	private int cantidad;
	private String ubicacion;
	private LocalDate fechaDisponibilidad;
	private int usuarioDonante;
	
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
	
	public int obtenerIdUsuarioDonante(){
		return this.usuarioDonante;
	}
}
