package controller.commands;

import model.Livraison;
import model.FenetreLivraison;
import model.DemandeLivraisons;


public class AjouterLivraison implements Commande 
{
//------------------------------------------------- ATTRIBUTES	

	protected Livraison livraison;
	protected FenetreLivraison fenetre;
	protected DemandeLivraisons demande;

//------------------------------------------------- CONSTRUCTORS
		
	public AjouterLivraison(Livraison l, FenetreLivraison f, DemandeLivraisons d)
	{
		livraison = l;
		fenetre = f;
		demande = d;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Ajoute une livraison à une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		demande.addLivraison(livraison, fenetre);
	}

	@Override
	public void unExecute() 
	{
		//demande.removeLivraison(livraison, fenetre);
	}
	
}
