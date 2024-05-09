package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TownTest {

	Town t = new Town(4, 4);

//checks the length
	@Test
	void test() {
		assertEquals(t.getLength(), 4);
	}
}