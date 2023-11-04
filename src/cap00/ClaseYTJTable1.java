package cap00;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class ClaseYTJTable1 {

	
	private static JFrame vent;
	private static JTable tabla;
	private static DefaultTableModel modelo; 
	//El modelo de datos que va a tener la JTable
	//El modelo manda las cosas que van a ver dentro de la tabla (sabe dnd están los datos, cuantas filas...)
	//JTable se ocupa de la visualizacion y el modelo del almacenamiento y gestion de datos.
	//La tabla solo sabe como se dibujan y editan los datos.
	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		
		//Ventana rápida
		vent = new JFrame();
		vent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		vent.setSize(400, 300);
		// Tamaño de pantalla: Toolkit.getDefaultToolkit().getScreenSize(); Devuelve el tamaño de mi escritorio
		vent.setLocation(300, 300);
		
		// Tabla en ventana
		modelo = new DefaultTableModel(new Object[] {"Nom", "Cod"}, 0);
		tabla = new JTable( modelo );
		// Esta manera también sirve: tabla.setModel(modelo);
		vent.getContentPane().add(new JScrollPane( tabla ), BorderLayout.CENTER);//por defecto es un Border
		
		//Añadir datos
		modelo.addRow( new Object[] {"Itziar", 70});
		//Otra manera:
		Vector<Object> v = new Vector<Object>();
		v.add("Andoni");
		v.add( 120 ); 
		modelo.addRow( v );
		modelo.addRow( new Object[] {"Xabi", 220});
		
		//Cambios de anchura
		tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(1).setMaxWidth(100);
		tabla.getColumnModel().getColumn(1).setMinWidth(80);
		//Prefer es mi intención
		
		
		//Investigar el modelo
		modelo = new DefaultTableModel(new Object[] {"Nom", "Cod"}, 0) { //Herencia (extiende de la clase tableModel) es una clase interna anónima
			
//			@Override
//			public Class<?> getColumnClass(int columnIndex) { //Es para asociar cada columna a un tipo de objeto
//				return super.getColumnClass(columnIndex);
//			}

			@Override
			public boolean isCellEditable(int row, int column) {
				boolean var = super.isCellEditable(row, column);
				System.out.println("isCellEditabel" + row + "," + column + var);
				if (column == 0) return false;
				else return super.isCellEditable(row, column);
			}

			@Override
			public Object getValueAt(int row, int column) {
				Object val = super.getValueAt(row, column);
				System.out.println("getValueAt" + row + "," + column + " <- " + val);
				return val; //+ "(c) Andoni" Quiero que se visualicen cosas distintas? pues cambio el get
			}

			@Override
			public void setValueAt(Object aValue, int row, int column) {
				System.out.println("setValueAt" + row + "," + column + " -> " + aValue);
				try {
					int valor = Integer.parseInt(aValue + ""); //Porque puede ser que de una excepción
					if (valor <= 255) {
						super.setValueAt(aValue, row, column);
					}
				}
				catch(NumberFormatException e) {
					//No cambia nada
				}
			}
			
		};
		modelo.addRow( new Object[] {"Itziar", 70});
		modelo.addRow( new Object[] {"Andoni", 120});
		modelo.addRow( new Object[] {"Xabi", 220});
		tabla.setModel(modelo);
		
		tabla.getTableHeader().setReorderingAllowed(false); //Prohibe el movimineto de columnas del usuario
		
		
		//Renderer es un componente que se utiliza para pintar
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() { //Esto cambia el renderer de toda la tabla y .class es la referencia de esa clase

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				System.out.println((comp instanceof JLabel) +  " -> " + comp);
				JLabel label = (JLabel) comp;
				if (column == 0) {
					comp.setBackground(Color.white);
				}
				else { // column == 1
					int valor = (Integer) modelo.getValueAt(row, column);
					//int valor = Integer.parseInt(value.toString());
					comp = new JSlider(0, 255, valor); //No se mueve, renderer solo se utiliza para pintar, los JSliders no se mueven. No es un componente real, por eso no se deja editar
				}
				if (isSelected) {
					comp.setBackground(Color.LIGHT_GRAY);
					if (hasFocus) {
						label.setBorder(BorderFactory.createLineBorder(Color.green, 3));
					}
				}
				return comp;
			}
			
		});
		
		tabla.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField() ) {
			Component ret; //Usar atributos en vez de variables locales porq no lo voy a usar solo en el método getTableCellEditorComponent
			int ValorAnt;
			JTextField tf;
				
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				ret =  super.getTableCellEditorComponent(table, value, isSelected, row, column);
				tf = (JTextField) ret;
				if (column == 1) {
					tf = null;
					int valor = Integer.parseInt(value.toString());
					ret = new JSlider(0,255, valor);
					((JSlider)ret).addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							ValorAnt = ((JSlider)ret).getValue();
						}
					});
				}
	
				return ret;
			}

			@Override
			public Object getCellEditorValue() { //Cuando sales del editor se swing llama a este metodo para saber que valor es el que ese editor ha editado
				if (tf != null) {
					return tf.getText();
				} else {
					return new Integer( ValorAnt );
				}
				
			}
			
		});
		
		tabla.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.rowAtPoint(e.getPoint()); // La fila de la tabla en la cual se ha hecho la pulsación del raton
				int col = tabla.columnAtPoint(e.getPoint());
				System.out.println("Click en la fila " + fila + "," + col); 
			}
		});
			
		//Solo hay editor (solo se edita una celda al momento)pero renderers hay muchos
		
//		System.out.println(modelo.isCellEditable(2, 2));
//		System.out.println(modelo.getValueAt(2, 1));
		System.out.println();
		
		vent.setVisible( true );
		}
}
