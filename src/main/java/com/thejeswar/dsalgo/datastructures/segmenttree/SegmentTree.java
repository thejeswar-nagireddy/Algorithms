package com.thejeswar.dsalgo.datastructures.segmenttree;

import java.util.Arrays;

/**
 * Segment Tree, which is used to find the MIN , MAX , SUM or Product etc in the
 * given range
 *
 * 
 * @author Thejeswar Nagireddy, thejeswarreddyn@hotmail.com
 */
public class SegmentTree {
	int[] inputArr;
	private int[] stArray;

	public SegmentTree(int[] inputArr) {
		this.inputArr = inputArr;
		stArray = new int[getSTLength(inputArr.length)];
		Arrays.fill(stArray, Integer.MAX_VALUE);
		buildTree();
	}

	// To build the Segment Tree
	private void buildTree() {
		int arrLen = inputArr.length;
		buildTreeHelper(0, arrLen - 1, 0);
	}

	private int buildTreeHelper(int low, int high, int position) {
		if (low == high) {
			stArray[position] = inputArr[low];
		} else {
			int mid = low + (high - low) / 2;
			stArray[position] = Math.min(buildTreeHelper(low, mid, 2 * position + 1),
					buildTreeHelper(mid + 1, high, 2 * position + 2));
		}
		return stArray[position];
	}

	/*
	 * To query the min value in the given range
	 * 
	 * @param start - start of the query range
	 * 
	 * @param end - end of the query range
	 */
	public int getMinInRange(int start, int end) {
		return getMinInRange(start, end, 0, inputArr.length - 1, 0);
	}

	private int getMinInRange(int start, int end, int low, int high, int position) {
		if (start <= low && high <= end)
			return stArray[position];
		else if (end < low || start > high)
			return Integer.MAX_VALUE;
		int mid = low + (high - low) / 2;
		return Math.min(getMinInRange(start, end, low, mid, 2 * position + 1),
				getMinInRange(start, end, mid + 1, high, 2 * position + 2));
	}

	/*
	 * To update the value at the specified index and propagate its effect up to the
	 * root
	 * 
	 * @param index - index of the element to be updated
	 * 
	 * @param val - updated value
	 */
	public void updateValue(int index, int val) {
		if (index < 0 || index >= inputArr.length) {
			System.out.println("Invalid input!");
			return;
		}
		int prevValue = inputArr[index];
		inputArr[index] = val;
		if (prevValue > val)
			updateValueUtil(0, inputArr.length - 1, index, val, 0);
	}

	private void updateValueUtil(int low, int high, int index, int val, int position) {
		if (index < low || index > high)
			return;
		stArray[position] = Math.min(val, stArray[position]);
		if (low != high) {
			int mid = low + (high - low) / 2;
			updateValueUtil(low, mid, index, val, 2 * position + 1);
			updateValueUtil(mid + 1, high, index, val, 2 * position + 2);
		}
	}

	// Utility methods
	/*
	 * To get the length of the segment tree for the given input array length using
	 * the given formulae 
	 * stLength = 2 * arrLen - 1, if arrLen is a power of 2 
	 * 			= 2 * (N) - 1, where N is the next power of 2, which is greater than arrLen
	 */
	private static int getSTLength(int arrLen) {
		// assume arrLen = 5
		if (isPowerOf2(arrLen)) {
			return arrLen * 2 - 1;
		}
		int count = 0;
		while (arrLen / 2 >= 1) {
			count++;
			arrLen /= 2;
		}
		return ((int) Math.pow(2, count + 1)) * 2 - 1;
	}

	/*
	 * To check, if the given number is power of 2 or not
	 */
	private static boolean isPowerOf2(int num) {
		if ((num & num - 1) == 0)
			return true;
		return false;
	}

	public static void main(String[] args) {
		// Segment tree range query
		int[] arr = { -6, 3, 5, 2, 1, 13 };
		SegmentTree st = new SegmentTree(arr);
		System.out.println("res: " + st.getMinInRange(2, 9));
		st.updateValue(3, -10);
		System.out.println("res: " + st.getMinInRange(2, 9));

	}

}
