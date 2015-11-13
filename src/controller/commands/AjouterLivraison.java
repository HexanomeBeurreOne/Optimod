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
	protected FenetreLivraison fenetreLivraison;

//------------------------------------------------- CONSTRUCTORS
		
	public AjouterLivraison(Plan unPlan, int unClient, Adresse precAdresse, Adresse nvLivraison, FenetreLivraison fenetre)
	{
		plan = unPlan;
		client = unClient;
		precLivraisonAdresse = precAdresse;
		nouvellelivraisonAdresse = nvLivraison;
		fenetreLivraison = fenetre;
	}
	
//------------------------------------------------- METHODS
	/**
	 * Ajoute une livraison a une fenetre de livraison pour une demande
	 */
	@Override
	public void execute() 
	{
		plan.ajouterLivraisonAvecFenetre(client, precLivraisonAdresse, nouvellelivraisonAdresse, fenetreLivraison);
	}

	/**
	 * Supprime une livraison d'une fenetre de livraison pour une demande
	 */
	@Override
	public void unExecute() 
	{
		plan.supprimerLivraison(nouvellelivraisonAdresse);
		System.out.println("unexecute");
	}
	
}
