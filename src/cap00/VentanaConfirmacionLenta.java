package cap00;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;


/** Ejercicio de hilos con ventanas. Programa esta clase para que se cree una ventana

 * que pida un dato de texto al usuario y un botón de confirmar para que se confirme.

 * Haz que al pulsar el botón de confirmación se llame al procesoConfirmar()

 * que simula un proceso de almacenamiento externo que tarda unos segundos.

 * Observa que hasta que ocurre esta confirmación la ventana no responde.

 * 1. Arréglalo para que la ventana no se quede "frita" hasta que se acabe de confirmar.

 * 2. Haz que el botón de "confirmar" no se pueda pulsar dos veces mientras el proceso

 * de confirmación se esté realizando

 * @author andoni.eguiluz @ ingenieria.deusto.es

 */

public class VentanaConfirmacionLenta {

		private static Thread hilo; //static porq estoy en una metodo/clase que es static

		private static Random r = new Random();

	// Este método simula un proceso que tarda un tiempo en hacerse (entre 5 y 10 segundos)

	private static void procesoConfirmar() {

		try {

			Thread.sleep( 5000 + 1000*r.nextInt(6) );

		} catch (InterruptedException e) {}

	}

	public static void main(String[] args) { //determina si las  clases anónimas van a ser estáticas

		verHilos( "Antes de ventana" );

		JFrame vent = new JFrame();

		// vent.setLayout( new BorderLayout() );

		vent.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

		vent.setSize( 300, 300 );

		vent.setLocation( 400, 400 );



		JPanel pCentral = new JPanel();

		JTextField textFieldUsuario = new JTextField( "Usuario:" );

		pCentral.add( textFieldUsuario );

		vent.add( pCentral, BorderLayout.NORTH );

		

		JButton bAceptar = new JButton( "Aceptar" );

		vent.add( bAceptar, BorderLayout.SOUTH );



		bAceptar.addActionListener( new ActionListener() {

			// Truco de Java: las variables externas que uses aquí se copian en la clase interna

			// JButton bAceptar = valor bAceptar de la clase externa (muy importante)

			// A cambio, tiene que ser final (no puede cambiar de valor)

			@Override

			public void actionPerformed(ActionEvent e) {

				bAceptar.setEnabled(false);

				System.out.println( "Pulsado botón" );

				bAceptar.setForeground( Color.RED ); 

				hilo = new Thread() { //Crea clase interna anónima que tiene una interfaz, el hilo es de una clase interna anónima

					//Clase interna anónima

					@Override

					public void run() { //Es el código principal del hilo

						// TODO Auto-generated method stub

						System.out.println("Inicio proceso");

						System.out.println(hilo.isAlive() + " " + hilo.getState());

						procesoConfirmar();

						System.out.println("Fin proceso");

						bAceptar.setEnabled(true);

					}

				};
				// Podría hacerse impl Runnable
//				Thread hilo2 = new Thread( new Runnable() {
//					@Override
//					public void run() {
//						procesoConfirmar();
//					}
//				});
				// procesoConfirmar(); en vez de hacerlo desde el escuchador que lo haga el hilo
				// hilo.run();

				hilo.start(); // Magia -- se CREA un nuevo hilo de ejecución y se le pasa la tarea de run()
				
			}

		});//Tres mains/codigos diferentes: main,boton,hilo

		

		vent.setVisible( true );

		System.out.println( "Fin, el hilo del main ha terminado" );

		verHilos( "Después de ventana" );

	}

	

	private static void verHilos( String mensaje ) {

		System.out.println( mensaje );

		for (Thread hilo : Thread.getAllStackTraces().keySet()) {

			System.out.println( "  " + hilo.getName() + " " + hilo.isDaemon() );

		}

	}

	private static class MiHilo extends Thread{ //static es lo global, solo existe una vez (por eso clase interna static)

		//clase interna no anónima

		//static para clases y no static para crear objetos de esa clase

		//Esta es estática, no depende de objetos

	}

}

/*
Mis apuntes: Una clase interna anónima es una clase que se define en el lugar donde se necesita, 
sin un nombre específico (creas una clase pero sin darle un nombre (es una calse porq está implementando una Interfaz o extendidno una clase abstracte)),
y se crea una instancia de ella al mismo tiempo que se define (esto último importatnte). Esto es útil cuando deseas implementar una interfaz o extender 
una clase abstracta de manera rápida sin tener que crear una clase separada.
*/
