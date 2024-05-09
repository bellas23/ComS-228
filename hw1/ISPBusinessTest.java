package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ISPBusinessTest {

	Town t = new Town(4, 4);

//checks the profit return to the nubmer of casual
	@Test
	void test() {
		t.randomInit(10);
		assertEquals(ISPBusiness.getProfit(t), 1);
	}
}