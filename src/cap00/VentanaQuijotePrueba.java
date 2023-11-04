package cap00;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;

/** Ejercicio de hilos  con ventanas. Esta clase carga el texto del Quijote en un área de texto,
 * y permite navegar por el área con la scrollbar y con botones de página arriba y página abajo.
 * 1. Modificarlo para que al pulsar los botones el scroll se haga con una animación 
 * a lo largo de un segundo, en lugar de en forma inmediata.
 * 2. Prueba a pulsar muy rápido varias páginas abajo. ¿Cómo lo arreglarías para que el scroll
 * en ese caso funcione bien y vaya bajando una página tras otra pero las baje *completas*?
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class VentanaQuijotePrueba extends JFrame {
	
	private static final long MSGS_REFRESCO = 5;
	
	public static void main(String[] args) {
		VentanaQuijotePrueba v = new VentanaQuijotePrueba();
		v.setVisible( true );
		v.cargaQuijote();
	}

	private JTextArea taTexto;
	private JScrollPane spTexto;
	
	private Thread hiloArriba;
	private Thread hiloAbajo;
	
	public VentanaQuijotePrueba() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle( "Don Quijote de la Mancha" );
		setSize( 800, 600 );
		setLocationRelativeTo( null );  // Pone la ventana relativa a la pantalla (la pone en el centro de pantalla)
		
		taTexto = new JTextArea();
		spTexto = new JScrollPane( taTexto );
		add( spTexto, BorderLayout.CENTER );
		
		JPanel pBotonera = new JPanel();
		JButton bPagArriba = new JButton( "^" );
		JButton bPagAbajo = new JButton( "v" );
		pBotonera.add( bPagArriba );
		pBotonera.add( bPagAbajo );
		add( pBotonera, BorderLayout.SOUTH );
		
		bPagArriba.addActionListener( new ActionListener() { //Clase interna anónima
			@Override
			public void actionPerformed(ActionEvent e) {
				muevePagina( -(spTexto.getHeight()-20) );
				
			}
		});
		bPagAbajo.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				muevePagina( (spTexto.getHeight()-20) );
				
			}
		});
		
	}
	
	private Thread ultimoHilo = null;

	private void muevePagina( int pixelsVertical ) {
		
		Thread hilo = new Thread() {
			
			@Override
			public void run() {
				
				if (ultimoHilo != null) {
					System.out.println("Hilo " + getName() + " espera a " + ultimoHilo.getName() + "..." );
					//getName() el hilo 'this', espera al ultimo hilo antes que el que ha sido creado.
					Thread anterior = ultimoHilo;
					ultimoHilo = this;
					try {
						anterior.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //Se espera a que este acabe
					System.out.println("Final de la espera Hilo " + getName() + " comienza su trabajo." );
					
				}
				
				else {
					ultimoHilo = this;
				}
				// TODO Cambiar este comportamiento de acuerdo a los comentarios de la cabecera de clase
				JScrollBar bVertical = spTexto.getVerticalScrollBar(); //returns the vertical view position
				System.out.println( "Moviendo texto de " + bVertical.getValue() + " a " + (bVertical.getValue()+pixelsVertical) );

				if (pixelsVertical > 0) {
					for (int i = 0; i < pixelsVertical; i++) {
						bVertical.setValue(bVertical.getValue() + 1);
						try {
							Thread.sleep(MSGS_REFRESCO);
						} catch (InterruptedException e) {
							//e.printStackTrace();
							return;
						}
					}
				}
				else {
					for (int i = 0; i < Math.abs(pixelsVertical); i ++) {
						bVertical.setValue(bVertical.getValue() - 1);
						try {
							Thread.sleep(MSGS_REFRESCO);
						} catch (InterruptedException e) {
							//e.printStackTrace();
							return;
						}
					}
				}
				//System.out.println("Trabajo del hilo: " + colaDeHilos.get(0).getName() + " terminado.");
				if (ultimoHilo == this) {
					ultimoHilo = null;
				}
				
			}
	   };
	   hilo.start();
	}
	
	private void cargaQuijote() {
		try {
			Scanner scanner = new Scanner( VentanaQuijotePrueba.class.getResourceAsStream( "DonQuijote.txt" ), "UTF-8" );
			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				taTexto.append( linea + "\n" );
			}
			scanner.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, "No se ha podido cargar el texto" );
		}
	}

}

/*
spTexto.getHight devulve la altura que se ven en el textArea, en este caso 529. 
Por ello, el punto mas alto es el 0, la primera línea. Cada vez que muevo para arriba o abajo,
muevo 509 píxeles; spTexto.getHeight()-20. bVertical.getValue() devulve el pixel o mejor dicho,
la altura del pixel a la que estás.
*/

