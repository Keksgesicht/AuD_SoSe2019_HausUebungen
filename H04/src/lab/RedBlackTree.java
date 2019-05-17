package lab;

import frame.TreeNode;

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
	private TreeNode _nil;
	
	public RedBlackTree() {
		_nil = new TreeNode();
		_root = _nil;
	}
	
	public TreeNode root() {
		return this._root;
	}
	
	public TreeNode nil() {
		return this._nil;
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
	
	public TreeNode minimum() {
		return minimumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with minimum keyvalue
	 */
	public TreeNode minimumSubtree(TreeNode x) {
		return subtreeMinMax(x, false);
	}
	
	public TreeNode maximum() {
		return maximumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with maximum keyvalue
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
		while(n != null)
			n = max ? 
				x.right : 
				x.left;
		return n;
	}
	
	/**
	 * cleares the pointers to the child TreeNodes
	 * @param z the TreeNode which cildren get kidnapped
	 */
	private void clearChildren(TreeNode z) {
		z.left = z.right = _nil;
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
			if(x.key > newNode.key)
				x = x.left;
			else
				x = x.right;
		}
		newNode.p = px;
		if(px == _nil)
			_root = newNode;
		else {
			if(px.key > newNode.key)
				px.left = newNode;
			else
				px.right = newNode;
		}
		newNode.color = TreeNode.NodeColor.RED;
		fixColorsAfterInsertion(newNode);
	}
	
	private void fixColorsAfterInsertion(TreeNode newNode) {
		while(newNode.p.color == TreeNode.NodeColor.RED) {
			if(newNode.p == newNode.p.p.left) {
				TreeNode y = newNode.p.p.right;
				if(y.color == TreeNode.NodeColor.RED) {
					newNode.p.color = TreeNode.NodeColor.BLACK;
					y.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					newNode = newNode.p.p;
				} else {
					if(newNode == newNode.p.right) {
						newNode = newNode.p;
						rotateLeft(newNode);
					}
					newNode.p.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					rotateRight(newNode.p.p);
				}
			} else {
				TreeNode y = newNode.p.p.left;
				if(y.color == TreeNode.NodeColor.RED) {
					newNode.p.color = TreeNode.NodeColor.BLACK;
					y.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					newNode = newNode.p.p;
				} else {
					if(newNode == newNode.p.left) {
						newNode = newNode.p;
						rotateRight(newNode);
					}
					newNode.p.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					rotateLeft(newNode.p.p);
				}
			}
		} _root.color = TreeNode.NodeColor.BLACK;
	}

	private void rotateLeft(TreeNode x) {
		TreeNode y = x.right;
		x.right=y.left;
		if(y.left != _nil)
			y.left.p = x;
		y.p = x.p;
		if(x.p == _nil)
			_root = y;
		else
			if(x == x.p.left)
				x.p.left = y;
			else
				x.p.right = y;
		y.left = x;
		x.p = y;
	}
	
	private void rotateRight(TreeNode x) {
		TreeNode y = x.left;
		x.right=y.left;
		if(y.right != _nil)
			y.right.p = x;
		y.p = x.p;
		if(x.p == _nil)
			_root = y;
		else
			if(x == x.p.right)
				x.p.right = y;
			else
				x.p.left = y;
		y.right = x;
		x.p = y;
	}
	
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
	 * removes a TreeNode from this RedBlackTree
	 * @param toDelete the TreeNode that gets removed
	 */
	public void delete(TreeNode toDelete) {
		if(toDelete.left == _nil)
			transplant(toDelete, toDelete.right);
		else if(toDelete.right == _nil)
			transplant(toDelete, toDelete.left);
		else {
			TreeNode y = toDelete.right;
			
			while(y.left != _nil)
				y = y.left;
			
			if(y.p != toDelete) {
				transplant(y, y.right);
				y.right = toDelete.right;
				y.right.p = y;
			}
			transplant(toDelete, y);
			y.left = toDelete.left;
			y.left.p = y;
		}
	}
	
}
