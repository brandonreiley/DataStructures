import java.util.Iterator;

/**
 * The Hash data structure has O(1) time complexity (best case) for add, remove, and find
 * for an object in the data structure. The methods in the Hash data structure are defined
 * by the HashI interface. The Hash consists of an array of Linked Lists,
 * the Linked Lists are defined by the HashListI interface.
 * 
 * @author Brandon Reiley
 *
 *
 * @param <K> The key for entries in the hash
 * @param <V> The value for entries in the hash
 */

public class Hash<K, V> implements HashI<K, V> {

	/**
	 * @param <K> key object
	 * @param <V> value object
	 * inner class that initializes the main objects we will use, K and V
	 */

	class HashElement<K, V> implements Comparable<HashElement<K, V>> {
		K key;
		V value;

		public HashElement(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(HashElement<K, V> o) {
			return (((Comparable<K>)o.key).compareTo(this.key));
		}
	}

	/**
	 * @param tableSize size of current table
	 * @param numElements current number of elements in the hash
	 * @param maxLoadFactor max size hash can be before automatically increasing
	 * initializing more important variables
	 */

	LinkedList <HashElement<K, V>>[] harray;
	int tableSize;
	int numElements;
	double maxLoadFactor;

	/**
	 * creates an array size of tableSize and adds new objects until tableSize is met
	 * @param tableSize current size of table
	 */

	public Hash(int tableSize) {
		this.tableSize = tableSize;
		harray = (LinkedList<HashElement<K, V>>[]) new LinkedList[tableSize];

		for (int i = 0; i < tableSize; i++)
			harray [i] = new LinkedList<HashElement<K, V>>();

		maxLoadFactor = 0.75;
		numElements = 0;
	}

	/**
	 * if the load has become bigger than the max, new table size is twice the size of the old one
	 * then calls resize method to increase table size to the new size
	 * After, it creates a new hash element, newHE and initializes harray to add newHE to itself
	 * numElements is increased
	 * @param newHE new hash element that gets added to harray
	 * @param key the key to add
	 * @param value the value associated with the key
	 */

	public boolean add(K key, V value) {
		if (loadFactor() > maxLoadFactor) {
			int newSize = tableSize * 2;
			resize(newSize);
		}
		HashElement<K, V> newhe = new HashElement<K, V> (key, value);
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		harray[hashval].add(newhe);
		numElements++;
		return true;
	}

	/**
	 * removes a hash element from harray
	 * decreases numElements
	 * @param key the key to remove
	 */

	public boolean remove(K key) {
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		harray[hashval].remove((Hash<K, V>.HashElement<K, V>)key);
		numElements--;
		return true;
	}

	/**
	 * Checks if the key changed, then finds the correct value for that key
	 * @return true if changed, false if not
	 * @param key the key to change
	 * @param value the new value to assign to the key
	 */

	public boolean changeValue(K key, V value) {
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;

		for (HashElement <K, V> he : harray[hashval]) {
			if (((Comparable<K>)he.key).compareTo(key) != 0) {
				key = he.key;
				value = getValue(key);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test whether the hash has the entry associated with the key.
	 * @return true of entry is correct, false if not
	 * @param key the key to look for
	 */
	
	public boolean contains(K key) {
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		
		for (HashElement <K, V> he : harray[hashval]) 
			if (((Comparable<K>)he.key).compareTo(key) == 0)
				return true;
		return false;
	}

	/**
	 * gets the desired he value if he.key and key are not equal
	 * @param key the key to find the value for
	 * @return the desired hash element in 'he'
	 */

	public V getValue(K key) {
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;

		for (HashElement <K, V> he : harray[hashval]) 
			if (((Comparable<K>)he.key).compareTo(key) == 0)
				return he.value;
		return null;
	}

	/**
	 * @return the number of key/value pairs currently stored in the dictionary
	 */

	public int size() {
		return numElements;
	}

	/**
	 * checks if first element in array is null
	 * if null, return true. if full, return false
	 * @return true or false depending on if harray has elements
	 */

	public boolean isEmpty() {
		return (harray[0] == null);
	}

	/**
	 * makes harray null
	 * sets numElemets to 0
	 */

	public void makeEmpty() {
		harray = null;
		numElements = 0;

	}

	/**
	 * Returns the current load factor of the dictionary (lambda)
	 * @return load factor
	 */

	public double loadFactor() {
		return numElements / tableSize;
	}

	/**
	 * gets the max load factor (at which point we need to resize)
	 * @return maxLoadFactor
	 */

	public double getMaxLoadFactor() {
		return maxLoadFactor;
	}

	/**
	 * Set the max load factor that we will need to resize
	 */

	public void setMaxLoadFActor(double loadfactor) {
		maxLoadFactor = loadfactor;
	}

	/**
	 * resizes the list with a temporary array
	 * @param harray assigned to tmparray, becomes what tmparray was
	 * @param newSize the size of the new dictionary
	 */

	public void resize(int newSize) {
		LinkedList<HashElement<K, V>> [] tmparray = (LinkedList<HashElement<K, V>>[]) new LinkedList[newSize];

		for (int i = 0; i < newSize; i++)
			tmparray[i] = new LinkedList <HashElement<K, V>>();
		for (K key : this) {
			V val = getValue(key);
			HashElement<K, V> newhe = new HashElement<K, V>(key, val);
			int hashval = key.hashCode();
			hashval = hashval & 0x7FFFFFFF;
			hashval = hashval % newSize;
			tmparray[hashval].add(newhe);
		}
		harray = tmparray;
		tableSize = newSize;
	}

	/**
	 * Returns an Iterator of the values in the list, presented in the same order as the list.
	 * @return iterator helper method
	 */

	public Iterator<K> iterator() {
		return new IteratorHelper();
	}

	/**
	 * declares Keys array and adds each element of harray to Keys
	 * @param Keys array called Keys
	 * @param position position in array
	 */

	class IteratorHelper <T> implements Iterator<T> {
		T[] Keys;
		int position;

		public IteratorHelper() {
			Keys = (T[]) new Object[numElements];
			int counter = 0;
			for (int i = 0; i < tableSize; i++) {
				LinkedList <HashElement<K, V>> list = harray[i];
				for (HashElement <K, V> he : harray[i])
					Keys[counter++] = (T) he.key;
			}
			position = 0;
		}

		/**
		 * @return true of position in array is not at the end, false if so
		 */

		public boolean hasNext() {
			return position < Keys.length;
		}

		/**
		 * @return next element in Keys array
		 */

		public T next() {
			if (!hasNext())
				return null;
			return Keys[position++];
		}
	}
}
