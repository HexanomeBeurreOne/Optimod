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
	public void testsGetPlanCorrect() {
		FactoryPlan factoryPlan = new FactoryPlan();
		// Test working plan
		// TODO : Check results
		String uriXml0 = "data/plan20x20.xml";
		Plan plan0 = factoryPlan.getPlan(uriXml0);
		assertNotNull("FAIL 0 : WORKING PLAN", plan0);
	}
	

	/**
	 * Test method for {@link model.FactoryPlan#getPlan(java.lang.String)}.
	 */
	@Test
	public void testsGetPlanNotWellXML() {
		FactoryPlan factoryPlan = new FactoryPlan();
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error1.xml", "FAIL 1 : EMPTY XML");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error2.xml", "FAIL 2 : NOT WEEL FORMED XML PLAN");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error3.xml", "FAIL 3 : NOT WEEL FORMED XML PLAN");
	}

	/**
	 * Test method for {@link model.FactoryPlan#getPlan(java.lang.String)}.
	 */
	@Test
	public void testsGetPlanWrongNodes() {
		FactoryPlan factoryPlan = new FactoryPlan();
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error4.xml", "FAIL 4 : NODES WITHOUT 'TRONCONS'");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error5.xml", "FAIL 5 : NODE WITHOUT ID");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error6.xml", "FAIL 6 : NODE WITHOUT X COORDINATE");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error7.xml", "FAIL 7 : NODE WITH STRING ID");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error8.xml", "FAIL 8 : NODE WITH STRING X COORDINATE");
		unitTestErrorPlan(factoryPlan, "data/plan20x20-error9.xml", "FAIL 9 : NODE WITH NEGATIVE X COORDINATE");
	}

	/**
	 * Test method for {@link model.FactoryPlan#getPlan(java.lang.String)}.
	 */
	@Test
	public void testsGetPlanWrongTroncon() {
		FactoryPlan factoryPlan = new FactoryPlan();
		int sizeAdresse0, sizeAdresse1, sizeAdresse2;
		Plan plan1 = factoryPlan.getPlan("data/plan20x20-error10.xml");
		plan1.affichePlan();
		sizeAdresse0 = plan1.getAdresseById(0).getTronconsSortants().size();
		sizeAdresse1 = plan1.getAdresseById(1).getTronconsSortants().size();
		sizeAdresse2 = plan1.getAdresseById(2).getTronconsSortants().size();
		assertTrue("FAIL 10 : TRONCON WITHOUT ID", 
				sizeAdresse0 == 1 && sizeAdresse1 == 2 && sizeAdresse2 == 3
				);
		Plan plan2 = factoryPlan.getPlan("data/plan20x20-error11.xml");
		plan2.affichePlan();
		sizeAdresse0 = plan2.getAdresseById(0).getTronconsSortants().size();
		sizeAdresse1 = plan2.getAdresseById(1).getTronconsSortants().size();
		sizeAdresse2 = plan2.getAdresseById(2).getTronconsSortants().size();
		assertTrue("FAIL 11 : TRONCON WITH WRONG SPEED", 
				sizeAdresse0 == 1 && sizeAdresse1 == 2 && sizeAdresse2 == 3
				);
	}
	
	/**
	 * Test if the plan returns null with a wrong XML as parameter 
	 * @param factoryPlan The Factory plan to create the plan
	 * @param fileURI The URI link to the XML file
	 * @param errorMessage The error message for JUnit
	 */
	private void unitTestErrorPlan(FactoryPlan factoryPlan, String fileURI, String errorMessage) {
		Plan plan = factoryPlan.getPlan(fileURI);
		assertNull(errorMessage, plan);
	}

}
