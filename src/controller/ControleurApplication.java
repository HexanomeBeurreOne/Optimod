package controller;

import model.Plan;
import view.Fenetre;
import model.DemandeLivraisons;
import model.FactoryDemandeLivraisons;
import model.FactoryPlan;
import model.Livraison;
import model.FenetreLivraison;
import controller.commands.*;

public class ControleurApplication 
{
//------------------------------------------------- ATTRIBUTES	
	
	protected PilesEtats undoRedo;

	protected Fenetre fenetre;
	protected Plan plan;
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControleurApplication(Plan p)
	{
		plan = p;
		fenetre = new Fenetre(plan, 0.83, this);
		undoRedo = new PilesEtats();
	}
		
//------------------------------------------------- METHODS
	
	public void undo()
	{
		undoRedo.undo();
	}
	
	public void redo()
	{
		undoRedo.redo();
	}
	
	public void chargerPlan() {
		//
		FactoryPlan factoryPlan = new FactoryPlan();
		Plan planTemp = factoryPlan.getPlan("data/plan20x20.xml");
		this.plan.setAdresses(planTemp.getAdresses());
		this.plan.setTroncons(planTemp.getTroncons());
		this.plan.setNom(planTemp.getNom());
	}
	
	public void chargerDemandeLivraisons() {
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		DemandeLivraisons dLTemp = factoryDemandeLivraisons.getDemandeLivraisons("data/livraison20x20-1.xml", this.plan);
		this.plan.setDemandeLivraisons(dLTemp);
	}
	
	/**
	 * Créé une livraison à une adresse
	 */
	public void addLivraison()
	{
//		AjouterLivraison ajout = new AjouterLivraison(plan, livraison, fenetre);
//		undoRedo.addCommand(ajout);
	}
	
	/**
	 * Supprime une livraison de la tournee
	 */
	public void removeLivraison()
	{
//		SupprimerLivraison suppression = new SupprimerLivraison(plan, livraison, fenetre);
//		undoRedo.addCommand(suppression);
	}
}
