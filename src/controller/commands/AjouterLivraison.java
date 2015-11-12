package controller.commands;

import model.Livraison;
import model.FenetreLivraison;
import model.Plan;
import model.Adresse;

public class AjouterLivraison implements Commande 
{
//------------------------------------------------- ATTRIBUTES	

	protected Plan plan;
	protected int client;
	protected Adresse precLivraisonAdresse;
	protected Adresse nouvellelivraisonAdresse;

//------------------------------------------------- CONSTRUCTORS
		
	public AjouterLivraison(Plan unPlan, int unClient, Adresse precAdresse, Adresse nvLivraison)
	{
		plan = unPlan;
		client = unClient;
		precLivraisonAdresse = precAdresse;
		nouvellelivraisonAdresse = nvLivraison;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Ajoute une livraison à une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		plan.ajouterLivraison(client, precLivraisonAdresse, nouvellelivraisonAdresse);
	}

	/**
	 * Supprime une livraison d'une fenetre de livraison pour une demande
	 */
	@Override
	public void unExecute() 
	{
		plan.supprimerLivraison(nouvellelivraisonAdresse);
	}
	
}
