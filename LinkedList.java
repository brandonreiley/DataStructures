import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked list program able to perform multiple linked list operations
 * @author Brandon Reiley
 * 
 * @param <E> generics data type
 */

public class LinkedList<E> implements ListI<E> {

	/**
	 * inner class declaring needed variables for the linked list
	 * @param <E> generics data type
	 */

	class Node<E> {
		E data;
		Node <E> next;

		public Node (E obj) {
			data = obj;
			next = null;
		}
	}

	/**
	 * declaring head pointer variable, tail pointer variable, and current size variable.
	 * @param head head pointer of linked list
	 * @param tail tail pointer of linked list
	 * @param currentSize current size of linked list
	 */

	private Node<E> head;
	private Node<E> tail;
	private int currentSize;

	/**
	 * Adds an object to the beginning of the list.
	 * @param obj the object to be added to the list
	 * @param newNode new node variable to be added to the beginning of the list
	 * @param head sets new node to head
	 */

	public void addFirst(E obj) {
		Node <E> newNode = new Node <E> (obj);
		newNode.next = head;
		head = newNode;
		currentSize++;
	}

	/**
	 * Adds an object to the end of the list. Iterates through list using temporary variable tmp until reaches end of list.
	 * @param obj the object to be added to the list
	 * @param newNode new node variable to be added to the beginning of the list
	 * @param tmp sets a temporary variable to head to iterate through list
	 */

	public void addLast(E obj) {
		Node<E> newNode = new Node <E> (obj);
		if (head == null) {
			head = newNode;
			currentSize++;
			return;
		}
		Node<E> tmp = head;

		while (tmp.next != null) {
			tmp = tmp.next;
		}
		tmp.next = newNode;
		currentSize++;
	}

	/**
	 * Removes the first Object in the list and returns it. Returns null if the list is empty.
	 * @param tmp sets a temporary variable to head and iterate through list
	 * @return the data of the first node in the list
	 */
	
	public E removeFirst() {
		if (head == null)
			return null;

		E tmp = head.data;

		if (head == tail)
			head = tail = null;
		else
			head = head.next;

		currentSize--;
		return tmp;
	}

	/**
	 * Removes the last Object in the list and returns it. Returns null if the list is empty.
	 * @param current sets a variable current to head and iterates until finds last node in list
	 * @param previous sets variable equal to current to find last node in list
	 * @return data of last node in the list
	 */

	public E removeLast() {
		if (head == null)
			return null;
		if (head == tail)
			return removeFirst();

		Node<E> current = head;
		Node<E> previous = null;

		while (current.next != null) {
			previous = current;
			current = current.next;
		}
		previous.next = null;
		tail = previous;
		currentSize--;
		return current.data;
	}

	/**
	 * Returns the first Object in the list, but does not remove it. Returns null if the list is empty.
	 * @return data of first node in the list
	 */

	public E peekFirst() {
		if (head == null)
			return null;

		return head.data;
	}

	/**
	 * Returns the last Object in the list, but does not remove it. Returns null if the list is empty.
	 * @param tmp sets temporary variable tmp to head and iterates through list to find last node in list
	 * @return data of last node in the list
	 */

	public E peekLast() {
		if (head == null)
			return null;

		Node<E> tmp = head;

		while (tmp.next != null)
			tmp = tmp.next;

		return tmp.data;
	}

	/**
	 * Returns the list to an empty state. This should generally be a constant time operation.
	 */

	public void makeEmpty() {
		head = tail = null;
		currentSize = 0;
	}

	/**
	 * Test whether the list is empty.
	 */

	public boolean isEmpty() {
		return (head == null);
	}

	/**
	 * Test whether the list is full. Always return false for this assignment.
	 */

	public boolean isFull() {
		return false;
	}

	/**
	 * Returns the number of Objects currently in the list.
	 * @return current number of objects in the list
	 */

	public int size() {
		return currentSize;
	}

	/**
	 * Test whether the list contains an object. This will use the object's compareTo method to determine whether two objects are the same.
	 * @param tmp sets temporary variable tmp to head and compares it to obj to see if they are the same
	 * @return true if tmp and obj are the same object and false once a non equal object's are found
	 */

	public boolean contains(E obj) {
		Node<E> tmp = head;

		while (tmp != null) {
			if (((Comparable<E>)tmp.data).compareTo(obj) == 0)
				return true;

			tmp = tmp.next;
		}
		return false;
	}

	/**
	 * Returns an Iterator of the values in the list, presented in the same order as the list.
	 * @return iterator helper method
	 */

	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	/**
	 * Iterator helper class that iterates through list to see if there is a next node
	 * @param index sets variable index to head and runs iterator helper method
	 */

	class IteratorHelper implements Iterator<E> {
		Node<E> index;
		public IteratorHelper() {
			index = head;
		}

		/**
		 * Returns true if the iteration has more elements.
		 * @return true if iteration has more elements, false if null
		 */

		public boolean hasNext() {
			return index != null;
		}

		/**
		 * Returns the next element in the iteration.
		 * @param tmp temporary variable set to iterate through list 
		 * @return next element in iteration
		 */

		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E tmp = index.data;
			index = index.next;
			return tmp;
		}
	}
}
