package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StreamerTest {

	Town t = new Town(4, 4);
	Streamer s = new Streamer(t, 1, 2);

	// just test if who returns the right state
	@Test
	void test() {
		assertEquals(s.who(), State.STREAMER);
	}
}