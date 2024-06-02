package Modelo;

import java.util.*;

public class Ubicacion {
	private static List<String> ubicacionesValidas;
	
	public Ubicacion() {
		Ubicacion.ubicacionesValidas = new ArrayList<>();
		
		//Acción específica para nuestros casos
		Ubicacion.ubicacionesValidas.add("Madrid");
		Ubicacion.ubicacionesValidas.add("Almagro");
		
	}
	
	public boolean esUbicacionValida(String ubicacion) {
		return Ubicacion.ubicacionesValidas.contains(ubicacion);
	}
}
