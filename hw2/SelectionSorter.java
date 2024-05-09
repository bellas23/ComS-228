package edu.iastate.cs228.hw2;

/**
 *  
 * @author Isabelle Singh
 *
 */

/**
 * 
 * This class implements selection sort.
 *
 */

public class SelectionSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts); // invoke superclass constructor to get pts[]
		super.algorithm = "Selection Sort";

	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		for (int i = 0; i < points.length; i++) {
			int minIndex = i; // index with smallest value
			for (int j = i + 1; j < points.length; j++) {

				// if jth element is less than current minIndex
				if (pointComparator.compare(this.points[j], this.points[minIndex]) < 0) {
					minIndex = j; // make j the new minIndex
				}
			}
			super.swap(i, minIndex); // place sorted element at front of array
		}
	}
}
