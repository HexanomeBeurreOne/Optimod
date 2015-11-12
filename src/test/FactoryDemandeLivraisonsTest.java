package test;

import static org.junit.Assert.*;

import model.FenetreLivraison;
import model.factory.FactoryDemandeLivraisons;

import org.junit.Test;

public class FactoryDemandeLivraisonsTest {

	@Test
	public void testGetLivraison() {
		//Livraison livraison = getLivraison(int client, int idAdresse, FenetreLivraison fenetreLivraison);
		fail("Not yet implemented");
	}

	@Test
	public void testGetFenetreLivraison() {
		FactoryDemandeLivraisons factoryTest = new FactoryDemandeLivraisons();
		
		// ERRORS
		
		FenetreLivraison fenetreLivraison1 = factoryTest.getFenetreLivraison(4, 4);
		assertNull("ERROR 1 : HOUR1 = HOUR2", fenetreLivraison1);

		FenetreLivraison fenetreLivraison2 = factoryTest.getFenetreLivraison(4, 2);
		assertNull("ERROR 2 : HOUR 1 > HOUR 2", fenetreLivraison2);
		
		FenetreLivraison fenetreLivraison3 = factoryTest.getFenetreLivraison(-4, 2);
		assertNull("ERROR 3 : HOUR 1 < 0", fenetreLivraison3);
		
		FenetreLivraison fenetreLivraison4 = factoryTest.getFenetreLivraison(23, 25);
		assertNull("ERROR 4 : HOUR 2 < 24", fenetreLivraison4);
		
		FenetreLivraison fenetreLivraison7 = factoryTest.getFenetreLivraison(25, 27);
		assertNull("ERROR 4 : HOUR 1 < 24", fenetreLivraison7);
		
		// CORRECT TESTS

		FenetreLivraison fenetreLivraison5 = factoryTest.getFenetreLivraison(0, 2);
		assertNotNull("OK 1 : HOUR1 = 0, HOUR2 = 2", fenetreLivraison5);
		assertTrue("OK 1 : HOUR1 = 0, HOUR2 = 2", 
				fenetreLivraison5.getHeureDebut() == 0 && fenetreLivraison5.getHeureDebut() == 2
				);

		FenetreLivraison fenetreLivraison6 = factoryTest.getFenetreLivraison(2, 4);
		assertTrue("OK 1 : HOUR1 = 2, HOUR2 = 4", 
				fenetreLivraison6.getHeureDebut() == 2 && fenetreLivraison6.getHeureDebut() == 4
				);
	}

	@Test
	public void testConvertTimeToSeconds() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckNodeTypeAndName() {
		fail("Not yet implemented");
	}

	@Test
	public void testAjouterEntrepot() {
		fail("Not yet implemented");
	}

	@Test
	public void testAjouterFenetreLivraison() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerifierChevauchementFenetreLivraison() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDemandeLivraisons() {
		fail("Not yet implemented");
	}

}
