package edu.iastate.cs228.hw1;

public class Empty extends TownCell {
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * returns an empty cell type
	 */
	@Override
	public State who() {
		return State.EMPTY;
	}

	/**
	 * Establishes rules for an empty cell
	 */
	@Override
	public TownCell next(Town tNew) {

		int[] nCensus = new int[5];

		census(nCensus);

		if (nCensus[OUTAGE] <= 1 & nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}
		return new Casual(tNew, row, col);
	}
}