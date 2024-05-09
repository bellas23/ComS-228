package edu.iastate.cs228.hw2;

/**
 *  
 * @author Isabelle Singh
 *
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

public class InsertionSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts); // invoke superclass constructor to get pts[]
		super.algorithm = "Insertion Sort";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		for (int i = 1; i < points.length; i++) {
			Point temp = points[i]; // current element
			int j = i - 1; // element before current element
			while ((j > -1)// while j is within array bounds

					// while jth element is greater than ith element
					&& (pointComparator.compare(points[j], temp) > 0)) {

				points[j + 1] = points[j]; // shift elements to the right
				j--; // decrement j because we shifted elements to the right
			}
			points[j + 1] = temp; // insert temp into correct position of sorted array
		}
	}
}
