package controller.commands;

import model.Livraison;
import model.FenetreLivraison;
import model.Plan;


public class AjouterLivraison implements Commande 
{
//------------------------------------------------- ATTRIBUTES	

	protected Livraison livraison;
	protected FenetreLivraison fenetre;
	protected Plan plan;

//------------------------------------------------- CONSTRUCTORS
		
	public AjouterLivraison(Plan p,Livraison l, FenetreLivraison f)
	{
		plan = p;
		livraison = l;
		fenetre = f;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Ajoute une livraison à une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		plan.addLivraison(livraison, fenetre);
	}

	/**
	 * Supprime une livraison d'une fenetre de livraison pour une demande
	 */
	@Override
	public void unExecute() 
	{
		plan.removeLivraison(livraison, fenetre);
	}
	
}
