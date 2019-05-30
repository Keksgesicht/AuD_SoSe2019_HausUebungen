package lab;

import frame.TreeNode;
import frame.TreeNode.NodeColor;

/**
 * Aufgabe H1a)
 * An implementation of a Black-Red-Tree
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */
public class RedBlackTree {
	
	private TreeNode _root;
	private TreeNode _nil; // mh.. Can I get a NilPointerException?
	
	public RedBlackTree() {
		_nil = new TreeNode();
		_root = _nil; // where is no root in an empty tree
	}
	
	/**
	 * @return the root node of this tree
	 */
	public TreeNode root() {
		return this._root;
	}
	
	/**
	 * @return the sentinel of this tree
	 */
	public TreeNode nil() {
		return _nil;
	}
	
	/**
	 * @param key the key of the TreeNode which should be found
	 * @return the nil-Node when there is no Node with that key <br>
	 * 		   or a TreeNode with that key
	 */
	public TreeNode search(int key) {
		TreeNode s = _root;		// die Suche startet mit der Wurzel
		while(s != _nil) {
			if(s.key == key)	// Falls Element bereits den gesuchten wert hat,
				break;			// ist die Suche hiermit beendet.
			s = key < s.key ? 	// Je nachdem, ob der gesuchte Wert
				s.left : 		// kleiner oder
				s.right; 		// größer ist.
		} return s;
	}
	
