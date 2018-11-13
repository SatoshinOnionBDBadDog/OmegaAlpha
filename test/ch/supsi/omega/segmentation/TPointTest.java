package ch.supsi.omega.segmentation;

import static org.junit.Assert.*;

import org.junit.Test;

public class TPointTest
{
	@SuppressWarnings("deprecation")
	@Test
	public void testEqualsObject()
	{
		TPoint one   = new TPoint(1, 1, 0.121, 0.12);
		TPoint two 	 = new TPoint(1, 1, 0.124, 0.12);
		TPoint three = new TPoint(1, 1, 0.128, 0.12);
		
		assertEquals(one, two);
		assertFalse(one.equals(three));
	}
}
