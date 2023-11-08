package cap01;

public class OrdenarStrings {
	
	public static void main(String[] args) {
		
		compararString("a", "b");
		compararString("b", "a");
		compararString("a", "a");
		compararString("A", "b");
		compararString("a", "B"); //Como está comparando el código ASCII 
		compararString("ala", "Beta");
		compararString("caña", "capa");
		
	}
	
	private static void compararString(String s1, String s2) {
		int comparacion = UtilsString.convierteOrd(s1).compareTo(UtilsString.convierteOrd(s2));
		if (comparacion < 0) {
			System.out.println(s1 + " es anterior a " + s2);
		} else if (comparacion > 0) {
			System.out.println(s1 + " es posterior a " + s2);
		} else {
			System.out.println(s1 + " es igual a " + s2);
		}
	}

}
