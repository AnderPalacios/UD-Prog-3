package cap05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Ejercicio_5_4 {
	
	public static void main(String[] args) {
		
		//Un mapa de entidad:
		HashMap<Integer, String> mapaEntidad = new HashMap<Integer, String>();
		
		//Varios mapas de la misma entidad:
		HashMap<String, String> mapaMinsmaEntidad = new HashMap<String, String>();
		HashMap<String, String> mapaMinsmaEntidad2 = new HashMap<String, String>();
		
		
		//Mapa de valores múltiples:
		HashMap<Integer, ArrayList<String>> mapaValoresMultiples = new HashMap<Integer, ArrayList<String>>();
		
		//Mapa de mapas
		HashMap<Integer, HashMap<String, Integer>> mapaDeMapas = new HashMap<Integer, HashMap<String,Integer>>();
		
		//Para practicar:
		HashMap<String, Integer> ints = new HashMap<String, Integer>();
		ints.put("Ander", 14);
		mapaDeMapas.put(1, ints);
		Ejercicio_5_4 ej = new Ejercicio_5_4();
		ej.recorrerMapa4(mapaDeMapas);
		
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
	
	
	//Métodos para mapa de valores múltiples:
	
	//Método de creación:
	private HashMap<Integer, ArrayList<String>> crearMapa3() {
		return new HashMap<Integer, ArrayList<String>>();
	}
	
	//Método de carga:
	private void cargarMapa3(HashMap<Integer, ArrayList<String>> mapaACargar, int k, ArrayList<String> arrayACargar) {
		if (!mapaACargar.containsKey(k)) {
			mapaACargar.put(k, arrayACargar);
		} else {
			ArrayList<String> yaEstaClave = mapaACargar.get(k);
			for (String s: arrayACargar) {
				yaEstaClave.add(s);
			}
			mapaACargar.put(k, yaEstaClave);
		}
	}
	
	//Método consulta Mapa:
	private ArrayList<String> consultaMapa2(HashMap<Integer, ArrayList<String>> mapaCons, int k) {
		if (!mapaCons.containsKey(k)) {
			return mapaCons.get(k);
		}
		return null;
	}
	
	
	//Método para recorrer el mapa:
	private void recorrerMapa3(HashMap<Integer, ArrayList<String>> mapaRec) {
		for (Integer key: mapaRec.keySet()) {
			System.out.println(key);
			for (String s: mapaRec.get(key)) {
				System.out.println(s);
			}
		}
	}
	
	
	//Métodos para mapa de mapas:
	
	//Método de creación:
	private HashMap<Integer, HashMap<String, Integer>> crearMapa4() {
		return new HashMap<Integer, HashMap<String,Integer>>();
	}
	
	//Método de carga:
	private void cargarMapa4(HashMap<Integer, HashMap<String, Integer>> mapaMapa, int key, HashMap<String, Integer> map, String s)  {
		if (!mapaMapa.containsKey(key)) {
			mapaMapa.put(key, map);
		} else { // Si la clave principal ya existe, actualizamos el mapa secundario con la clave secundaria y su valor.
			HashMap<String, Integer> mapaSec = mapaMapa.get(key);
			mapaSec.put(s, mapaSec.get(s));
			
		}
	}
	
	//Método consulta mapa:
	private HashMap<String, Integer> consultaMapa4(HashMap<Integer, HashMap<String, Integer>> mapaC, int clave) {
		if (mapaC.containsKey(clave)) {
			return mapaC.get(clave);
		}
		return null;
	}
	
	private void recorrerMapa4(HashMap<Integer, HashMap<String, Integer>> mapaRec) {
		for (Integer k: mapaRec.keySet()) {
			System.out.println(mapaRec.get(k));
			for (String s: mapaRec.get(k).keySet()) {
				System.out.println(mapaRec.get(k).get(s));
			}
			/*
			 * HashMap<String, Integer> m = mapaRec.get(k);
			 * for (String s: m.keySet(){
			 * 	syso(m.get(s))
			 * {
			 */
		}
	}
}
