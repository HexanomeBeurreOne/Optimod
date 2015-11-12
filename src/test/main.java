package test;

import controller.ControleurApplication;
import model.*;
import view.*;

public class main {

	public static void main(String[] args) {
		/*
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		plan.calculTournee();
		Tournee tournee = plan.getTournee();
		// _________
		Livraison liv, livPrec;
		liv = tournee.getEtapes().get(10).getLivraison();
		livPrec = tournee.getEtapes().get(9).getLivraison();
		plan.supprimerLivraison(liv.getAdresse());
		System.out.println(tournee);
		plan.ajouterLivraison(800, livPrec.getAdresse(), liv.getAdresse());
		System.out.println(tournee);
		// _________
		*/

		Plan plan = new Plan();
		ControleurApplication controleur = new ControleurApplication(plan, 1);
		
	}
}
