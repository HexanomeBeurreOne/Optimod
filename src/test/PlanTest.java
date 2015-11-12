/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Adresse;
import model.DemandeLivraisons;
import model.Livraison;
import model.Plan;
import model.factory.FactoryDemandeLivraisons;
import model.factory.FactoryPlan;

/**
 * @author Perso
 *
 */
public class PlanTest {

	/**
	 * Test method for {@link model.Plan#Plan()}.
	 */
	@Test
	public void testPlan() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#setDemandeLivraisons(model.DemandeLivraisons)}.
	 */
	@Test
	public void testSetDemandeLivraisons() {
		Plan plan = new Plan();
		DemandeLivraisons demandeLivraisons = new DemandeLivraisons();
		plan.setDemandeLivraisons(demandeLivraisons);

		assertTrue("FAIL 1 : getDemandeLivraisons not working", demandeLivraisons.equals(plan.getDemandeLivraisons()));
	}

	/**
	 * Test method for {@link model.Plan#removeLivraison(model.Livraison, model.FenetreLivraison)}.
	 */
	@Test
	public void testRemoveLivraison() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#addFenetreLivraison(model.FenetreLivraison)}.
	 */
	@Test
	public void testAddFenetreLivraison() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#getNom()}.
	 */
	@Test
	public void testGetNom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#setNom(java.lang.String)}.
	 */
	@Test
	public void testSetNom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#getAdresses()}.
	 */
	@Test
	public void testGetAdresses() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#setAdresses(java.util.List)}.
	 */
	@Test
	public void testSetAdresses() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#getTroncons()}.
	 */
	@Test
	public void testGetTroncons() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#setTroncons(java.util.List)}.
	 */
	@Test
	public void testSetTroncons() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#addAdresse(model.Adresse)}.
	 */
	@Test
	public void testAddAdresse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#removeAdresse(model.Adresse)}.
	 */
	@Test
	public void testRemoveAdresse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#addTroncon(model.Troncon)}.
	 */
	@Test
	public void testAddTroncon() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#getTournee()}.
	 */
	@Test
	public void testGetTournee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#setTournee(model.Tournee)}.
	 */
	@Test
	public void testSetTournee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#setObjetSelectionne(java.lang.Object, boolean)}.
	 */
	@Test
	public void testSetObjetSelectionne() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#calculTournee()}.
	 */
	@Test
	public void testCalculTournee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#dijkstra(model.Adresse, java.util.List)}.
	 */
	@Test
	public void testDijkstra() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#getAdresseById(int)}.
	 */
	@Test
	public void testGetAdresseById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#nouveauxPlusCourtsChemins(model.Adresse, model.Adresse)}.
	 */
	@Test
	public void testNouveauxPlusCourtsChemins() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#testSuppression(model.Adresse)}.
	 */
	@Test
	public void testTestSuppression() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#supprimerLivraison(model.Adresse)}.
	 */
	@Test
	public void testSupprimerLivraison() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#ajouterLivraison(int, model.Adresse, model.Adresse)}.
	 */
	@Test
	public void testAjouterLivraison() {
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		plan.calculTournee();
		
		//Trouver une adresse qui n'est pas une livraison
		Adresse nouvelleLivraisonAdresse = null;
		int adresseId = 0;
		while(nouvelleLivraisonAdresse == null)
		{
			nouvelleLivraisonAdresse = plan.getAdresses().get(adresseId);
			if(demandeLivraisons.getLivraison(nouvelleLivraisonAdresse) == null)	{
				nouvelleLivraisonAdresse = null;
			}
			adresseId++;
		}
		
		Adresse adressePrec = plan.getTournee().getEtapes().get(0).getLivraison().getAdresse();
		
		plan.ajouterLivraison(0, adressePrec, nouvelleLivraisonAdresse);
		
		assertEquals("FAIL 2 : ajouterLivrasionAvecFenetre(), new Livraison not added", plan.getTournee().getEtapes().get(1).getLivraison().getAdresse(), nouvelleLivraisonAdresse);
	}

	/**
	 * Test method for {@link model.Plan#ajouterLivraisonAvecFenetre(int, model.Adresse, model.Adresse, model.FenetreLivraison)}.
	 */
	@Test
	public void testAjouterLivraisonAvecFenetre() {
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		plan.calculTournee();
		
		//Trouver une adresse qui n'est pas une livraison
		Adresse nouvelleLivraisonAdresse = null;
		int adresseId = 0;
		while(nouvelleLivraisonAdresse == null)
		{
			nouvelleLivraisonAdresse = plan.getAdresses().get(adresseId);
			if(demandeLivraisons.getLivraison(nouvelleLivraisonAdresse) == null)	{
				nouvelleLivraisonAdresse = null;
			}
			adresseId++;
		}
		
		Livraison livPrec = plan.getTournee().getEtapes().get(0).getLivraison();
		
		plan.ajouterLivraisonAvecFenetre(0, livPrec.getAdresse(), nouvelleLivraisonAdresse, livPrec.getFenetreLivraison());
		
		assertEquals("FAIL 3 : ajouterLivrasionAvecFenetre(), new Livraison not added", plan.getTournee().getEtapes().get(1).getLivraison().getAdresse(), nouvelleLivraisonAdresse);
		assertEquals("FAIL 4 : ajouterLivrasionAvecFenetre(), new Livraison not added in right FenetreLivraison", plan.getTournee().getEtapes().get(1).getLivraison().getFenetreLivraison(), livPrec.getFenetreLivraison());
	}

	/**
	 * Test method for {@link model.Plan#testAjout(model.Adresse, model.Adresse)}.
	 */
	@Test
	public void testTestAjout() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#affichePlan()}.
	 */
	@Test
	public void testAffichePlan() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#chercheAdresse(int, int)}.
	 */
	@Test
	public void testChercheAdresse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Plan#cherche(int, int)}.
	 */
	@Test
	public void testCherche() {
		fail("Not yet implemented");
	}

}
