package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

/**
 * @author Isabelle Singh
 *
 *         The ISPBusiness class performs simulation over a grid plain with
 *         cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {

	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());

		for (int i = 0; i < tOld.getLength(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {

				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);

			}
		}

		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return numCasual Number of casual cells
	 */
	public static int getProfit(Town town) {

		int numCasual = 0;

		for (int i = 0; i < town.getLength(); i++) {

			for (int j = 0; j < town.getLength(); j++) {
				if (town.grid[i][j].who() == State.CASUAL) {

					numCasual += 1;
				}
			}
		}

		return numCasual;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid via an input file (option: 1) or wants to generate it randomly
	 * (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and if user choice is to populate it randomly, then populate the
	 * grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle). Print the final profit in terms of %. You should print the
	 * profit percentage with two digits after the decimal point: Example if profit
	 * is 35.5600004, your output should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {

		int input;
		double profit;
		final int billingCycle = 12;

		Scanner scnr = new Scanner(System.in);

		System.out.println("How to populate grid (type 1 or 2):\n1 - from a file\n2 - randomly with seed\n");
		input = scnr.nextInt();
		Town town = null;

		if (input == 1) {
			String filePath = "";
			try {

				System.out.println("Enter the file name:\n");
				scnr.nextLine();
				filePath = scnr.nextLine();

				File file = new File(filePath);
				town = new Town(filePath);
			}

			catch (FileNotFoundException e) {
				System.out.println("ERROR: Invalid file path");
			}
		}
		if (input == 2) {
			int seed;
			int row;
			int col;
			System.out.println("Provide rows, cols and seed integer separated by spaces:\n");
			row = scnr.nextInt();
			col = scnr.nextInt();
			seed = scnr.nextInt();
			town = new Town(row, col);

			town.randomInit(seed);

		}
		double gain = 0.0;

		for (int month = 0; month < 12; month++) {

			gain += (getProfit(town) / ((double) town.getWidth() * (double) town.getLength())) * 100;
			town = updatePlain(town);

		}
		gain = gain / 12;

		System.out.printf("%.2f%c", gain, '%');

	}
}