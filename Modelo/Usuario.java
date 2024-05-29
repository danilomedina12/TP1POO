package Modelo;

import Controlador.ControladorDonaciones;

import java.time.*;

public class Usuario {
	private static int nextId = 1;
	public int id;
	public String nombre;
 
	public Usuario(String nombre) {
		this.id = nextId++;
		this.nombre = nombre;
	}
	
    public void donarCajas(int cantidad, LocalDate fecha, String ubicacion) {
        ControladorDonaciones.agregarDonacion(cantidad, fecha, ubicacion, this.id);
    }

	public int obtenerId() {
		return this.id;
	}

	public void reservarCajas(int cantidad, LocalDate plusDays, String ubicacion) {
		ControladorDonaciones.reservarCajas(cantidad, plusDays, ubicacion);
	}

	public String obtenerNombre() {
		return this.nombre;
	}
}
