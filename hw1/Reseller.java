package edu.iastate.cs228.hw1;

/*
 * Class that models a Reseller cell
 */
public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	
	/**
	 * returns a reseller cell type
	 */
	@Override
	public State who() {
		return State.RESELLER;
	}

	/**
	 * Establishes rules for a reseller cell
	 */
	@Override
	public TownCell next(Town tNew) {

		int[] nCensus = new int[5];

		census(nCensus);

		if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3) {
			return new Empty(tNew, row, col);
		}
		if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		} else {

			return new Reseller(tNew, row, col);
		}
	}
}
