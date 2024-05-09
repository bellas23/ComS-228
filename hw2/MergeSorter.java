package edu.iastate.cs228.hw2;

/**
 *  
 * @author Isabelle Singh
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts); // invoke superclass constructor to get pts[]
		super.algorithm = "Merge Sort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {

		mergeSortRec(this.points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {

		int middle = pts.length / 2;

		if (pts.length <= 1) {
			return;
		}

		// create new empty left and right arrays
		Point[] left = new Point[middle];
		Point[] right = new Point[pts.length - middle];

		// populate left array
		for (int i = 0; i < middle; i++) {
			left[i] = pts[i];
		}

		// populate right array
		int j = 0;
		for (int i = middle; i < pts.length; i++) {
			right[j] = pts[i];
			j++;
		}

		// call mergeSortRec recursively for each array
		mergeSortRec(left);
		mergeSortRec(right);

		
		merge(pts,left, right);
		
	}

	/**
	 * Merge two Point arrays
	 * 
	 * @param first  the first Point array
	 * @param second second Point array
	 */
	private void merge(Point[] result, Point[] first, Point[] second) {
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < first.length && j < second.length) {
			if (pointComparator.compare(first[i], second[j]) <= 0) {
				result[k] = first[i];
				i++;
			} else {
				result[k] = second[j];
				j++;
			}
			k++;
		}
		while (i < first.length) {
			result[k] = first[i];
			i++;
			k++;
		}
		while (j < second.length) {
			result[k] = second[j];
			j++;
			k++;
		}
	}
}
