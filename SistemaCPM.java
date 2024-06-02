import Controlador.ControladorDonaciones;
import Controlador.ControladorRegistro;
import Modelo.DonacionesUsuario;
import Modelo.Usuario;

import java.util.*;
import java.time.*;

public class SistemaCPM {
	
	public static void main(String[] args) throws Exception {
		try {
			testControladorDonaciones();
			System.out.println("-----------------------------------------------");
			testUsuariosYDonaciones();
			System.out.println("-----------------------------------------------");
			testControladorRegistro();
			System.out.println("-----------------------------------------------");
			testValidacionUbicacion();
			System.out.println("-----------------------------------------------");
			testValidacionUbicacionExtendida();
			System.out.println("-----------------------------------------------");
			testDonacionEnUbicacionNoValida();
			System.out.println("Pasaron los tests");
		} catch (AssertionError e) {
			System.out.println("Fallaron los tests: " + e.getMessage());
		}
	}

	public static void testControladorDonaciones() throws Exception {
		System.out.println("Test del controlador de donaciones");
		ControladorDonaciones.donaciones = new ArrayList<>();
		LocalDate now = LocalDate.now();
		LocalDate dayAfter = now.plusDays(1);
		ControladorDonaciones.agregarDonacion(15, now, "Madrid", 2);
		List<DonacionesUsuario> encontradas = ControladorDonaciones.buscarCajas(2, dayAfter, "Madrid");
		assertion (encontradas != null && encontradas.stream().mapToInt(DonacionesUsuario::obtenerCantidad).sum() == 15,"Buscar cajas fallo antes de reservar.");
		ControladorDonaciones.reservarCajas(15, dayAfter, "Madrid");
		List<DonacionesUsuario> otrasencontradas = ControladorDonaciones.buscarCajas(2, dayAfter, "Madrid");
		assertion(otrasencontradas == null || otrasencontradas.isEmpty(),"Buscar cajas fallo despues de reservar.");
	}


	private static void assertion(boolean condition, String message) throws AssertionError {
		if (!condition) {
			throw new AssertionError(message);
		}
	}

	public static void testUsuariosYDonaciones() throws Exception {
	    System.out.println("Test de los usuarios y donaciones");
	    Usuario sarahConnor = new Usuario("Sarah Connor");
	    sarahConnor.donarCajas(10, LocalDate.now(), "Almagro");
	    System.out.println(sarahConnor.obtenerNombre() + " ya donó");
	    Usuario charlesLeclerc = new Usuario("Charles Leclerc");
	    charlesLeclerc.donarCajas(5, LocalDate.now(), "Almagro");
	    System.out.println(charlesLeclerc.obtenerNombre() + " ya donó");
	    Usuario elenRipley = new Usuario("Elen Ripley");
	    elenRipley.reservarCajas(13, LocalDate.now().plusDays(1), "Almagro");
	    System.out.println(elenRipley.obtenerNombre() + " ya reservó");
	    if (ControladorDonaciones.donaciones.stream().noneMatch(d -> d.obtenerIdUsuarioDonante() == sarahConnor.obtenerId())) {
	        System.out.println("Prueba exitosa: La donación de Sarah Connor ya no está en el sistema.");
	    } else {
	        System.out.println("Prueba fallida: La donación de Sarah Connor todavía está en el sistema.");
	    }

	    DonacionesUsuario donacionCharles = ControladorDonaciones.donaciones.stream().filter(d -> d.obtenerIdUsuarioDonante() == charlesLeclerc.obtenerId()).findFirst().orElse(null);
	    if (donacionCharles != null && donacionCharles.obtenerCantidad() == 2) {
	        System.out.println("Prueba exitosa: La donación de Charles Leclerc tiene la cantidad correcta de cajas (2 cajas) en el sistema.");
	    } else {
	        System.out.println("Prueba fallida: La donación de Charles Leclerc tiene " + (donacionCharles != null ? donacionCharles.obtenerCantidad() : "N/A") + " cajas.");
	    }
	}

	public static void testControladorRegistro() {
	    System.out.println("Test del controlador de registro de usuarios");	
	    ControladorRegistro ControladorRegistro = new ControladorRegistro();

	    ControladorRegistro.enviarDatosRegistro("Sarah Connor");
	    assertion(ControladorRegistro.usuariosRegistrados.size() == 1, "Falló la prueba de registro exitoso");

	    ControladorRegistro.enviarDatosRegistro("");
	    assertion(ControladorRegistro.usuariosRegistrados.size() == 1, "Falló la prueba de registro con nombre vacío");

	    ControladorRegistro.enviarDatosRegistro("Charles Leclerc");
	    assertion(ControladorRegistro.usuariosRegistrados.size() == 2, "Falló la prueba de registro con distinto nombre");

	    System.out.println("Pasaron los tests de ControladorRegistro");
	}

	public static void testValidacionUbicacion() throws Exception {
	    System.out.println("Test de validación de ubicación");

	    String ubicacionValida = "Madrid";
	    boolean esValida = ControladorDonaciones.validarUbicacion(ubicacionValida);
	    assertion(esValida, "La ubicación '" + ubicacionValida + "' debería ser válida.");

	    String ubicacionNoValida = "Posadas";
	    esValida = ControladorDonaciones.validarUbicacion(ubicacionNoValida);
	    assertion(!esValida, "La ubicación '" + ubicacionNoValida + "' no debería ser válida.");
	    System.out.println("Pasó el test de validación de ubicación");
	}

	public static void testValidacionUbicacionExtendida() throws Exception {
	    System.out.println("Test extendido de validación de ubicación");

	    List<String> ubicacionesValidas = Arrays.asList("Madrid", "Almagro");
	    for (String ubicacion : ubicacionesValidas) {
	        boolean esValida = ControladorDonaciones.validarUbicacion(ubicacion);
	        assertion(esValida, "La ubicación '" + ubicacion + "' debería ser válida.");
	    }

	    List<String> ubicacionesNoValidas = Arrays.asList("Barcelona", "Sevilla", "Posadas");
	    for (String ubicacion : ubicacionesNoValidas) {
	        boolean esValida = ControladorDonaciones.validarUbicacion(ubicacion);
	        assertion(!esValida, "La ubicación '" + ubicacion + "' no debería ser válida.");
	    }

	    System.out.println("Todas las pruebas de validación de ubicación extendida pasaron.");
	}
	public static void testDonacionEnUbicacionNoValida() throws Exception {
	    System.out.println("Test de donación en ubicación no válida");

	    String ubicacionNoValida = "UbicacionNoValida";
	    LocalDate fecha = LocalDate.now();
	    int cantidad = 10;
	    int idUsuario = 1;  
	    ControladorDonaciones.donaciones = new ArrayList<>(); 
	    ControladorDonaciones.agregarDonacion(cantidad, fecha, ubicacionNoValida, idUsuario);

	    boolean donacionAgregada = ControladorDonaciones.donaciones.stream().anyMatch(d -> d.obtenerUbicacion().equals(ubicacionNoValida));
	    assertion(!donacionAgregada, "La donación en una ubicación no válida debería haber sido rechazada.");

	    System.out.println("Pasó el test de donación en ubicación no válida");
	}
}
