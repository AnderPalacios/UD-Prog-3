package cap05;

import java.util.HashMap;

public class Ejercicio_5_4 {
	
	public static void main(String[] args) {
		
		//Un mapa de entidad:
		HashMap<Integer, String> mapaEntidad = new HashMap<Integer, String>();
		
		//Varios mapas de la misma entidad:
		HashMap<String, String> mapaMinsmaEntidad = new HashMap<String, String>();
		HashMap<String, String> mapaMinsmaEntidad2 = new HashMap<String, String>();
		
		
		//Mapa de valores múltiples (SEGUIR AQUÍ, TERCER APARTADO (los dos mas interesantes))
	}
	
	//Métodos para mapa de entidad:
	
	//Método de creación:
	
	private HashMap<Integer, String> crearMapa() {
		return new HashMap<Integer, String>();
	}
	
	//Método de carga:
	private void cargarMapa(HashMap<Integer, String> mapa, int key, String value) {
		mapa.put(key, value);
	}
	
	//Método de Consulta:
	private String consultaMapa(HashMap<Integer, String> mapaConsulta, int key) {
		return mapaConsulta.get(key);
	}
	
	//Método de recorrido:
	private void recorreMapa(HashMap<Integer, String> mapaArecorrer) {
		for (Integer i: mapaArecorrer.keySet()) {
			System.out.println("Para la clave " + i + ", el valor es: " + mapaArecorrer.get(i));
		}
	}
	
	
	
	//Métodos para varios mapas de la misma entidad:
	
	//Método de creación:
	
	private HashMap<String, String> crearMapa2() {
		return new HashMap<String, String>();
	}
	
	//Método de carga:
	private void cargarMapa2(HashMap<String, String> mapaEntidad, String clave, String value) {
		mapaEntidad.put(clave, value);
	}
	
	//Método de consulta:
	private String consultaMapa2(HashMap<String, String> mapaConsulta, String key) {
		return mapaConsulta.get(key);
	}
	
	private void recorrerMapa2(HashMap<String, String> mapaRecorrer) {
		for (String k: mapaRecorrer.keySet()) {
			System.out.println("Para la clave " + k + ", el valor es: " + mapaRecorrer.get(k));
		}
	}
	
	
	
}
