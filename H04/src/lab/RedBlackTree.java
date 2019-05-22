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
	private static TreeNode _nil; // mh.. When I try to access this. Do I get a NilPointerException?
	
	static {
		_nil = new TreeNode(); // nil doesn't need to exist multiple times
		_nil.left = _nil;
		_nil.right = _nil;	// nil is our sentinel
		_nil.p = _nil;
	}
	
	public RedBlackTree() {
		_root = _nil; // the root is at the beginning a useless node
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
		TreeNode s = _root;
		while(s != _nil) {
			if(s.key == key)
				break;
			s = key < s.key ? 
				s.left : 
				s.right; 
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
		TreeNode n = x;
		TreeNode m = _nil; 
		while(n != _nil) {
			m = n;
			n = max ?
				n.right :	// maximum
				n.left;		// minimum
		}
		return m;
	}
	
	/**
	 * cleares the pointers to the child TreeNodes
	 * @param z the TreeNode which cildren get kidnapped
	 */
	private void clearChildren(TreeNode z) {
		z.left = z.right = _nil;	// sets the children to our sentinel
	}

	/**
	 * inserts a new TreeNode to this RedBlackTree
	 * @param newNode the new TreeNode
	 */
	public void insert(TreeNode newNode) {
		clearChildren(newNode);
		TreeNode x = _root; 
		TreeNode px = _nil;
		
		while(x != _nil) {
			px = x;
			x = x.key > newNode.key ? 
				x.left :
				x.right;
		}
		newNode.p = px;
		if(px == _nil)			// if px didn't change the loop haven't run once
			_root = newNode;
		else {
			if(px.key > newNode.key) // hängt den neuen Knoten in der Sortierung passend als Kind an den Baum
				px.left = newNode;
			else
				px.right = newNode;
		}
		newNode.color = NodeColor.RED;
		fixColorsAfterInsertion(newNode);
	}
	
	/**
	 * lackiert den Baum neu an, sodass er nach dem Einfügen immer noch ein Rot-Schwarz-Baum ist 
	 * @param newNode der Problemknoten, welcher eingefügt wurde, mit dem das neu anstriechen beginnt
	 */
	private void fixColorsAfterInsertion(TreeNode newNode) {
		while(newNode.p.color == NodeColor.RED) {
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
		} _root.color = NodeColor.BLACK;
	}

	/**
	 * bewegt einen auf die Position seines linken Kindes 
	 * @param x der Knoten der bewegt wird
	 */
	private void rotateLeft(TreeNode x) {
		TreeNode y = x.right;	// markiert das andere Kind
		x.right = y.left;		// der linke Teilbaum des rechten Kindes ist nun das rechte Kind
		if(y.left != _nil)
			y.left.p = x;
		y.p = x.p;
		if(x.p == _nil)
			_root = y;
		else {
			if(x == x.p.left)
				x.p.left = y;
			else
				x.p.right = y;
		}
		y.left = x;
		x.p = y;
	}
	
	/**
	 * bewegt einen auf die Position seines rechten Kindes 
	 * @param x der Knoten der bewegt wird
	 */
	private void rotateRight(TreeNode y) {
		TreeNode x = y.left;	// markiert das andere Kind
		y.left = x.right;		// der rechte Teilbaum des linken Kindes ist nun das linke Kind
		if(x.right != _nil)
			x.right.p = y;
		x.p = y.p;
		if(y.p == _nil)
			_root = x;
		else {
			if(y == y.p.right)
				y.p.right = x;
			else
				y.p.left = x;
		}
		x.right = y;
		y.p = x;
	}
	
	/**
	 * Setzt Knoten v an die Position von Knoten u
	 * @param u Knoten u
	 * @param v Knoten v
	 */
	private void transplant(TreeNode u, TreeNode v) {
		if(u.p == _nil)
			_root = v;
		else {
			if(u == u.p.left)
				u.p.left = v;
			else
				u.p.right = v;
		}
		if(v != _nil)
			v.p = u.p;
	}
	
	/**
	 * @see https://algorithmtutor.com/Data-Structures/Tree/Red-Black-Trees/#Deletion
	 * removes a TreeNode from this RedBlackTree
	 * @param toDelete the TreeNode that gets removed
	 */
	public void delete(TreeNode toDelete) {
		TreeNode.NodeColor dColor = toDelete.color;
		TreeNode x, y;
		
		if(toDelete.left == _nil) {
			x = toDelete.right;	
			transplant(toDelete, toDelete.right);
		} else if(toDelete.right == _nil) {
			x = toDelete.left;
			transplant(toDelete, toDelete.left);
		} else {
			y = minimumSubtree(toDelete.right);
			dColor = y.color;
			
			x = y.right;
			if (y.p == toDelete)
				x.p = y;
			else {
				transplant(y, y.right);
				y.right = toDelete.right;
				y.right.p = y;
			}
			transplant(toDelete, y);
			y.left = toDelete.left;
			y.left.p = y;
			y.color = toDelete.color;
		}
		if(dColor == NodeColor.BLACK)
			fixUpAfterDelete(x);
	}
	
	private void fixUpAfterDelete(TreeNode x) {
		TreeNode xSibling;
		while(x != _root && x.color == NodeColor.BLACK) {
			boolean sPos = (x == x.p.left);
			xSibling = sPos ?
					   x.p.right :
					   x.p.left;
			
			if(xSibling.color == NodeColor.RED) { // Fall 1
				xSibling.color = NodeColor.BLACK;
				x.p.color = NodeColor.RED;
				if(sPos) {
					rotateLeft(x.p);
					xSibling = x.p.right;
				} else {
					rotateRight(x.p);
					xSibling = x.p.left;
				}
			}
			if(xSibling.right.color == NodeColor.BLACK) { // Fall 3.2
				xSibling.color = NodeColor.RED;
				x = x.p;
			} else {
				if(sPos) {
					if(xSibling.right.color == NodeColor.BLACK) { // Fall 3.3
						xSibling.left.color = NodeColor.BLACK;
						xSibling.color = NodeColor.RED;
						rotateRight(xSibling);
						xSibling = x.p.right;
					}	// Fall 3.4
					xSibling.color = x.p.color;
					x.p.color = NodeColor.BLACK;
					xSibling.right.color = NodeColor.BLACK;
					rotateLeft(x.p);
					x = _root;
				} else {
					if(xSibling.left.color == NodeColor.BLACK) { // Fall 3.3
						xSibling.right.color = NodeColor.BLACK;
						xSibling.color = NodeColor.RED;
						rotateLeft(xSibling);
						xSibling = x.p.left;
					}	// Fall 3.4
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
