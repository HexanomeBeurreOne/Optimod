package test;

import model.*;

public class main {

	public static void main(String[] args) {
		
		FactoryPlan factoryPlan = new FactoryPlan();
		
		// le fichier pass� en param�tre doit se trouver dans le dossier principal du projet
		Plan plan = factoryPlan.getPlan("plan20x20.xml");
		plan.affichePlan();
		
	}

}
