package cap01;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class UtilsStringTest {
	
//	@Before
//	public void setUp() throws Exception {
//		System.out.println("Before");
//	}
//	
//	@After
//	public void tearDown() throws Exception {
//		System.out.println("After");
//	}

	
	@Test
	public void testWrapString() {
//		System.out.println("test1");
		String prueba = "Hola\nEsto es un string con tres líneas\ny\tvarios\ttabuladores.";
		assertEquals("Comprobación wrapString", "Hol...", UtilsString.wrapString( prueba, 3));
//		if ("Hol...".equals(UtilsString.wrapString( prueba, 3))) {
//			System.out.println( "OK" );
//		} else {
//			System.out.println( "FAIL" );
//		}
		assertEquals("", UtilsString.wrapString("", 1));
		assertEquals("", UtilsString.wrapString("", 0));
		assertEquals("abc...", UtilsString.wrapString("abcdefg", 3));
		assertEquals("abcef...", UtilsString.wrapString("abcdefg", 6));
		assertEquals("abcdefg", UtilsString.wrapString("abcdefg", 7));
		assertEquals("abcdefg", UtilsString.wrapString("abcdefg", 15));
		assertEquals("abc\te...", UtilsString.wrapString("abcd\tefg", 6));
	}
	
	@Test
	public void testWrapStringNegativo() { //Como esta prueba es un poco diferente, hage otro test
		try {
			UtilsString.wrapString("abcde", -5);
			fail("No provocada excepción");
		} catch (IndexOutOfBoundsException e) {
			//No hace falta hacer nada explícito - acabar es que funciona
			//assertTrue(true); se puede poner para saber que va bien
		}
	}
	
	//Otra manera de probar excepciones:
	@Test (expected = IndexOutOfBoundsException.class) //Esto hace lo mismo que el de arriba  
	public void testWrapStringNegatico() {
		UtilsString.wrapString("abcde", -5);
	}
	
	@Test 
	public void testQuitarTabsYSaltosLinea() {
//		System.out.println("test2");
		String prueba = "Hola\nEsto es un string con tres líneas\ny\tvarios\ttabuladores.";
		String prueba2 = "Hola#Esto es un string con tres líneas#y|varios|tabuladores.";
		assertEquals(prueba2, UtilsString.quitarTabsYSaltosLinea(prueba)); //assertEquals nos hace la comprobación
//		if (prueba2.equals(UtilsString.quitarTabsYSaltosLinea(prueba))) {
//			System.out.println( "OK" );
//		} else {
//			System.out.println( "FAIL" );
//			fail("Error en quitartabysaltoslinea");
//		}
		
		assertEquals("", UtilsString.quitarTabsYSaltosLinea(""));
		assertEquals("sin tabs", UtilsString.quitarTabsYSaltosLinea("sin tabs"));
		assertEquals("a|b", UtilsString.quitarTabsYSaltosLinea("a\tb"));
		assertEquals("a#b", UtilsString.quitarTabsYSaltosLinea("a\nb"));
		assertEquals("a|||b", UtilsString.quitarTabsYSaltosLinea("a\t\t\tb"));
		assertEquals("a###b", UtilsString.quitarTabsYSaltosLinea("a\n\n\nb"));
		assertEquals("|#|#", UtilsString.quitarTabsYSaltosLinea("\t\n\t\n"));
		
	}
	
	
	@Test
	public void testQuitarTabsYSaltosLineaNull() {
		assertNull(UtilsString.quitarTabsYSaltosLinea(null));
	}
	
	
	@Test
	public void testConvierteOrdMay() {
//		compararString("a", "b");
//		compararString("b", "a");
//		compararString("a", "a");
//		compararString("A", "b");
//		compararString("a", "B");
//		compararString("ala", "Beta");
//		compararString("caña", "capa");
		assertTrue(UtilsString.convierteOrd("a").compareTo(UtilsString.convierteOrd("b")) < 0);
		assertTrue(UtilsString.convierteOrd("b").compareTo(UtilsString.convierteOrd("a")) > 0);
		assertTrue(UtilsString.convierteOrd("a").compareTo(UtilsString.convierteOrd("b")) == 0);
		assertTrue(UtilsString.convierteOrd("A").compareTo(UtilsString.convierteOrd("b")) < 0);
		assertTrue(UtilsString.convierteOrd("a").compareTo(UtilsString.convierteOrd("B")) < 0);
		assertTrue(UtilsString.convierteOrd("ala").compareTo(UtilsString.convierteOrd("beta")) < 0);
	}
	
	//Los haog en distintos tests porque los dos dan error, es para que salgan lod dos.
	@Test
	public void testConvierteEnyes() {
		assertTrue(UtilsString.convierteOrd("caña").compareTo(UtilsString.convierteOrd("capa")) < 0);
	}
	
	@Test
	public void testConvierteTildes() {
		assertTrue(UtilsString.convierteOrd("cántico").compareTo(UtilsString.convierteOrd("cine")) < 0);
		assertTrue(UtilsString.convierteOrd("cómo").compareTo(UtilsString.convierteOrd("casa")) > 0);
		assertTrue(UtilsString.convierteOrd("dé").compareTo(UtilsString.convierteOrd("de")) == 0);
	}
	
	@Test
	public void testConvierteOrdDieresis() {
		assertTrue(UtilsString.convierteOrd("güecho").compareTo(UtilsString.convierteOrd("guion")) < 0);
	}
	
}
