import java.util.Iterator;

/**
 * @author Brandon Reiley
 * Program for a self balancing binary search tree, or AVL Tree
 */
public class AVLTree<K,V> implements AVLTreeI<K, V> {

	/**
	 * setting up inner class
	 * @param Nodes leftchild, rightchild, parent
	 */

	class Node <K,V> {
		K key;
		V value;
		Node<K,V> leftChild, rightChild, parent;

		public Node (K key, V value) {
			this.key = key;
			this.value = value;
			leftChild = rightChild = parent = null;
		}
	}

	private Node <K,V> root;
	private int currentSize;

	/**
	 * resets root and current size
	 */

	public AVLTree() {
		Node<K,V> root = null;
		int currentSize = 0; 
	}

	/**
	 * adds new nodes to root of empty, if not calls add method to add root and node
	 * @param key object being added to tree
	 * @param value the value associated to the key
	 */

	public void add(K key, V value) {
		Node <K,V> node = new Node <K,V> (key, value);
		if (root == null) {
			root = node;
			currentSize++;
			return;
		}
		add (root, node);
	}

	/**
	 * searches through tree to find where to add new node
	 * @param parent parent of the node being added
	 * @param newNode the new node being added
	 */

	private void add (Node <K,V> parent, Node <K,V> newNode) {
		if (((Comparable<K>)newNode.key).compareTo(parent.key) > 0) {
			if (parent.rightChild == null) {
				parent.rightChild = newNode;
				newNode.parent = parent;
				currentSize++;
			}
			else {
				add (parent.rightChild, newNode);
			}
		}
		else {
			if (parent.leftChild == null) {
				parent.leftChild = newNode;
				newNode.parent = parent;
				currentSize++;
			}
			else {
				add (parent.leftChild, newNode);
			}
		}
		rotate(newNode);
	}

	/**
	 * Tests whether the AVLTree contains the key
	 * @param key the key to look for
	 * @return whether the key is found
	 */

	public boolean contains(K key) {
		return getValue(key) != null;
	}
	
	/**
	 * Get the value associated with a given key
	 * @param key the key to get the value for
	 * @return the current value
	 */
	
	public V getValue(K key) {
		return find (key, root);
	}
	
	private V find (K key, Node <K,V> n) {
		if (n == null) 
			return null;
		if (((Comparable<K>)key).compareTo(n.key) < 0)
			return find (key, n.leftChild);
		if (((Comparable<K>)key).compareTo(n.key) > 0)
			return find (key, n.rightChild);
		return (V) n.value;
			
	}

	/**
	 * @return current size of tree
	 */

	public int size() {
		return currentSize;
	}

	/**
	 * @return true if tree is empty, false if not
	 */

	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * @return height of tree by calling height method
	 */

	public int height() {
		if (root == null)
			return 0;
		return height (root) - 1;
	}

	/**
	 * if left child and right child are null, no height, else checks both sides of tree and adds one
	 * to find correct height of tree
	 * @param node root node that we use to search tree
	 * @return height of tree
	 */

	private int height (Node<K,V> node) {
		if (node == null) //we are leaf node
			return 0;
		
		int leftheight = height (node.leftChild) + 1;
		int rightheight = height (node.rightChild) + 1;
		
		if (leftheight > rightheight)
			return leftheight;
		return rightheight;
	}
	
	/**
	 * checks to see what kind of rotation is needed
	 * @param node node used search through tree
	 */
	private void rotate (Node<K,V> node) {
		if (node.parent.leftChild != null) {
			if (node.parent.parent.leftChild != null) {
				rightRotate (node.parent.parent);
				return;
			}
			if (node.parent.parent.rightChild != null) {
				rightLeftRotate (node.parent.parent);
				return;
			}
		}
		if (node.parent.rightChild != null) {
			if (node.parent.parent.rightChild != null) {
				leftRotate (node.parent.parent);
				return;
			}
			if (node.parent.parent.leftChild != null) {
				leftRightRotate (node.parent.parent);
				return;
			}
		}

	}
	
	/**
	 * performs a left rotation on the AVL Tree
	 * @param node node used to begin the rotation
	 */
	
	private void leftRotate (Node <K,V> node) {
		Node <K,V> tmp = node.rightChild;
		node.rightChild = tmp.leftChild;
		
		if (node.rightChild != null) {
			node.rightChild.parent = node;
		}
		if (node.parent == null) { //we are root node
			root = tmp;
			tmp.parent = null;
		}
		else {
			tmp.parent = node.parent;
			if (node == node.parent.leftChild) 
				tmp = tmp.parent.leftChild;
			else {
				tmp.parent.rightChild = tmp;
			}
			tmp.leftChild = node;
			node.parent = tmp;
		}
	}
	
	/**
	 * performs a right rotation on the AVL Tree
	 * @param node node used to begin the rotation
	 */
	
	private void rightRotate (Node <K,V> node) {
		Node <K,V> tmp = node.leftChild;
		node.leftChild = tmp.rightChild;
		
		if (node.leftChild != null) {
			node.leftChild.parent = node;
		}
		if (node.parent == null) { //we are root node
			root = tmp;
			tmp.parent = null;
		}
		else {
			tmp.parent = node.parent;
			if (node == node.parent.rightChild) 
				tmp = tmp.parent.rightChild;
			else {
				tmp.parent.leftChild = tmp;
			}
			tmp.rightChild = node;
			node.parent = tmp;
		}
	}
	
	/**
	 * performs a left right rotation on the AVL Tree
	 * @param node node used to begin the rotation
	 */
	
	private void leftRightRotate (Node <K,V> node) {
		leftRotate(node.leftChild);
		rightRotate(node);
	}
	
	/**
	 * performs a right left rotation on the AVL Tree
	 * @param node node used to begin the rotation
	 */
	
	private void rightLeftRotate (Node <K,V> node) {
		rightRotate(node.rightChild);
		leftRotate(node);
	}
	
	/**
	 * Iterates through the AVL Tree
	 * @return array array of keys in avl tree
	 */
	
	public Iterator<K> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper <T> implements Iterator <T> {
		T[] array;
		int position, i;
		
		public IteratorHelper() {
			array = (T[]) new Object [currentSize];
			int position = i = 0;
			inOrder(root);
		}
		
		/**
		 * adds nodes to array in inorder traversal
		 * @param current root node that starts the inorder adding ot array
		 */
		
		private void inOrder (Node <K,V> current) {
			if (current != null) {
				inOrder (current.leftChild);
				array[i++] = (T) current.key;
				inOrder(current.rightChild);
			}
		}
		
		/**
		 * @return true if there is a next node, false if not
		 */
		
		public boolean hasNext() {
			return position < array.length;
		}
		
		/**
		 * returns the next node in the array, null if at end
		 */
		
		public T next() {
			if (!hasNext()) 
				return null;
			return array[position++];
		}
		
	}
	
	/**
	 * calls print method and sends root as parameter
	 */
	
	public void print() {
		print (root);
	}
	
	/**
	 * Recursively prints out the AVL Tree in in order
	 * @param n root node
	 */
	
	private void print (Node <K,V> n) {
		if (n == null)
			return;
		if (n.leftChild != null) 
			print (n.leftChild);
		if (n == root) {
			System.out.println("Key: " + n.key + " Value: " + n.value + " (root)");
		}
		else {
			for(int i = 0; i < height(); i++)
				System.out.print(".");
		System.out.println("Key: " + n.key + " Value: " + n.value);
		}
		if (n.rightChild != null)
			print(n.rightChild);
	}
}
