package controller;

import model.Plan;
import model.DemandeLivraisons;
import model.Livraison;
import model.FenetreLivraison;
import controller.commands.*;

public class ControleurApplication 
{
//------------------------------------------------- ATTRIBUTES	
	
	protected PilesEtats undoRedo;

	protected Plan plan;
	protected Livraison livraison;
	protected FenetreLivraison fenetre;
	protected DemandeLivraisons demande;
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControleurApplication(Plan p)
	{
		plan = p;
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
	
	/**
	 * Créé une livraison à une adresse
	 */
	public void addLivraison()
	{
		AjouterLivraison ajout = new AjouterLivraison(plan, livraison, fenetre);
		undoRedo.addCommand(ajout);
	}
	
	/**
	 * Supprime une livraison de la tournee
	 */
	public void removeLivraison()
	{
		SupprimerLivraison suppression = new SupprimerLivraison(plan, livraison, fenetre);
		undoRedo.addCommand(suppression);
	}
}
