/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Adresse;
import model.Chemin;
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

		assertEquals("FAIL 1 : getDemandeLivraisons not working", demandeLivraisons, plan.getDemandeLivraisons());
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
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		//assertNull("FAIL 2 : Tournee not null, but it should be null before calcul", plan.getTournee());
		
		plan.calculTournee();
		
		assertNotNull("FAIL 3 : Tournee null, but it should not be null after calcul", plan.getTournee());
		
		assertEquals("FAIL 4 : Tournee's Entrepot not equal to DemandeLivraisons's Entrepot", plan.getTournee().getEntrepot(), plan.getDemandeLivraisons().getEntrepot());
	}

	/**
	 * Test method for {@link model.Plan#dijkstra(model.Adresse, java.util.List)}.
	 */
	@Test
	public void testDijkstra() {
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		List<Adresse> cibles = new ArrayList<Adresse>();
		cibles.add(plan.getAdresses().get(1));
		Hashtable<Integer, Chemin> dij = plan.dijkstra(plan.getAdresses().get(0), cibles);
		assertEquals("FAIL 5 : dijsktra(), size result is not right", dij.size(), 1);
		assertEquals("FAIL 6 : dijsktra(), chemin is misformed", dij.get(plan.getAdresses().get(1).getId()).getDebut(), plan.getAdresses().get(0));
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
		
		assertEquals("FAIL 3 : ajouterLivrasionAvecFenetre(), new Livraison not added", plan.getTournee().getEtapes().get(1).getLivraison().getAdresse(), nouvelleLivraisonAdresse);
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
		
		assertEquals("FAIL 4 : ajouterLivrasionAvecFenetre(), new Livraison not added", plan.getTournee().getEtapes().get(1).getLivraison().getAdresse(), nouvelleLivraisonAdresse);
		assertEquals("FAIL 5 : ajouterLivrasionAvecFenetre(), new Livraison not added in right FenetreLivraison", plan.getTournee().getEtapes().get(1).getLivraison().getFenetreLivraison(), livPrec.getFenetreLivraison());
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
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		plan.calculTournee();
		
		int x, y;
		Adresse adresse;
		List<Adresse> adresses = plan.getAdresses();
		Iterator<Adresse> it = adresses.iterator();
		
		while(it.hasNext())	{
			adresse = it.next();
			x = adresse.getCoordX();
			y = adresse.getCoordY();
			if(plan.getDemandeLivraisons().getLivraison(adresse) == null)	{
				assertEquals("FAIL 5 : cherche(), adresse not recognized in plan with its coordinates", adresse, plan.cherche(x, y, 2));
			}
			else	{
				assertEquals("FAIL 6 : cherche(), livraison not recognized in plan with its coordinates", plan.getDemandeLivraisons().getLivraison(adresse), plan.cherche(x, y, 2));
			}
		}
		
		x=0;
		y=0;
		assertNull("FAIL 7 : cherche(), livraison not recognized in plan with its coordinates", plan.cherche(x, y, 2));
	}

}
