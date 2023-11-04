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

public class PruebasConHilos extends JFrame{
	
	private static final Dimension tamayo = new Dimension(700,700); //Para evitar los 'numeros mágicos'.

	public static void main(String[] args) {
		PruebasConHilos prueba = new PruebasConHilos();
		prueba.setVisible(true);
		//prueba.cargarDatos();
	}
	
	private JTextArea taTextos;
	private JScrollPane sptexto;
	
	public PruebasConHilos() {
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
				for (Thread t: colaHilos) {
					t.interrupt();
				}
				
			}

		});
		
	}
	
	private Vector<Thread> colaHilos = new Vector<Thread>();
	
	private void ejecutar() {
		Thread hilo = new Thread() {
			
			public void run() {
				//Manejo de hilos:
				 colaHilos.add(this);
				 while (colaHilos.get(0) != this) {
					 if (interrupted()) return;
					 System.out.println(this.getName() + " tiene esta cola de hilos:  " + colaHilos);
					 try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						//e.printStackTrace();
						return;
					}
				 }
				
				//Acción que voy a hacer:
				for (int i = 0; i < 30; i ++) {
					if (interrupted()) return;
					taTextos.append("A \n");
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						//e.printStackTrace();
						return;
					}
				}
				System.out.println("Trabajo de hilo: " + this.getName() + " terminado");
				colaHilos.remove(0);
			};
		};
		hilo.start();
	}
	
//	private void cargarDatos() {
//		Scanner texto = new Scanner("");
//	}
}
