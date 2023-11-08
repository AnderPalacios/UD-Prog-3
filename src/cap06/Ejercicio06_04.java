package cap06;

import java.awt.BorderLayout;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class Ejercicio06_04 extends JFrame{
	
	public static void main(String[] args) {
		
		Ejercicio06_04 ventTree = new Ejercicio06_04();
		ventTree.inicializar();
	}
	
	private MiTree tree;
	private DefaultTreeModel modeloTree;
	private DefaultMutableTreeNode raiz;
	
	public Ejercicio06_04() {
		setSize(480, 640);
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		tree = new MiTree();
		
		JPanel pnlBotonera = new JPanel();
		JButton btnAnyadirHijo = new JButton("Añadir Hijo");
		JButton btnAnyadirHermano = new JButton("Añadir Hermano");
		JButton btnBorrarNodo = new JButton("Borrar nodo");
		pnlBotonera.add(btnAnyadirHijo);
		pnlBotonera.add(btnAnyadirHermano);
		pnlBotonera.add(btnBorrarNodo);
		
		getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
		getContentPane().add(pnlBotonera, BorderLayout.SOUTH);
		
	}
	
	public void inicializar( ) {
		this.setVisible( true );
		raiz = new DefaultMutableTreeNode("Raíz");
		modeloTree = new DefaultTreeModel(raiz);
		tree.setModel(modeloTree);
		crearNodo("Hijo1", raiz, 0);
		DefaultMutableTreeNode nodo2 = crearNodo("Hijo2", raiz, 0);
		//crearNodo(new JProgressBar(), raiz, 0);
		crearNodo(Integer.valueOf(4), raiz, 0);
		crearNodo("Nieto1", nodo2, 0);
	}
	
	public DefaultMutableTreeNode crearNodo(Object dato, MutableTreeNode nodoPadre, int posicion) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(dato);
		modeloTree.insertNodeInto(nodo, nodoPadre, posicion);
		tree.expandir(new TreePath(nodo.getPath()), true);
		return nodo;
		
	}
	
	public class MiTree extends JTree {
		public void expandir(TreePath tp, boolean estado) {
			setExpandedState(tp, estado);
		}
	}

}
