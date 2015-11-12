package controller.commands;

import model.Adresse;
import model.Plan;
import model.FenetreLivraison;
import model.Livraison;


public class SupprimerLivraison implements Commande 
{
//------------------------------------------------- ATTRIBUTES	

	protected Plan plan;
	protected int client;
	protected Adresse precLivraisonAdresse;
	protected Adresse livraisonAdresseSelectionnee;
	protected FenetreLivraison fenetreLivraison;
	
//------------------------------------------------- CONSTRUCTORS
	
	public SupprimerLivraison(Plan unPlan, int unClient, Adresse precAdresse, Adresse adSelectionne, FenetreLivraison fenetre)
	{
		plan = unPlan;
		client = unClient;
		precLivraisonAdresse = precAdresse;
		livraisonAdresseSelectionnee = adSelectionne;
		fenetreLivraison = fenetre;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Supprime une livraison d'une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		plan.supprimerLivraison(livraisonAdresseSelectionnee);
	}

	/**
	 * Ajoute une livraison à une fenetre de livraison pour une demande
	 */
	@Override
	public void unExecute() 
	{
		plan.ajouterLivraisonAvecFenetre(client, precLivraisonAdresse, livraisonAdresseSelectionnee, fenetreLivraison);
	}
	
}
