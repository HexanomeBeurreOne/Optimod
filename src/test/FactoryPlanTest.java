/**
 * 
 */
package test;

import static org.junit.Assert.*;
import model.Plan;
import model.FactoryPlan;

import org.junit.Test;

/**
 * @author ccanete
 *
 */
public class FactoryPlanTest {

	/**
	 * Test method for {@link model.FactoryPlan#getDomTree(java.lang.String)}.
	 */
	@Test
	public void testGetDomTree() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.FactoryPlan#getAdresse(int, int, int)}.
	 */
	@Test
	public void testGetAdresse() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.FactoryPlan#getTroncon(java.lang.String, double, double, int)}.
	 */
	@Test
	public void testGetTroncon() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link model.FactoryPlan#getPlan(java.lang.String)}.
	 */
	@Test
	public void testsGetPlan() {
		FactoryPlan factoryPlan = new FactoryPlan();
		// Test working plan
		// TODO : Check results
		String uriXml0 = "data/plan20x20.xml";
		Plan plan0 = factoryPlan.getPlan(uriXml0);
		assertNotNull("FAIL 0 : WORKING PLAN", plan0);
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error1.xml", "FAIL 1 : EMPTY XML");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error2.xml", "FAIL 2 : NOT WEEL FORMED XML PLAN");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error3.xml", "FAIL 3 : NOT WEEL FORMED XML PLAN");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error4.xml", "FAIL 4 : NODES WITHOUT 'TRONCONS'");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error5.xml", "FAIL 5 : NODE WITHOUT ID");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error6.xml", "FAIL 6 : NODE WITHOUT NO X COORDINATE");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error7.xml", "FAIL 7 : NODE WITH STRING ID");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error8.xml", "FAIL 8 : NODE WITH STRING X COORDINATE");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error9.xml", "FAIL 9 : NODE WITH NEGATIVE X COORDINATE");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error10.xml", "FAIL 10 : TRONCON WIHTOUT STREETNAME");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error11.xml", "FAIL 11 : TRONCON WITH WRONG SPEED");
	}
	
	private void unitTestErrorPlan(FactoryPlan factoryPlan, String fileURI, String errorMessage) {
		System.out.println("TEST PLAN : " + fileURI);
		Plan plan = factoryPlan.getPlan(fileURI);
		if(plan!=null){
			plan.affichePlan();
		}
		else {
			System.out.println("NO PLAN");
		}
		assertTrue(errorMessage, plan==null);
	}

}
