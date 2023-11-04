package cap00;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ejercicio06 extends JFrame{
	
	public static void main(String[] args) {
		
		Ejercicio06 vent = new Ejercicio06();
		vent.setVisible( true );
		
	}
	
	private static JTable tabla;
	private static DefaultTableModel modelo;
	private JComboBox<String> tamayoTabla;
	private JButton btnAleatorio;
	private Random r = new Random();
	
	public Ejercicio06() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(700,700);
		setLocationRelativeTo( null );
		
		tamayoTabla = new JComboBox<String>();
		tamayoTabla.addItem("2x2");
		tamayoTabla.addItem("3x3");
		tamayoTabla.addItem("4x4");
		tamayoTabla.addItem("5x5");
		
		tamayoTabla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tamayoTabla.getSelectedItem().equals("2x2")) {
					modelo.setRowCount(2);
					modelo.setColumnCount(2);
				}
				else if (tamayoTabla.getSelectedItem().equals("3x3")){
					modelo.setRowCount(3);
					modelo.setColumnCount(3);
				}
				else if (tamayoTabla.getSelectedItem().equals("4x4")) {
					modelo.setRowCount(4);
					modelo.setColumnCount(4);
				}
				else {
					modelo.setRowCount(5);
					modelo.setColumnCount(5);
				}
			}
		});
		
		
		btnAleatorio = new JButton("Aleatorio");
		btnAleatorio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				aleatorio();
				
			}
		});
		
		modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		
		
		//Añadir al contenedor
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(tamayoTabla);
		pnlSouth.add(btnAleatorio);
		getContentPane().add(pnlSouth,BorderLayout.SOUTH);
		getContentPane().add(new JScrollPane(tabla),BorderLayout.CENTER);
		
	}
	
	private void aleatorio() {
		
		Thread hilo = new Thread() {
			
			public void run() {
				
				long tiempoInicio = System.currentTimeMillis();
				while (System.currentTimeMillis()-tiempoInicio < 3000) { //Durante 3 segundos
					for (int i = 0; i < modelo.getRowCount(); i ++) {
						for (int j = 0; j < modelo.getColumnCount(); j++) {
							int numero = (int)(r.nextInt(1000))+1;
							modelo.setValueAt(numero + "", i, j);
						}
					}
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			};
		};
		hilo.start();
		 
	}
}
