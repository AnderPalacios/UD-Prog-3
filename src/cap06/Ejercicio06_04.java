package cap06;

import java.awt.BorderLayout;import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Ejercicio06_04 extends JFrame{
	
	public static void main(String[] args) {
		
		Ejercicio06_04 ventTree = new Ejercicio06_04();
		ventTree.inicializar();
	}
	
	private MiTree tree;
	private DefaultTreeModel modeloTree;
	private DefaultMutableTreeNode raiz;
	
	private JTable tabla;
	private ModeloTabla modeloTabla;
	private ArrayList<Object> array = new ArrayList<Object>();
	
	
	public Ejercicio06_04() {
		setSize(640, 480);
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		tabla = new JTable();
		JScrollPane pnlS = new JScrollPane(tabla);
		
		tree = new MiTree();
		JScrollPane spPrincipal = new JScrollPane(tree);
		
		JPanel pnlBotonera = new JPanel();
		JButton btnAnyadirHijo = new JButton("Añadir Hijo");
		JButton btnAnyadirHermano = new JButton("Añadir Hermano");
		JButton btnBorrarNodo = new JButton("Borrar nodo");
		pnlBotonera.add(btnAnyadirHijo);
		pnlBotonera.add(btnAnyadirHermano);
		pnlBotonera.add(btnBorrarNodo);
		
		getContentPane().add(pnlS, BorderLayout.CENTER);
		getContentPane().add(spPrincipal, BorderLayout.WEST);
		getContentPane().add(pnlBotonera, BorderLayout.SOUTH);
		
		//Paso 3:
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			
			private JProgressBar ph = new JProgressBar(0,100);

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
					boolean leaf, int row, boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				DefaultMutableTreeNode nodo =  (DefaultMutableTreeNode) value;
				if (nodo.getUserObject() instanceof Integer) {
					ph.setValue((Integer) nodo.getUserObject());
					return ph;
				}
				return c;
			}
		});
		
		//Paso 4:
		tree.setEditable(true );
		tree.setCellEditor(new DefaultTreeCellEditor(tree, (DefaultTreeCellRenderer) tree.getCellRenderer()) {

			private boolean esEntero = false;
			private Integer valorAnterior;
			
			@Override
			public boolean isCellEditable(EventObject event) {
				if (event instanceof MouseEvent) {
					if (((MouseEvent) event).getClickCount() > 1) {
						return true;
					}
				}
				return false;
			}

			
			
			@Override
			public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
					boolean leaf, int row) {
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
				if (nodo.getUserObject() instanceof Integer) {
					esEntero = true;
				}
				if (esEntero) {
					valorAnterior = (Integer) nodo.getUserObject();
				}
				return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
			}

			@Override
			public Object getCellEditorValue() {
				
				if (esEntero) {
					try {
						return Integer.parseInt(super.getCellEditorValue().toString());
					} catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(Ejercicio06_04.this, "Error");
					}
				}
				
				return super.getCellEditorValue();
			}
			
		});
		
		
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 1) {
//					TreePath path =  tree.getPathForLocation(e.getX(), e.getY());
//					if (path != null) {
//						DefaultMutableTreeNode nodo =  (DefaultMutableTreeNode) path.getLastPathComponent(); //En este es en el que se ha hecho el click
//						System.out.println(nodo.getUserObject() + "y su clase: " + nodo.getUserObject().getClass());
//					}
//				}
				
				//La parte de arriba es la que es parte del ejercico, esto lo queria añadir yo.
				if (e.getClickCount() >= 1) {
					TreePath tp = tree.getClosestPathForLocation(e.getX(), e.getY());
					DefaultMutableTreeNode nodoSel = (DefaultMutableTreeNode) tp.getLastPathComponent();
					Object dato = nodoSel.getUserObject();
					array.add(dato);
					System.out.println(dato);
					modeloTabla = new ModeloTabla();
					tabla.setModel(modeloTabla);
				}
			}
			
		});
		
		btnAnyadirHijo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath tp = tree.getSelectionPath();
				if (tp != null) {
					crearNodo("Nuevo",  (DefaultMutableTreeNode) tp.getLastPathComponent(), 0);
				}
				
			}
		});
		
		btnBorrarNodo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath tp = tree.getSelectionPath();
				if (tp != null) {
					borrarNodo((DefaultMutableTreeNode) tp.getLastPathComponent());
				}
				
			}
		});
		
	}
	
	public void inicializar( ) {
		this.setVisible( true );
		raiz = new DefaultMutableTreeNode("Raíz");
		modeloTree = new DefaultTreeModel(raiz);
		tree.setModel(modeloTree);
		crearNodo("Hijo1", raiz, 0);
		DefaultMutableTreeNode nodo2 = crearNodo("Hijo2", raiz, 1);
		crearNodo(new Point(10,20), raiz, 2);
		crearNodo(Integer.valueOf(4), raiz, 3);
		//crearNodo(new JProgressBar(), raiz, 0);
		//crearNodo(Integer.valueOf(4), raiz, 0);
		crearNodo("Nieto 2.1", nodo2, 0);
		//modeloTree.insertNodeInto(new DefaultMutableTreeNode("Ander"), raiz, 0);
	}
	
	public DefaultMutableTreeNode crearNodo(Object dato, MutableTreeNode nodoPadre, int posicion) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(dato);
		modeloTree.insertNodeInto(nodo, nodoPadre, posicion);
		tree.expandir(new TreePath(nodo.getPath()), true);
		return nodo;
		
	}
	
	public void borrarNodo(DefaultMutableTreeNode nodoAborrar) {
		DefaultMutableTreeNode nodoPadre = (DefaultMutableTreeNode) nodoAborrar.getParent();
		if (nodoPadre != null) {
			modeloTree.removeNodeFromParent(nodoAborrar);
		}
	}
	
	public static class MiTree extends JTree {
		public void expandir(TreePath tp, boolean estado) {
			setExpandedState(tp, estado);
		}
	}
	
	private class ModeloTabla implements TableModel {

		@Override
		public int getRowCount() {
			return array.size();
		}

		@Override
		public int getColumnCount() {
			return 1;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return "Objecto/dato";
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			for (int i = 0; i < array.size(); i++) {
				return array.get(rowIndex);
			}
			return null;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
