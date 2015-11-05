package test;

import java.util.TreeMap;

import model.*;
import view.*;

public class main {

	public static void main(String[] args) {
		
		FactoryPlan factoryPlan = new FactoryPlan();
		
		// le fichier pass� en param�tre doit se trouver dans le dossier principal du projet
		Plan plan = factoryPlan.getPlan("plan20x20.xml");
		//plan.affichePlan();
		
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		
		DemandeLivraisons demandeLivraisons = factoryDemandeLivraisons.getDemandeLivraisons("livraison20x20-1.xml", plan);

		plan.setDemandeLivraisons(demandeLivraisons);
		plan.getDemandeLivraisons().afficheDemandeLivraisons();
		
		/*
		Livraison newLivraison = new Livraison(666, plan.getAdresseById(399), demandeLivraisons.getFenetreLivraison(30600));
		plan.addLivraison(newLivraison, demandeLivraisons.getFenetreLivraison(30600));
		
		plan.getDemandeLivraisons().afficheDemandeLivraisons();

		Fenetre fenetre = new Fenetre(plan, 1);*/
	}

}
