package com.thejeswar.dsalgo.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/*
 * Binary Min Heap implementation
 */
public class BinaryHeap<T extends Comparable<T>> {

	private List<T> list;

	private Map<T, TreeSet<Integer>> map = new HashMap<>();

	public BinaryHeap() {
		this(1);
	}

	public BinaryHeap(int capacity) {
		list = new ArrayList<>(capacity);
	}

	// Construct a priority queue using heapify in O(n) time, a great explanation
	// can be found at:
	// http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
	public BinaryHeap(T[] elements) {
		this(elements.length);
		for (int i = 0; i < elements.length; i++) {
			list.add(elements[i]);
			mapAdd(elements[i], i);
		}
		// Heapify process, O(n)
		for (int i = Math.max(0, (size() / 2) - 1); i >= 0; i--)
			heapifyDown(i);
	}

	public void add(T element) {
		if (element == null)
			throw new IllegalArgumentException();
		list.add(element);
		mapAdd(element, size() - 1);
		heapifyUp(size() - 1);
	}

	public T peek() {
		if (isEmpty())
			return null;
		return list.get(0);
	}

	public T poll() {
		return removeAt(0);
	}

	/*
	 * To remove the given element from the heap, in O(logN)
	 */
	public boolean remove(T element) {
		T res = null;
		if (element != null && size() > 0) {
			TreeSet<Integer> set = map.get(element);
			if (set != null && set.size() > 0)
				res = removeAt(set.last());
		}
		return res != null;
	}

	/*
	 * To remove the element at the given index, in O(logN)
	 */
	private T removeAt(int index) {
		if (isEmpty())
			return null;
		if (index < size() - 1)
			swap(index, size() - 1);
		T element = list.get(size() - 1);
		list.remove(size() - 1);
		mapRemove(element, size());
		if (index < size())
			heapifyDown(index);
		if (index > 0)
			heapifyUp(index);
		return element;
	}

	private int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	private void heapifyUp(int k) {
		int parent;
		while (true) {
			if (k <= 0)
				break;
			parent = parentIndex(k);
			if (less(k, parent))
				swap(parent, k);
			k = parent;
		}
	}

	private void heapifyDown(int index) {
		while (true) {
			int rightChildIndex = rightChildIndex(index);
			int leftChildIndex = leftChildIndex(index);
			// If the leftChildIndex is outside the range of heap
			if (leftChildIndex >= size())
				break;
			// Assume, the left is the smallest node
			int swapIndex = leftChildIndex;
			// Find the smallest one and correct the swapIndex
			if (rightChildIndex < size() && less(rightChildIndex, leftChildIndex))
				swapIndex = rightChildIndex;
			// If we cann't sink index anymore
			if (less(index, swapIndex))
				break;
			swap(swapIndex, index);
			index = swapIndex;
		}
	}

	private boolean less(int indxOne, int indxTwo) {
		return list.get(indxOne).compareTo(list.get(indxTwo)) < 0;
	}

	private void swap(int indxOne, int indxTwo) {
		mapSwap(list.get(indxOne), list.get(indxTwo), indxOne, indxTwo);
		T temp = list.get(indxOne);
		list.set(indxOne, list.get(indxTwo));
		list.set(indxTwo, temp);
	}

	private int parentIndex(int k) {
		return (k - 1) / 2;
	}

	private int leftChildIndex(int k) {
		return 2 * k + 1;
	}

	private int rightChildIndex(int k) {
		return 2 * k + 2;
	}

	private void mapRemove(T element, int index) {
		TreeSet<Integer> set = map.get(element);
		if (set.size() <= 1)
			map.remove(element);
		else
			set.remove(index);
	}

	private void mapAdd(T element, int index) {
		TreeSet<Integer> set = map.get(element);
		if (set == null) {
			set = new TreeSet<Integer>();
			set.add(index);
			map.put(element, set);
		} else
			set.add(index);
	}

	private void mapSwap(T val1, T val2, int val1Indx, int val2Indx) {
		TreeSet<Integer> oneSet = map.get(val1);
		TreeSet<Integer> twoSet = map.get(val2);
		oneSet.remove(val1Indx);
		twoSet.remove(val2Indx);
		oneSet.add(val2Indx);
		twoSet.add(val1Indx);
	}

	static class Node implements Comparable<Node> {
		int id;
		int value;

		public Node(int id, int value) {
			this.id = id;
			this.value = value;
		}

		@Override
		public int compareTo(Node node) {
			return value - node.value;
		}
	}

	/* For testing */
	public static void main(String[] args) {
		BinaryHeap<Node> heap = new BinaryHeap<>();
		heap.add(new Node(0, 5));
		heap.add(new Node(1, 2));
		heap.add(new Node(2, 3));
		heap.add(new Node(3, 1));
		heap.add(new Node(4, 7));
		heap.add(new Node(5, 2));
		while (!heap.isEmpty()) {
			System.out.println(heap.poll().value);
		}
	}
}
