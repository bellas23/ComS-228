package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Isabelle Singh
 */

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	/**
	 * Returns the number of items contained in the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the specefied item to the end of the list
	 * 
	 * @param item the item to be added at the end of the list
	 * @return true if tem was able to be added and false if no item was added
	 */
	@Override
	public boolean add(E item) {
		if (item == null) {
			throw new NullPointerException();
		}

		if (this.contains(item)) {
			return false;
		}
		if (size == 0) {
			Node newNode = new Node();
			newNode.addItem(item);
			head.next = newNode;
			newNode.previous = head;
			newNode.next = tail;
			tail.previous = newNode;
		} else {
			if (tail.previous.count < nodeSize) {
				tail.previous.addItem(item);
			} else {
				Node newNode = new Node();
				newNode.addItem(item);
				Node tempNode = tail.previous;
				tempNode.next = newNode;
				newNode.previous = tempNode;
				newNode.next = tail;
				tail.previous = newNode;
			}
		}
		size++;
		return true;
	}

	/**
	 * Adds the specified item into the specified position in the list
	 * 
	 * @param pos  the specified index for which to add the item
	 * @param item the specified item to add at the given index
	 */
	@Override
	public void add(int pos, E item) {
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}
		if (head.next == tail) {
			add(item);
		}
		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;
		
		if (offset == 0) {
			
			if (temp.previous.count < nodeSize && head != temp.previous) {
				temp.previous.addItem(item);
				size++;
				return;
			} else if (temp == tail) {
				add(item);
				size++;
				return;
			}
		}
		if (temp.count < nodeSize) {
			temp.addItem(offset, item);
		} 
		
		else {
			Node newSucc = new Node();
			int halfPoint = nodeSize / 2;
			int count = 0;
			for (int i = count; i < halfPoint; i++) {
				newSucc.addItem(temp.data[halfPoint]);
				temp.removeItem(halfPoint);
				count++;
			}
			Node oldSucc = temp.next;
			temp.next = newSucc;
			newSucc.previous = temp;
			newSucc.next = oldSucc;
			oldSucc.previous = newSucc;
			if (offset <= nodeSize / 2) {
				temp.addItem(offset, item);
			}
			if (offset > nodeSize / 2) {
				newSucc.addItem((offset - nodeSize / 2), item);
			}
		}
		size++;
	}

	/**
	 * Removes the item at the specified position in the list
	 * 
	 * @param pos the index of where to remove the item
	 */
	@Override
	public E remove(int pos) {
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}
		
		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;
		E nodeVal = temp.data[offset];
		
		if (temp.next == tail && temp.count == 1) {
			Node predecessor = temp.previous;
			predecessor.next = temp.next;
			temp.next.previous = predecessor;
			temp = null;
		} else if (temp.next == tail || temp.count > nodeSize / 2) {
			temp.removeItem(offset);
		} else {
			temp.removeItem(offset);
			Node succesor = temp.next;
			
			if (succesor.count > nodeSize / 2) {
				temp.addItem(succesor.data[0]);
				succesor.removeItem(0);
			} else if (succesor.count <= nodeSize / 2) {
				for (int i = 0; i < succesor.count; i++) {
					temp.addItem(succesor.data[i]);
				}
				temp.next = succesor.next;
				succesor.next.previous = temp;
				succesor = null;
			}
		}
		size--;
		return nodeVal;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		E[] list = (E[]) new Comparable[size];

		int tempIndex = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				list[tempIndex] = temp.data[i];
				tempIndex++;
			}
			temp = temp.next;
		}

		head.next = tail;
		tail.previous = head;

		insertionSort(list, new InsertionCompare());
		size = 0;
		for (int i = 0; i < list.length; i++) {
			add(list[i]);
		}
	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		E[] list = (E[]) new Comparable[size];

		int tempIndex = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				list[tempIndex] = temp.data[i];
				tempIndex++;
			}
			temp = temp.next;
		}

		head.next = tail;
		tail.previous = head;

		bubbleSort(list);
		size = 0;
		for (int i = 0; i < list.length; i++) {
			add(list[i]);
		}
	}

	/**
	 * Returns an iterator for Stoutlist that iterates in a forward direction
	 * 
	 * @return an iterator for the StoutList
	 */
	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	/**
	 * Returns a list iterator for Stoutlist that iterates both in a forward
	 * direction and backward direction
	 * 
	 * @return a list iterator for the StoutList
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	/**
	 * Returns a list iterator for Stoutlist that iterates both in a forward
	 * direction and backward direction, starting at the specified index
	 * 
	 * @param index the starting index for the list iterator
	 * @return a list iterator for the StoutList starting at the specified index
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Models a specific element in the list
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * Finds info for a specific item at the specefied position
	 * 
	 * @param current position of item who's info to find
	 * @return NodeInfo the info for a specific element in the list
	 */
	private NodeInfo find(int pos) {
		Node temp = head.next;
		int currentPos = 0;
		while (temp != tail) {
			if (currentPos + temp.count <= pos) {
				currentPos = currentPos + temp.count;
				temp = temp.next;
				continue;
			}
			NodeInfo nodeInfo = new NodeInfo(temp, pos - currentPos);
			return nodeInfo;
		}
		return null;
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	private class StoutListIterator implements ListIterator<E> {
		
		/**
		 * Keep track of current direction after the most recent action
		 */
		int previous = 0;
		int next = 1;
		
		/**
		 * Keep track of the iterator's cursor
		 */
		int current; 
		
		/**
		 * Keep track of the most recent action executed
		 */
		int recent;
		
		/**
		 * Array used to store data from StoudtList
		 */
		public E[] list;

		/**
		 * Default constructor
		 */
		public StoutListIterator() {
			current = 0;
			recent = -1;
			initialize();
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			current = pos;
			recent = -1;
			initialize();
		}

		/**
		 * Puts data from StoudtList into list as an array
		 */
		private void initialize() {
			list = (E[]) new Comparable[size];

			int tempIndex = 0;
			Node temp = head.next;
			while (temp != tail) {
				for (int i = 0; i < temp.count; i++) {
					list[tempIndex] = temp.data[i];
					tempIndex++;
				}
				temp = temp.next;
			}
		}

		/**
		 * Determines whether the iterator has another element to traverse in the list
		 * 
		 * @return true if there is a next element in the list, false otherwise
		 */
		@Override
		public boolean hasNext() {
			if (current >= size) {
				return false;
			} else {
				return true;
			}
		}

		/**
		 * Returns the next element the iterator will traverse in the list and shifts
		 * the pointer over 1 element
		 * 
		 * @return the next element the iterator will traverse in the list
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			recent = next;
			return list[current++];
		}

		/**
		 * Finds the last element returned by next() or previous() and removes it from
		 * the list and Stoutlist
		 */
		@Override
		public void remove() {
			if (recent == next) {
				StoutList.this.remove(current - 1);
				initialize();
				recent = -1;
				current--;
				if (current < 0)
					current = 0;
			} else if (recent == previous) {
				StoutList.this.remove(current);
				initialize();
				recent = -1;
			} else {
				throw new IllegalStateException();
			}
		}

		/**
		 * Determines whether the iterator has a previous element to traverse in the
		 * list
		 * 
		 * @return true if there is a previous element in the list, false otherwise
		 */
		@Override
		public boolean hasPrevious() {
			if (current <= 0)
				return false;
			else
				return true;
		}

		/**
		 * @return the index of next element
		 */
		@Override
		public int nextIndex() {
			return current;
		}

		/**
		 * Returns the previous element the iterator will traverse in the list and
		 * shifts the pointer back 1 (-1) element
		 * 
		 * @return the previous element the iterator will traverse in the list
		 */
		@Override
		public E previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			recent = previous;
			current--;
			return list[current];
		}

		/**
		 * @return the index of previous element
		 */
		@Override
		public int previousIndex() {
			return current - 1;
		}

		/**
		 * Replaces the current pointer's element with the specified element
		 * 
		 * @param e the specified element to replace the current pointer's element
		 */
		@Override
		public void set(E e) {
			if (recent == next) {
				NodeInfo nodeInfo = find(current - 1);
				nodeInfo.node.data[nodeInfo.offset] = e;
				list[current - 1] = e;
			} else if (recent == previous) {
				NodeInfo nodeInfo = find(current);
				nodeInfo.node.data[nodeInfo.offset] = e;
				list[current] = e;
			} else {
				throw new IllegalStateException();
			}

		}

		/**
		 * Adds the specefied element to the end of the list
		 * 
		 * @param e the element to be added to the end of the list
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			StoutList.this.add(current, e);
			current++;
			initialize();
			recent = -1;

		}
	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) {
			E curr = arr[i];
			int j = i - 1;
			while (j >= 0 && comp.compare(arr[j], curr) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = curr;
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		int len = arr.length;
		for (int i = 0; i < len - 1; i++) {
			for (int j = 0; j < len - i - 1; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					E t = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = t;
				}
			}
		}
	}

	/**
	 * Comparator for insertion sort
	 */
	class InsertionCompare<E extends Comparable<E>> implements Comparator<E> {
		
		@Override
		public int compare(E lhs, E rhs) {
			return lhs.compareTo(rhs);
		}
	}
}