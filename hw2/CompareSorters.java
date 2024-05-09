package edu.iastate.cs228.hw2;

/**
 *  
 * @author Isabelle Singh
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println("keys  1 (random integers)  2 (file input)  3 (exit)");

		Scanner scnr = new Scanner(System.in);

		int key = 1;
		int trial = 1;

		Random rand = new Random();

		PointScanner[] scanners = new PointScanner[4];

		while (true) {

			//call generateRandomPoints() to initialize an array of random points
			System.out.print("Trial " + trial + ": ");
			key = scnr.nextInt();
			System.out.println();

			if (key != 1 && key != 2) {
				System.exit(0);

			} else {

				if (key == 1) { // key 1 - generate random points

					System.out.print("Enter number of random points: ");
					int numPts = scnr.nextInt();
					System.out.println();

					Point[] pts = generateRandomPoints(numPts, rand);

					// initialize 4 different algorithm PointScanner objects over the point array
					scanners[0] = new PointScanner(pts, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(pts, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(pts, Algorithm.MergeSort);
					scanners[3] = new PointScanner(pts, Algorithm.QuickSort);
				}

				// b) Reassigns to the array scanners[] (declared below) the references to four
				// new PointScanner objects, which are created using four different values
				// of the Algorithm type: SelectionSort, InsertionSort, MergeSort and QuickSort.
				// PointScanner[] scanners = new PointScanner[4];

				else { // key 2 - read points from file

					System.out.println("Points from a file");
					System.out.print("File name: ");
					String pointsFile = scnr.next();

					// a) Initialize scanners[]
					scanners[0] = new PointScanner(pointsFile, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(pointsFile, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(pointsFile, Algorithm.MergeSort);
					scanners[3] = new PointScanner(pointsFile, Algorithm.QuickSort);
				}
			}

			// b) Iterate through scanners[] and call scan()
			for (int i = 0; i < scanners.length; i++) {
				scanners[i].scan();
				scanners[i].writeMCPToFile();
			}

			// c) print out statistics table
			System.out.println("");
			System.out.printf("%-17s %-10s %-10s \n", "algorithm", "size", "time (ns)");
			System.out.println("--------------------------------------");

			for (int i = 0; i < scanners.length; i++) {
				System.out.println(scanners[i].stats());
			}

			System.out.println("--------------------------------------");
			System.out.println();

			trial++;
		}
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] ï¿½
	 * [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException("Error: Number of points is less than 1");
		} else {
			Point[] points = new Point[numPts];
			int x;
			int y;

			for (int i = 0; i < numPts; i++) {
				x = rand.nextInt(101) - 50;
				y = rand.nextInt(101) - 50;

				Point p = new Point(x, y);
				points[i] = p;
			}

			return (points);
		}
	}
}
