package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.ControleurApplication;
import model.DemandeLivraisons;
import model.Plan;
import model.factory.FactoryDemandeLivraisons;
import model.factory.FactoryPlan;

public class ControleurApplicationTest {
	
	@Before
	public void setUp() {
		Plan plan = new Plan();
		ControleurApplication controler = new ControleurApplication(plan, 1);
	}

	@Test
	public void testUndo() {
		fail("Not yet implemented");
	}

	@Test
	public void testRedo() {
		fail("Not yet implemented");
	}

	@Test
	public void testChargerPlan() {
		fail("Not yet implemented");
	}

	@Test
	public void testChargerDemandeLivraisons() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculerTournee() {
		fail("Not yet implemented");
	}

	@Test
	public void testAjouterLivraison() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveLivraison() {
		fail("Not yet implemented");
	}

}
