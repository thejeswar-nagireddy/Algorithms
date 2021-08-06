package com.thejeswarn.algorithms.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {
	private List<T> list;

	public BinaryHeap() {
		this(1);
	}

	public BinaryHeap(int capacity) {
		list = new ArrayList<T>(capacity);
	}

	public BinaryHeap(T[] elements) {
		this(elements.length);
		for (T element : elements) {
			list.add(element);
		}
		for (int i = Math.max(0, (size() / 2) - 1); i >= 0; i--) {
			heapifyDown(i);
		}
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
			parent = parentIndex(k);
			if (parent <= 0)
				break;
			if (less(k, parent)) {
				swap(parent, k);
			}
			k = parent;
		}
	}

	private void heapifyDown(int k) {
		int rightChildIndex;
		int leftChildIndex;
		int swapIndex;
		while (true) {
			rightChildIndex = rightChildIndex(k);
			leftChildIndex = leftChildIndex(k);
			if (leftChildIndex >= size())
				break;
			swapIndex = leftChildIndex;
			if (rightChildIndex < size() && less(rightChildIndex, leftChildIndex)) {
				swapIndex = rightChildIndex;
			}
			if (less(swapIndex, k)) {
				swap(swapIndex, k);
			}
			k = swapIndex;
		}
	}

	private boolean less(int i, int j) {
		if (list.get(i).compareTo(list.get(j)) < 0)
			return true;
		return false;
	}

	private void swap(int i, int j) {
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

	public void add(T element) {
		list.add(element);
		heapifyUp(size() - 1);
	}

	public T peek() {
		if (isEmpty())
			return null;
		return list.get(0);
	}

	public T poll() {
		if (isEmpty())
			return null;
		T res = list.get(0);
		swap(0, size() - 1);
		list.remove(size() - 1);
		heapifyDown(0);
		return res;
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
}
