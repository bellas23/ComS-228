package edu.iastate.cs228.hw1;

public class Streamer extends TownCell {

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * returns a streamer cell type
	 */
	@Override
	public State who() {
		return State.STREAMER;
	}

	/**
	 * Establishes rules for a streamer cell
	 */
	@Override
	public TownCell next(Town tNew) {

		if (nCensus[OUTAGE] <= 1 & nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}

		else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		}

		else if (nCensus[OUTAGE] > 0) {
			return new Empty(tNew, row, col);
		}

		else {
			return new Streamer(tNew, row, col);
		}

	}

}
