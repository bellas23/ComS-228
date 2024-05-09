package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Isabelle Singh
 *
 */
public class Town {

	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;

		grid = new TownCell[length][width];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);

		try {
			Scanner scnr = new Scanner(file);
			this.length = scnr.nextInt();
			this.width = scnr.nextInt();
			grid = new TownCell[length][width];

			while (scnr.hasNextLine()) {
				for (int i = 0; i < length; i++) {
					for (int j = 0; j < width; i++) {
						if (scnr.next() == "C") {
							grid[i][j] = new Casual(this, i, j);
						} else if (scnr.next() == "S") {
							grid[i][j] = new Streamer(this, i, j);
						} else if (scnr.next() == "R") {
							grid[i][j] = new Reseller(this, i, j);
						} else if (scnr.next() == "E") {
							grid[i][j] = new Empty(this, i, j);
						} else if (scnr.next() == "O") {
							grid[i][j] = new Outage(this, i, j);
						}
					}
				}
				scnr.close();
			}
		}

		catch (FileNotFoundException e) {
			System.out.print("You have entered an incorrect file path.");
		}
	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; i++) {
				int randval = rand.nextInt(5);
				switch (randval) {
				case TownCell.CASUAL:
					grid[i][j] = new Casual(this, i, j);
					break;

				case TownCell.STREAMER:
					grid[i][j] = new Streamer(this, i, j);
					break;

				case TownCell.RESELLER:
					grid[i][j] = new Reseller(this, i, j);
					break;

				case TownCell.EMPTY:
					grid[i][j] = new Empty(this, i, j);
					break;

				case TownCell.OUTAGE:
					grid[i][j] = new Outage(this, i, j);
					break;
				}
			}
		}
	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; i++) {
				s += grid[i][j].who().toString().charAt(0) + "";
			}
			s += "\n";
		}
		return s;
	}
}
