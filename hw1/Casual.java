package edu.iastate.cs228.hw1;

public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * returns a casual cell type
	 */
	@Override
	public State who() {
		return State.CASUAL;
	}

	/**
	 * Establishes rules for a casual cell
	 */
	@Override
	public TownCell next(Town tNew) {

		int[] nCensus = new int[5];
		census(nCensus);

		if (nCensus[OUTAGE] <= 1 & nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}

		else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		}

		else if (nCensus[STREAMER] > 0 || nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}

		else {
			return new Casual(tNew, row, col);
		}

	}

}
