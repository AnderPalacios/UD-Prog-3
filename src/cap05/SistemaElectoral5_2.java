 package cap05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class SistemaElectoral5_2 {
	
	private static String[] nombres = {"Luis", "María", "Elena", "Andoni", 
			"Isabel", "Asier", "Andoni", "Luis", "Carlos", "Elena", "Luis", "Aitziber", "Luis"};
	
	public static void main(String[] args) {
		
		LinkedList<String> listaNombres = new LinkedList<String>(Arrays.asList(nombres));
		System.out.println(listaNombres);

		Iterator<String> iterator = listaNombres.descendingIterator();
		ArrayList<String> arrayListInvertido = new ArrayList<String>();
		while (iterator.hasNext()) {
			arrayListInvertido.add(iterator.next());
		}
		System.out.println(arrayListInvertido);
		
//		Esto también funciona:
//		HashSet<String> setDeNombres = new HashSet<String>(listaNombres);
//		System.out.println(setDeNombres);
		
		HashSet<String> setDeStrings = new HashSet<String>();
		for (String nombre: arrayListInvertido) {
			setDeStrings.add(nombre);
		}
		System.out.println(setDeStrings);
		System.out.println("Número de nombres: " + setDeStrings.size());
		
		
		//Implementación 2:
		HashMap<String, Integer> mapaContadorPorNombre = new HashMap<String, Integer>();
		for (String nombre: arrayListInvertido) {
			if (!mapaContadorPorNombre.containsKey(nombre)) {
				mapaContadorPorNombre.put(nombre, 1);
			} else {
				int cuenta = mapaContadorPorNombre.get(nombre);
				mapaContadorPorNombre.put(nombre, cuenta+1);
				// or: mapaContadorPorNombre.put(nombre, mapaContadorPorNombre.get(nombre)+1);
			}
		}
		//Scaralo por pantalla:
		for (String key: mapaContadorPorNombre.keySet()) {
			System.out.println(key + " = " + mapaContadorPorNombre.get(key));
		}
	}
	
}

//Su implementación:

//LinkedList<String> lista = new LinkedList<>(Arrays.asList(nombres)); //Ve el array como si fuera una lista (en esta carga le pasas un collection). El contenido se replica en la lista (la lista apunta a estos mismos datos)
//System.out.println(lista);
//
//Iterator<String> iterator = lista.descendingIterator(); //Itera desde el final
//ArrayList<String> nombresInvertidos = new ArrayList<String>();
//while (iterator.hasNext()) {
//	nombresInvertidos.add(iterator.next());
//}
//System.out.println(nombresInvertidos);
//
////Cuenta con HashSet de Strings
//HashSet<String> hs = new HashSet<String>();
//for (String n: nombresInvertidos) {
//	hs.add(n);
//}
//System.out.println(hs);
//System.out.println("Número de nombres: " + hs.size()   );