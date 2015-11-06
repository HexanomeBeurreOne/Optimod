package controller.commands;

import model.Plan;
import model.FenetreLivraison;
import model.Livraison;


public class SupprimerLivraison implements Commande 
{
//------------------------------------------------- ATTRIBUTES	

	protected Livraison livraison;
	protected FenetreLivraison fenetre;
	protected Plan plan;
	
//------------------------------------------------- CONSTRUCTORS
	
	public SupprimerLivraison(Plan p, Livraison l, FenetreLivraison f)
	{
		plan = p;
		livraison = l;
		fenetre = f;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Supprime une livraison d'une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		plan.removeLivraison(livraison, fenetre);
	}

	/**
	 * Ajoute une livraison à une fenetre de livraison pour une demande
	 */
	@Override
	public void unExecute() 
	{
		plan.addLivraison(livraison, fenetre);
	}
	
}
