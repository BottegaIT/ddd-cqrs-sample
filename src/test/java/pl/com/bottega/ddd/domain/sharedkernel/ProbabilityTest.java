package pl.com.bottega.ddd.domain.sharedkernel;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import org.junit.Test;

import pl.com.bottega.ddd.domain.sharedkernel.Probability;



public class ProbabilityTest {

	@Test
	public void testFromDecimal(){
		//given
		Probability p = Probability.fromDecimal(new BigDecimal(0.5));

		assertNotNull(p);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromDecimalNegativeNumber(){
		//given
		Probability p = Probability.fromDecimal(new BigDecimal(-0.5));

		assertNotNull(p);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromDecimalOverOne(){
		//given
		Probability p = Probability.fromDecimal(new BigDecimal(1.5));

		assertNotNull(p);
	}
	
	@Test
	public void testEqualsSame(){
		//given
		Probability p1 = Probability.fromDecimal(new BigDecimal(0.5));
		Probability p2 = Probability.fromDecimal(new BigDecimal(0.5));

		assertTrue(p1.equals(p2));
	}
	
	@Test
	public void testEqualsNotSame(){
		//given
		Probability p1 = Probability.fromDecimal(new BigDecimal(0.5));
		Probability p2 = Probability.fromDecimal(new BigDecimal(0.6));

		assertFalse(p1.equals(p2));
	}
	
	@Test
	public void testCombinedWith(){
		//given
		Probability p1 = Probability.fromDecimal(new BigDecimal(0.5));
		Probability p2 = Probability.fromDecimal(new BigDecimal(0.6));
		Probability result = Probability.fromDecimal(new BigDecimal(0.3));

		///when
		Probability combination = p1.combinedWith(p2);
		
		//then
		assertEquals(result, combination);
	}
	
	@Test
	public void testNot(){
		//given
		Probability p1 = Probability.fromDecimal(new BigDecimal(0.1));
		Probability result = Probability.fromDecimal(new BigDecimal(0.9));

		///when
		Probability negation = p1.not();
		
		//then
		assertEquals(result, negation);
	}
}
