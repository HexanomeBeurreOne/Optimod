package test;

import controller.ControleurApplication;
import model.*;
import view.*;

public class main {

	public static void main(String[] args) {
		
		
		FactoryPlan factoryPlan = new FactoryPlan();
		
		// Le fichier passe en parametre doit se trouver dans le dossier principal du projet
		Plan plan = factoryPlan.getPlan("data/plan20x20.xml");
		
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", plan);

		plan.setDemandeLivraisons(demandeLivraisons);
		
		
		//ControleurApplication controller = new ControleurApplication(plan);
		//Fenetre fenetre = new Fenetre(plan, 0.83, controller);

		plan.calculTournee();

	}
}
