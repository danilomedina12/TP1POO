package Modelo;

import java.util.*;

public class Ubicacion {
    private static List<String> ubicacionesValidas = new ArrayList<>();

    static {
        // Inicializar las ubicaciones válidas
        ubicacionesValidas.add("Madrid");
        ubicacionesValidas.add("Almagro");
    }

    public static boolean esUbicacionValida(String ubicacion) {
        return ubicacionesValidas.contains(ubicacion);
    }
}
