package cap06;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class VentanaTablaDatos extends JFrame{
	
	private static final int COLHAB = 2;
	private static final int COL_AUTONOMIA = 4;
	
	private JTable tablaDatos;
	private ModeloTabla modeloTabla;
	private DataSetMunicipios datosMunis;
	
	//Atributos paso 11:
	private int colSel = -1;
	private String auton;
	
	public VentanaTablaDatos(JFrame ventOrigen) {
		
		setSize(640,480);
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle( "VentanaTabladatos" );
		
		//JPanel pnlCentral = new JPanel();
		tablaDatos = new JTable();
		//pnlCentral.add(new JScrollPane(tablaDatos), BorderLayout.CENTER);
		
		JPanel pnlBotones = new JPanel();
		JButton btnAnyadir = new JButton("Añadir");
		JButton btnBorrar = new JButton("Borrar");
		pnlBotones.add(btnAnyadir);
		pnlBotones.add(btnBorrar);
		
		
		getContentPane().add(pnlBotones, BorderLayout.SOUTH);
		getContentPane().add(new JScrollPane(tablaDatos));
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				ventOrigen.setVisible( false );
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				
				ventOrigen.setVisible( true );
				
			}

		});
		
		btnBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					datosMunis.getListaMunicipios().remove(filaSel);
					modeloTabla.borrarFila(filaSel);
					//tablaDatos.repaint();
				}
			}
		});	
		
		btnAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					datosMunis.anyadir(new Municipio(datosMunis.getListaMunicipios().size()+1
							, "", 0, "", ""), filaSel);
					modeloTabla.anyadirFila(filaSel);
				}
				
			}
		});
	}
	
	public void setDatos(DataSetMunicipios datosMunis) {
		 this.datosMunis = datosMunis;
		 
		 modeloTabla = new ModeloTabla();
		 tablaDatos.setModel(modeloTabla);
		 
		 //Paso 4:
		 tablaDatos.getTableHeader().setReorderingAllowed( false );
		 TableColumn c = tablaDatos.getColumnModel().getColumn(0);
		 c.setMaxWidth(50);
		 c = tablaDatos.getColumnModel().getColumn(2);
		 c.setMinWidth(150);
		 
		 //Paso 7:
		 tablaDatos.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
			 private JProgressBar ph = new JProgressBar(0,5000000) {

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.setColor(Color.black);
					g.drawString(getValue()+"", 50, 10);
				}
				 
			 };

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				 if (column == 2) {
					 ph.setValue( (Integer) value);
					 return ph;
				 }
				return c;
			}
			 
		 });
		 
		 //Paso 8:
		 tablaDatos.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				int filaSel = tablaDatos.rowAtPoint(e.getPoint());
				int colSel = tablaDatos.columnAtPoint(e.getPoint());
				if (filaSel >= 0 && colSel == COLHAB) {
					int poblacion = datosMunis.getListaMunicipios().get(filaSel).getHabitantes();
					tablaDatos.setToolTipText(String.format("Poblacion: %, d" ,poblacion));
				} else {
					tablaDatos.setToolTipText( null );
				}
//				if (colEnTabla == 2) {
//					int numHabs = datosMunis.getListaMunicipios().get(filaEnTabla).getHabitantes();
//					tablaDatos.setToolTipText( String.format( "Población: %,d", numHabs ) );
//				} else {
//					tablaDatos.setToolTipText( null );  // Desactiva
//				}
			}
		});
		 
		 
		 //Paso 11:
		 tablaDatos.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaSel = tablaDatos.rowAtPoint(e.getPoint());
				colSel = tablaDatos.columnAtPoint(e.getPoint());
				if (filaSel >= 0 && colSel == COL_AUTONOMIA) {
					auton = datosMunis.getListaMunicipios().get(filaSel).getAutonomia();
					tablaDatos.repaint();
				}
			}
		});
		 
		tablaDatos.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setBackground(Color.white);
				if (value.equals(auton) && column == COL_AUTONOMIA) {
					c.setBackground(Color.cyan);
				}
				return c;
			}
			
		});
		
		//Paso 12:
		tablaDatos.setDefaultEditor(Integer.class, new DefaultCellEditor(new JTextField() ) {
			SpinnerModel smodel = new SpinnerNumberModel(500000,200000,5000000,1000);
			private JSpinner sp = new JSpinner(smodel);
			

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
				if (column == 2) {
					sp.setValue((Integer) value);
					return sp;
				}
				return c;
			}
			
		});
		 
	}
	
	
	private class ModeloTabla implements TableModel {
		
		private final Class<?>[] tipos = {Integer.class, String.class, Integer.class, String.class, String.class};
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return tipos[columnIndex];
		}
		
		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public int getRowCount() {
			return datosMunis.getListaMunicipios().size();
		}
		
		private final String[] cabeceras = {"Código","Nombre","Habitantes","Provincia","Autonomia"};
		@Override
		public String getColumnName(int columnIndex) {
			return cabeceras[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case (0):
				return datosMunis.getListaMunicipios().get(rowIndex).getCodigo();
			case (1):
				return datosMunis.getListaMunicipios().get(rowIndex).getNombre();
			case (2):
				return datosMunis.getListaMunicipios().get(rowIndex).getHabitantes();
			case (3):
				return datosMunis.getListaMunicipios().get(rowIndex).getProvincia();
			case (4):
				return datosMunis.getListaMunicipios().get(rowIndex).getAutonomia();
			default:
				return null;
			}
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return false;
			}
			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			switch (columnIndex) { //Todo esto se edita porq al mover, editar... la tabal se llama al renderer
			case (1):
				datosMunis.getListaMunicipios().get(rowIndex).setNombre((String) aValue);
				break;
			case (2):
				datosMunis.getListaMunicipios().get(rowIndex).setHabitantes((Integer) aValue);
				break;
			case (3):
				datosMunis.getListaMunicipios().get(rowIndex).setProvincia((String) aValue);
				break;
			case (4):
				datosMunis.getListaMunicipios().get(rowIndex).setAutonomia((String)aValue);
				break;
			}
			
		}

		ArrayList<TableModelListener> events = new ArrayList<TableModelListener>();
		@Override
		public void addTableModelListener(TableModelListener l) {
			System.out.println("AddTableModelListener");
			events.add(l);
			//System.out.println(events);
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			events.remove(l);
			
		}
		
		public void fireTableChanged(TableModelEvent e) {
			for (TableModelListener tbl: events) {
				tbl.tableChanged(e);
			}
		}
		
		public void borrarFila(int fila) {
			fireTableChanged(new TableModelEvent(modeloTabla, fila, datosMunis.getListaMunicipios().size()));
		}
		
		public void anyadirFila(int fila) {
			fireTableChanged(new TableModelEvent(modeloTabla, fila, datosMunis.getListaMunicipios().size()+1));
		}
		
	}

}
