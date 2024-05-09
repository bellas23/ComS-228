package edu.iastate.cs228.hw2;

/**
 * 
 * @author Isabelle Singh
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected long scanTime; // execution time in nanoseconds.
	
	private String outputFile = null;

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {

		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("pts is null or of no length");
		}

		// copy the points into points[] array
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}

		// assign the correct sorting algorithm
		this.sortingAlgorithm = algo;

	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {

		File input = new File(inputFileName);
		Scanner scnr = new Scanner(input);

		// temporary array to store integers from file
		ArrayList<Integer> intArr = new ArrayList<Integer>();

		// load integers into file
		while (scnr.hasNextInt()) {
			intArr.add(scnr.nextInt());
		}

		// check for even number of integers
		if ((intArr.size() % 2) != 0) {
			throw new InputMismatchException("File contains an odd number of values");
		}

		// create new Points[] array
		this.points = new Point[intArr.size() / 2];

		// make a new Point object for every two values
		int j = 0;
		for (int i = 0; i < intArr.size(); i += 2) {

			// create the new Point object with sequential values in intArr
			Point p = new Point(intArr.get(i), intArr.get(i + 1));

			// assign Point object to the instance variable
			this.points[j] = p;

			j++;
		}

		// assign correct algorithm
		this.sortingAlgorithm = algo;
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		AbstractSorter aSorter;

		// create object to be referenced by aSorter according to sortingAlgorithm.
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(this.points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(this.points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(this.points);
		} else {
			aSorter = new QuickSorter(this.points);
		}

		// For each of the two rounds of sorting, have aSorter do the following:
		// a) call setComparator() with an argument 0 or 1.
		int x = 0;
		int y = 0;
		long start = System.nanoTime(); // get start time

		for (int i = 0; i < 2; i++) {
			// call setComparator
			aSorter.setComparator(i);

			// b) call sort().
			if (i == 0) {
				aSorter.sort();
				// x = aSorter.getMedian().getX();
			}

			if (i == 1) {
				aSorter.sort();
				// y = aSorter.getMedian().getY();
			}

			// c) use a new Point object to store coordinates of medianCoordinatePoint
			medianCoordinatePoint = new Point(x = aSorter.getMedian().getX(), y = aSorter.getMedian().getY());

			// d) set medianCoordinatePoint reference to object with correct coordinates.

			// e) sum up times spent on both sorting rounds and set the variable scanTime.
			long end = System.nanoTime();
			this.scanTime = end - start;
		}
	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		String returnString = String.format("%-17s %-10d %-10d", this.sortingAlgorithm, this.points.length,
				this.scanTime);
		return (returnString);
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		return ("MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")");
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException {
	    if (medianCoordinatePoint == null) {
	        return; 
	    }

	    String outputFileName = "medianCoordinatePoint.txt";

	    try (PrintWriter writer = new PrintWriter(outputFileName)) {
	        writer.println(this.toString());
	    } catch (FileNotFoundException e) {
	        throw e; 
	    }
	}
}
