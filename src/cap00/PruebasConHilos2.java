package cap00;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PruebasConHilos2 extends JFrame{
	
	private static final Dimension tamayo = new Dimension(700,700); //Para evitar los 'numeros mágicos'.

	public static void main(String[] args) {
		PruebasConHilos2 prueba = new PruebasConHilos2();
		prueba.setVisible(true);
		//prueba.cargarDatos();
	}
	
	private JTextArea taTextos;
	private JScrollPane sptexto;
	private boolean noseguir;
	private Thread UltimoHilo = null; //Es el hilo que está detrás de otro
	
	public PruebasConHilos2() {
		setSize(tamayo);
		setLocationRelativeTo( null );
		setTitle( "Pruebas con hilos" );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JButton btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutar();
				
			}
		});
		add(btnEjecutar,BorderLayout.SOUTH);
		taTextos = new JTextArea();

		sptexto = new JScrollPane(taTextos);
		add(sptexto, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {

				if (UltimoHilo != null) {
					UltimoHilo.interrupt();
				}
				
			}

		});
		
	}
	
	//private Vector<Thread> colaHilos = new Vector<Thread>();
	
	private void ejecutar() {
		Thread hilo = new Thread() {
			
			public void run() {
				//Manejo de hilos:
				
				noseguir = true;
				// Como seguir es una variable de clase, aunq entre en otro hilo,
				//afecta al anterior.
				
				 while (UltimoHilo != null) {
					 if (interrupted()) return;
					 taTextos.append("\n");
					 taTextos.append(UltimoHilo.getName() + " parado. \n");
					 taTextos.append("\n");
					 try {
						Thread.sleep(800); // Probar con 80 para ver lo que hace
					} catch (InterruptedException e) {
						//e.printStackTrace();
						return;
					}
				 }
				 noseguir =  false;
				 UltimoHilo = this;
				
				//Acción que voy a hacer:
				for (int i = 1; i < 31; i ++) {
					if (noseguir) break;
					if (interrupted()) return;
					taTextos.append("Iteración de bucle " + i + " del hilo: " + UltimoHilo.getName() + "\n");
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						//e.printStackTrace();
						return;
					}
				}
				
				UltimoHilo = null;
				//System.out.println("Trabajo de hilo: " + this.getName() + " terminado");
			};
		};
		hilo.start();
	}
	
//	private void cargarDatos() {
//		Scanner texto = new Scanner("");
//	}
}

