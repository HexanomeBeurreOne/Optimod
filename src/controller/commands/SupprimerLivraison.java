package controller.commands;

import model.DemandeLivraisons;
import model.FenetreLivraison;
import model.Livraison;


public class SupprimerLivraison implements Commande 
{
//------------------------------------------------- ATTRIBUTES	

	protected Livraison livraison;
	protected FenetreLivraison fenetre;
	protected DemandeLivraisons demande;
	
//------------------------------------------------- CONSTRUCTORS
	
	public SupprimerLivraison(Livraison l, FenetreLivraison f, DemandeLivraisons d)
	{
		livraison = l;
		fenetre = f;
		demande = d;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Supprime une livraison d'une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		demande.removeLivraison(livraison, fenetre);
	}

	/**
	 * Ajoute une livraison à une fenetre de livraison pour une demande
	 */
	@Override
	public void unExecute() 
	{
		demande.addLivraison(livraison, fenetre);
	}
	
}
