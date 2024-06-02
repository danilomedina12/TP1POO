package Controlador;

import Modelo.DonacionesUsuario;
import Vista.PantallaDeTexto;
import Modelo.Ubicacion;

import java.time.*;
import java.util.*;

public class ControladorDonaciones {
	public static List<DonacionesUsuario> donaciones;
	
	public static List<DonacionesUsuario> buscarCajas(int cantidad, LocalDate fecha, String ubicacion) {
	    List<DonacionesUsuario> donacionesDisponibles = new ArrayList<>();
	    List<DonacionesUsuario> donacionesEnUbicacion = buscarDonacionesPorUbicacion(ubicacion);
	    int cajasEncontradas = 0;
	    
	    Iterator<DonacionesUsuario> iterator = donacionesEnUbicacion.iterator();
	    while (iterator.hasNext() && cajasEncontradas < cantidad) {
	        DonacionesUsuario donacion = iterator.next();
	        if (donacion.obtenerFechaDisponibilidad().isBefore(fecha)) {
	            donacionesDisponibles.add(donacion);
	            cajasEncontradas += donacion.obtenerCantidad();
	        }
	    }
	    
	    if (cajasEncontradas < cantidad) {
	        PantallaDeTexto.mostrarTextoFallo("No se encontraron suficientes cajas. Cajas encontradas: " + cajasEncontradas);
	    }
	    
	    return donacionesDisponibles;
	}

	public static void reservarCajas(int cantidad, LocalDate fecha, String ubicacion) {
	   if(!validarUbicacion(ubicacion)) {
			return;
	   }
	    List<DonacionesUsuario> cajasParaReservar = buscarCajas(cantidad, fecha, ubicacion);
	    if(!hayCajasSuficientesParaReservar(cajasParaReservar,cantidad)) {
	        PantallaDeTexto.mostrarTextoFallo("No se pudo realizar la reserva. No hay suficientes cajas disponibles");
	        return;
	    }
	    int cajasReservadas = 0;
	    for(DonacionesUsuario donacion : cajasParaReservar) {
	        int cajasAReservar = Math.min(donacion.obtenerCantidad(), cantidad - cajasReservadas);
	        if (cajasAReservar < donacion.obtenerCantidad()) {
	            donacion.actualizarCantidad(donacion.obtenerCantidad() - cajasAReservar);
	        } else {
	            donaciones.remove(donacion);
	        }
	        cajasReservadas += cajasAReservar;
	    }
	    PantallaDeTexto.mostrarTextoExito("Reserva exitosa. Cajas reservadas: " + cajasReservadas);
	}
	
	public static void agregarDonacion(int cantidad, LocalDate fecha, String ubicacion, int idUsuario) {
		if(!validarUbicacion(ubicacion)) {
			return;
		}
		DonacionesUsuario nuevaDonacion = new DonacionesUsuario(cantidad, ubicacion, fecha, idUsuario);
		donaciones.add(nuevaDonacion);
		PantallaDeTexto.mostrarTextoExito("Donacion exitosa. Cantidad de cajas donadas: " + cantidad);
	}
	
	public static List<DonacionesUsuario> buscarDonacionesPorUbicacion(String ubicacion){
		List<DonacionesUsuario> donacionesEnUbicacion = new ArrayList<>();
		for(DonacionesUsuario donacion : donaciones) {
			if(donacion.obtenerUbicacion().equals(ubicacion)) {
				donacionesEnUbicacion.add(donacion);
			}
		}
		return donacionesEnUbicacion;
	}

	private static boolean hayCajasSuficientesParaReservar(List<DonacionesUsuario> donacionesBuscadas, int cantidadParaReservar) {
		return donacionesBuscadas.stream().mapToInt(DonacionesUsuario :: obtenerCantidad).sum() >= cantidadParaReservar;
	}
	public static boolean validarUbicacion(String ubicacion) {
	        boolean esValida = Ubicacion.esUbicacionValida(ubicacion);
	        
	        if (!esValida) {
	            PantallaDeTexto.mostrarTextoFallo("La ubicación: " + ubicacion + " no es apta. Por favor indicá otra ubicación");
	        }
	        
	        return esValida;
	    }
}