	/**
	 * @return the TreeNode with minimum keyvalue in this subtree
	 */
	public TreeNode minimum() {
		return minimumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with minimum keyvalue in this subtree
	 */
	public TreeNode minimumSubtree(TreeNode x) {
		return subtreeMinMax(x, false);
	}
	
	/**
	 * @return the TreeNode with maximum keyvalue in this subtree
	 */
	public TreeNode maximum() {
		return maximumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with maximum keyvalue in this subtree
	 */
	public TreeNode maximumSubtree(TreeNode x) {
		return subtreeMinMax(x, true);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @param max whether it should search the maximum (true) or minimum (false)
	 * @return the TreeNode with maximum or minimum keyvalue
	 */
	private TreeNode subtreeMinMax(TreeNode x, boolean max) {
		TreeNode n = x;		// die suche beginnt an der Wurzel des gewählten Unterbaums
		TreeNode m = _nil;	// Wenn x=nil dann m auch nil  
		while(n != _nil) {
			m = n;			// ansonsten m gleich "Eltern"-Element von n, wenn n=nil wird
			n = max ?
				n.right :	// maximum: rechstes Element 
				n.left;		// minimum: linkstes Element
		}
		return m;
	}
	
	/**
	 * cleares the pointers to the child TreeNodes
	 * @param z the TreeNode which cildren get kidnapped
	 */
	private void clearChildren(TreeNode z) {
		z.left = z.right = _nil;	// the children gets neutralized
	}

	/**
	 * inserts a new TreeNode to this RedBlackTree
	 * @param newNode the new TreeNode
	 */
	public void insert(TreeNode newNode) {
		clearChildren(newNode);
		TreeNode x = _root; 
		TreeNode px = _nil;
		
		while(x != _nil) {	// finds the position to insert
			px = x;
			x = x.key > newNode.key ? 
				x.left :
				x.right;
		}
		newNode.p = px;
		if(px == _nil)			// when px didn't change we are still at the root
			_root = newNode;
		else {
			if(px.key > newNode.key) // hängt den neuen Knoten in der Sortierung passend als Kind an den Baum
				px.left = newNode;
			else
				px.right = newNode;
		}
		newNode.color = NodeColor.RED; // new TreeNodes are RED befor fixing
		fixColorsAfterInsertion(newNode); // fix
	}
	
	/**
	 * lackiert den Baum neu an, sodass er nach dem Einfügen immer noch ein Rot-Schwarz-Baum ist 
	 * @param newNode der Problemknoten, welcher eingefügt wurde, mit dem das neu anstriechen beginnt
	 */
	private void fixColorsAfterInsertion(TreeNode newNode) {
		while(newNode.p.color == NodeColor.RED) { // Regel 3: Rot-Rot-Regel
			if(newNode.p == newNode.p.p.left) {
				TreeNode y = newNode.p.p.right;
				if(y.color == NodeColor.RED) {
					newNode.p.color = NodeColor.BLACK;
					y.color = NodeColor.BLACK;
					newNode.p.p.color = NodeColor.RED;
					newNode = newNode.p.p;
				} else {
					if(newNode == newNode.p.right) {
						newNode = newNode.p;
						rotateLeft(newNode);
					}
					newNode.p.color = NodeColor.BLACK;
					newNode.p.p.color = NodeColor.RED;
					rotateRight(newNode.p.p);
				}
			} else {
				TreeNode y = newNode.p.p.left;
				if(y.color == NodeColor.RED) {
					newNode.p.color = NodeColor.BLACK;
					y.color = NodeColor.BLACK;
					newNode.p.p.color = NodeColor.RED;
					newNode = newNode.p.p;
				} else {
					if(newNode == newNode.p.left) {
						newNode = newNode.p;
						rotateRight(newNode);
					}
					newNode.p.color = NodeColor.BLACK;
					newNode.p.p.color = NodeColor.RED;
					rotateLeft(newNode.p.p);
				}
			}
		} _root.color = NodeColor.BLACK; // Regel 2: Wurzel ist schwarz
	}

	/**
	 * bewegt einen auf die Position seines linken Kindes 
	 * @param x der Knoten der bewegt wird
	 */
	private void rotateLeft(TreeNode x) {
		TreeNode y = x.right;	// markiert das rechte Kind
		x.right = y.left;		// der linke Teilbaum des rechten Kindes ist nun das rechte Kind von x
		if(y.left != _nil)
			y.left.p = x;		// somit hat der linke Teilbaum des rechten Kindes als Elter nun auch das Element x
		y.p = x.p;				// y an das Elter von x hängen
		if(x.p == _nil)			// Wenn das Elter nil ist,
			_root = y;			// ist man bereits an der Wurzel
		else {
			if(x == x.p.left)	// y an das Elter von x hängen
				x.p.left = y;
			else
				x.p.right = y;
		}
		y.left = x;				// x ist nun linke Kind von y
		x.p = y;				// somit ist y auch das Elter von x
	}
	
	/**
	 * bewegt einen auf die Position seines rechten Kindes 
	 * @param y der Knoten der bewegt wird
	 */
	private void rotateRight(TreeNode y) {
		TreeNode x = y.left;	// markiert das linke Kind
		y.left = x.right;		// der rechte Teilbaum des linken Kindes ist nun das rechte Kind von y
		if(x.right != _nil)
			x.right.p = y;		// somit hat der rechte Teilbaum des linken Kindes als Elter nun auch das Element x
		x.p = y.p;				// y an das Elter von x hängen
		if(y.p == _nil)			// Wenn das Elter nil ist,
			_root = x;			// ist man bereits an der Wurzel
		else {
			if(y == y.p.right)	// y an das Elter von x hängen
				y.p.right = x;
			else
				y.p.left = x;
		}
		x.right = y;			// x ist nun linke Kind von y
		y.p = x;				// somit ist y auch das Elter von x
	}
	
	/**
	 * Setzt Knoten v an die Position von Knoten u
	 * @param u Knoten u
	 * @param v Knoten v
	 */
	private void transplant(TreeNode u, TreeNode v) {
		if(u.p == _nil)			// Wenn das Elter nil ist,
			_root = v;			// ist man bereits an der Wurzel
		else {
			if(u == u.p.left)	// v an das Elter von u hängen
				u.p.left = v;
			else
				u.p.right = v;
		}
		//if(v != _nil)
			v.p = u.p;			// v an das Elter von u hängen
	}
	
	/**
	 * @see https://algorithmtutor.com/Data-Structures/Tree/Red-Black-Trees/#Deletion
	 * removes a TreeNode from this RedBlackTree
	 * @param toDelete the TreeNode that gets removed
	 */
	public void delete(TreeNode toDelete) {
		NodeColor dColor = toDelete.color;
		TreeNode x, y;
		
		if(toDelete.left == _nil) { // Wenn das zulöschende Element mindestens ein Halbblatt ist,
			x = toDelete.right;	
			transplant(toDelete, toDelete.right); // ersätze es durch seinen Unterbaum
		} else if(toDelete.right == _nil) {
			x = toDelete.left;
			transplant(toDelete, toDelete.left);
		} else {
			y = minimumSubtree(toDelete.right); // Finde nach dem Wert nachfolgende Element
			dColor = y.color;
			
			x = y.right;
			if (y.p == toDelete)
				x.p = y;
			else { 							// Falls kein Nachfolger
				transplant(y, y.right);		// das Halbblatt y durch seinen Unterbaum ersetzen
				y.right = toDelete.right;	// und das zu löschende Element durch y ersetzen
				y.right.p = y;
			}
			transplant(toDelete, y);		// und das zu löschende Element durch y ersetzen
			y.left = toDelete.left;
			y.left.p = y;
			y.color = toDelete.color;
		}
		if(dColor == NodeColor.BLACK)	// Fall ein rotes aus dem Baum genommen wurde, verletzt dies Regel 4 nicht
			fixUpAfterDelete(x);		// Sobald es schwarz war, wird ein fix benötigt
	}
	
	private void fixUpAfterDelete(TreeNode x) {
		TreeNode xSibling; // Das Kind vom Elter von x, welches nicht x ist.
		while(x != _root && x.color == NodeColor.BLACK) {
			if(x == x.p.left) {
				xSibling = x.p.right; // Fall 1: Geschwisterkind ist RED
				if(xSibling.color == NodeColor.RED) {
					xSibling.color = NodeColor.BLACK;
					x.p.color = NodeColor.RED;
					rotateLeft(x.p);
					xSibling = x.p.right;
				} // Fall 2: Geschwisterkind and seine Kinder sind BLACK
				if(xSibling.left.color == NodeColor.BLACK && xSibling.right.color == NodeColor.BLACK) {
					xSibling.color = NodeColor.RED;
					x = x.p;
				} else { // Fall 3: Geschwisterkind's rechtes Kind ist BLACK und sein linkes ist RED
					if(xSibling.right.color == NodeColor.BLACK) {
							xSibling.left.color = NodeColor.BLACK;
							xSibling.color = NodeColor.RED;
							rotateRight(xSibling);
							xSibling = x.p.right;
					} // Fall 4: eines der Kinder des Geschwisterkinds ist RED
					xSibling.color = x.p.color;
					x.p.color = NodeColor.BLACK;
					xSibling.right.color = NodeColor.BLACK;
					rotateLeft(x.p);
					x = _root;
				}
			} else {
				xSibling = x.p.left; // Fall 1: Geschwisterkind ist RED
				if(xSibling.color == NodeColor.RED) {
					xSibling.color = NodeColor.BLACK;
					x.p.color = NodeColor.RED;
					rotateRight(x.p);
					xSibling = x.p.left;
				} // Fall 2: Geschwisterkind and seine Kinder sind BLACK
				if(xSibling.left.color == NodeColor.BLACK && xSibling.right.color == NodeColor.BLACK) {
					xSibling.color = NodeColor.RED;
					x = x.p;
				} else { // Fall 3: Geschwisterkind's linkes Kind ist BLACK und sein rechtes ist RED
					if(xSibling.left.color == NodeColor.BLACK) {
						xSibling.right.color = NodeColor.BLACK;
						xSibling.color = NodeColor.RED;
						rotateLeft(xSibling);
						xSibling = x.p.left;
					} // Fall 4: eines der Kinder des Geschwisterkinds ist RED
					xSibling.color = x.p.color;
					x.p.color = NodeColor.BLACK;
					xSibling.left.color = NodeColor.BLACK;
					rotateRight(x.p);
					x = _root;
				}
			}
		} x.color = NodeColor.BLACK;
	}
	
}
