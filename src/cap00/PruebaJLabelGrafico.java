package cap00;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class PruebaJLabelGrafico extends JFrame {
	
	private static final Dimension TAMAYO_VENTANA = new Dimension(600, 400);
	
	public static void main(String[] args) {
		PruebaJLabelGrafico vent = new PruebaJLabelGrafico();
		vent.setVisible( true );
		vent.mover();
	}
	
	// No static
	
	private JPanel pJuego;
	private MiCoche lCoche;
	
	public PruebaJLabelGrafico() {
		// Configuración de la ventana
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE ); //Exit on close acaba con el programa, 
		//esto no hace que el programa acaba, sino que cierre la ventana.
		//setSize( 600, 400); en vez de números mágicos;
		setSize(TAMAYO_VENTANA);
		
		// setLocation( 2000, 0 );
		
		// Creamos los contenedores
		pJuego = new JPanel();
		pJuego.setLayout( null ); //Lo pones nulo para que yo ponga el tamaño que quiero de coche o lo que sea
		
		// Creamos los componentes
		// Se puede hacer con ficheros:
		// lCoche = new JLabel( new ImageIcon( "src/es/deusto/prog3/cap00/resueltos/coche.png" ));
		//si no inducas ruta, te lo busca en la carpeta raíz del proyecto esto es accederlo como fichero
		
		// O con recursos (esto es el acceso como recurso):
		// lCoche = new JLabel( new ImageIcon( PruebaJLabelGrafico.class.getResource("coche.png") ) );
		// Lo vamos a hacer con una clase personalizada que dibuja mejor lo que queremos que JLabel:
		lCoche = new MiCoche( new ImageIcon( PruebaJLabelGrafico.class.getResource("coche.png") ) );
		
		// Asociamos componentes a contenedores
		pJuego.add( lCoche );
		getContentPane().add( pJuego, BorderLayout.CENTER );
		// Gestión de eventos (si procede)
		
	}
	
	private void mover() {
		// Hacer algo con el coche
		// lCoche.setSize( 100, 100 );  // Tamaño
		// lCoche.setLocation( 100, 0 );  // Posición
		// lCoche.setBounds( 100, 0, 100, 100);  // Tamaño y posición
		double direccionPrueba = Math.PI/8;  // Prueba de movimiento en dirección 22,5º (en radianes)
		double x = lCoche.getX();  // Para movimiento fino mejor números reales que enteros
		double y = lCoche.getY();
		double pixelsPorPaso = 5;
		lCoche.setDireccion( direccionPrueba );
		// Animación de movimiento
		for (int i=0; i<100; i++) {
			x += pixelsPorPaso*Math.cos(direccionPrueba);
			y += pixelsPorPaso*Math.sin(direccionPrueba);
			lCoche.setLocation( (int)x, (int)y );
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
		// Animación de rotación
		for (int i=0; i<200; i++) {
			direccionPrueba += 0.05;
			lCoche.setDireccion( direccionPrueba ); //Claro, setDireccion lo he creado yo, 
			//Swing no sabe que que tiene que cambiar la direccion. setLocation() es un cambio explicito.
			//Ya estaba creado y Swing sabe que tiene que repintarlo.
			//Entonces:
			lCoche.repaint();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {//Va a estar dormido excepto si lo interrumpe (otro hilo)
				//e.printStackTrace();
			}
		}
		// Pregunta: ¿por qué no se esta viendo la animación de rotación, y sí se ve la de movimiento?
		// Pista: redimensiona la ventana mientras está ocurriendo esta animación
		// ¿Cómo se soluciona?
	}

	/** Clase que permite crear JLabels adaptadas a un gráfico de coche que puede girar y se escala a 100x100 píxeles
	 * @author andoni.eguiluz @ ingenieria.deusto.es
	 */
	private class MiCoche extends JLabel {
		
		private static final int TAMAYO_COCHE = 100; //Static porque quiero que todos los coches tengan el mismo tamaño
		//En vez de 
		private double direccion; // Ángulo de giro del coche

		/**
		 * @param i
		 */
		public MiCoche( ImageIcon i ) {
			super( i );
			setSize(TAMAYO_COCHE,TAMAYO_COCHE);
		}
		
		/** Cambia la dirección del coche
		 * @param direccion	Nuevo ángulo (en radianes)
		 */
		public void setDireccion(double direccion) {
			this.direccion = direccion + Math.PI/2; 
			// Reflexión ¿Por qué se suman 90º?
		}
		
		@Override
		protected void paintComponent(Graphics g) { //Cambiar esto para dibujar distinto, 
			//swing repinta continuamente la ventana, llama a este método.
			
			// super.paintComponent(g);  // No vamos a dibujar igual que un JLabel
			Graphics2D g2 = (Graphics2D) g;  // Componente gráfico en el que dibujar (Graphics2D tiene más funcionalidad que Graphics)
			Image img = ((ImageIcon)getIcon()).getImage();  // Imagen del label - la vamos a dibujar diferente
			g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );  // suaviza la rotación
			g2.rotate( direccion, TAMAYO_COCHE/2, TAMAYO_COCHE/2 );  // Rota la imagen
			g2.drawImage( img, 0, 0, 100, 100, null  );  // Dibuja la imagen escalando a 100x100
		}
	}
	
}


/*
Main es un hilo y cuando pongo el setVisile(true), Swing tira su hilo.
Entones, Swing llama al paintComponent() cuando llego a la línea de repaint() pero eso para 
setDireccion() y Swing va llamando a paintComponent() muchas veces por segundo y el es que pinta 'actualiza'
*/






