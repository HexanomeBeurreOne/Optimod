package test;

import controller.ControleurApplication;
import model.*;
import view.*;

public class main {

	public static void main(String[] args) {

		FactoryPlan factoryPlan = new FactoryPlan();
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);
		plan.setDemandeLivraisons(demandeLivraisons);
		
		plan.calculTournee();
		Tournee tournee = plan.getTournee();
		
		Livraison liv;
		for(int i = 0; i<12 ; i++) {
			liv = tournee.getEtapes().get(0).getLivraison();
			plan.supprimerLivraison(liv.getAdresse());
			System.out.println(tournee);
		}
		
		/*
		liv = tournee.getEtapes().get(10).getLivraison();
		plan.supprimerLivraison(liv);
		plan.ajouterLivraison(liv, liv.getFenetreLivraison(), 10);
		System.out.println(tournee);*/

		//Plan plan = new Plan();
		//ControleurApplication controleur = new ControleurApplication(plan);

	}
}
