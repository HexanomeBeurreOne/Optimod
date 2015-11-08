package test;

import controller.ControleurApplication;
import model.*;
import view.*;

public class main {

	public static void main(String[] args) {
		Plan plan = new Plan();
		ControleurApplication controller = new ControleurApplication(plan);
		
		/*
		plan.setDemandeLivraisons(demandeLivraisons);
		plan.getDemandeLivraisons().afficheDemandeLivraisons();
		
		
		Livraison newLivraison = new Livraison(666, plan.getAdresseById(399), demandeLivraisons.getFenetreLivraison(30600));
		plan.addLivraison(newLivraison, demandeLivraisons.getFenetreLivraison(30600));
		
		plan.getDemandeLivraisons().afficheDemandeLivraisons();
		*/
	}
}
