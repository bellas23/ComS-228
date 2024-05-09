package edu.iastate.cs228.hw2;

/**
 *  
 * @author Isabelle Singh
 *
 */

import java.util.Comparator;
import java.lang.IllegalArgumentException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later the sorted) sequence.
 *
 */
public abstract class AbstractSorter {

	protected Point[] points; // array of points operated on by a sorting algorithm.
								// stores ordered points after a call to sort().

	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
										// "quicksort". Initialized by a subclass constructor.

	protected Comparator<Point> pointComparator = null;

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Input was null or 0");
		} else {

			// create new array of Point objects with the same length as pts[]
			points = new Point[pts.length];

			// Independantly copy each Point object from pts[] over to points[]
			for (int i = 0; i < pts.length; i++) {

				// create new Point object for each element in pts[]
				points[i] = new Point(pts[i].getX(), pts[i].getY());
			}
		}
	}

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order ==
	 * 0, by y-coordinate if order == 1. Assign the comparator to the variable
	 * pointComparator.
	 * 
	 * 
	 * @param order 0 by x-coordinate 1 by y-coordinate
	 * 
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 * 
	 */
	public void setComparator(int order) throws IllegalArgumentException {
		if (order < 0 || order > 1) {
			throw new IllegalArgumentException("Error: Order is not 0 or 1. Please enter either 0 or 1");
		} 
		else if (order == 0) {
			Point.setXorY(true);
			pointComparator = new Comparator<Point>() {
				
				@Override
				public int compare(Point p1, Point p2) {
					return p1.compareTo(p2);
				}
			};
			
		} else if (order == 1) {
			Point.setXorY(false);
			pointComparator = new Comparator<Point>() {

				@Override
				public int compare(Point p1, Point p2) {
					return p1.compareTo(p2);
				}
			};
		}
	}

	/**
	 * Use the created pointComparator to conduct sorting.
	 * 
	 * Should be protected. Made public for testing.
	 */
	public abstract void sort();

	/**
	 * Obtain the point in the array points[] that has median index
	 * 
	 * @return median point
	 */
	public Point getMedian() {
		return points[points.length / 2];
	}

	/**
	 * Copys the array points[] onto the array pts[].
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts) {
		for (int i = 0; i < points.length; i++) {
			pts[i] = points[i];
		}
	}

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		Point temp = points[i]; // make a temporary copy of ith element
		points[i] = points[j]; // assign jth element to ith element
		points[j] = temp; // assign temporary copy of ith element to jth element to complete swap
	}

//	public void compareTo(Point lhs, Point rhs) {
//
//	}

}
