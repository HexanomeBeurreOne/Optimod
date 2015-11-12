package controller;

import model.Plan;
import model.Tournee;
import view.Fenetre;
import model.Adresse;
import model.DemandeLivraisons;
import model.factory.FactoryDemandeLivraisons;
import model.factory.FactoryPlan;
import model.Livraison;
import model.FenetreLivraison;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.commands.*;

public class ControleurApplication 
{
//------------------------------------------------- ATTRIBUTES	
	
	protected PilesEtats undoRedo;
	protected Fenetre fenetre;
	protected Plan plan;
	private double echelle;
	
	private boolean tourneeCalculee;
	private Object objetSelectionne;
	private Adresse adresseSelectionnee;
	private Livraison livraisonSelectionnee;
	private boolean etatAjouterLivraison;
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControleurApplication(Plan p, double e)
	{
		undoRedo = new PilesEtats();
		plan = p;
		echelle = e;
		fenetre = new Fenetre(plan, echelle, this);
		
		tourneeCalculee = false;
		objetSelectionne = new Object();
		adresseSelectionnee = new Adresse();
		livraisonSelectionnee = new Livraison();
		etatAjouterLivraison = false;
	}
		
//------------------------------------------------- METHODS
	
	public void undo()
	{
		//undoRedo.undo();
		System.out.println("undo");
	}
	
	public void redo()
	{
		//undoRedo.redo();
		System.out.println("redo");
	}
	
	public void chargerPlan() {
		FactoryPlan factoryPlan = new FactoryPlan();
		String fichier = chargerFichier("./data");
		
		if (fichier != null) {
	    	Plan planTemp  = factoryPlan.getPlan(fichier);
	    	
		    if (planTemp != null) {
		    	this.plan.setAdresses(planTemp.getAdresses());
				this.plan.setTroncons(planTemp.getTroncons());
				this.plan.setNom(planTemp.getNom());
				this.plan.setDemandeLivraisons(new DemandeLivraisons());
				this.plan.setTournee(new Tournee());
				fenetre.getBoutons().get(1).setEnabled(true);
				fenetre.getBoutons().get(2).setEnabled(false);
				fenetre.getBoutons().get(3).setEnabled(false);
				fenetre.getBoutons().get(4).setEnabled(false);
				tourneeCalculee = false;
				fenetre.getZoneMessage().setText("Vous pouvez charger une demande de livraisons");
	    	} else {
	    		JOptionPane.showMessageDialog(fenetre,
	    			    "Le fichier de plan est mal formé",
	    			    "Erreur",
	    			    JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	}
	
	public void chargerDemandeLivraisons() {
		FactoryDemandeLivraisons factoryDemandeLivraisons = new FactoryDemandeLivraisons();
		String fichier = chargerFichier("./data");
		
		if (fichier != null) {
			DemandeLivraisons dLTemp = factoryDemandeLivraisons.getDemandeLivraisons(fichier, this.plan);
			
			if(dLTemp != null) {
				
				ArrayList<Integer> infosCouleurs = new ArrayList<Integer>();
				List<FenetreLivraison> fenetreLivraisons = dLTemp.getFenetresLivraisons();
				for (int i = 0; i < fenetreLivraisons.size(); i++) {
					infosCouleurs.add(fenetreLivraisons.get(i).getLivraisons().size());
				}
				
				fenetre.genererCouleurs(infosCouleurs);
			
				this.plan.setDemandeLivraisons(dLTemp);
				this.plan.setTournee(new Tournee());
				
				//On active le bouton "calculer tournee
				fenetre.getBoutons().get(2).setEnabled(true);
				//On desactive les autres boutons
				fenetre.getBoutons().get(3).setEnabled(false);
				fenetre.getBoutons().get(4).setEnabled(false);
				
				tourneeCalculee = false;
				
				fenetre.getZoneMessage().setText("Vous pouvez calculer une tournée");
			} else {
				JOptionPane.showMessageDialog(fenetre,
					    "Le fichier de demande de livraisons est mal formé",
					    "Erreur",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private String chargerFichier(String path) {
		JFileChooser chooser = new JFileChooser(path);
	    int returnVal = chooser.showOpenDialog(new JFrame());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile().getPath();
	    }
	    return null;
	}
	
	public void calculerTournee () {
		plan.calculTournee();
		fenetre.getZoneMessage().setText("Vous pouvez cliquer sur une adresse ou une livraison");
		tourneeCalculee = true;
	}
	
	public void getObjetSelectionne(int x, int y) {
		
		if (tourneeCalculee) {
			objetSelectionne = plan.cherche(x, y);
			
			//Si l'utilisateur clique sur une Adresse ou une Livraison
			if (objetSelectionne != null) {
				
				//Si l'utilisateur clique sur une adresse
				if (objetSelectionne.getClass().getName() == "model.Adresse") {
					
					// On active le bouton pour ajouter une livraison
					fenetre.getBoutons().get(3).setEnabled(true);
					// On desactive le bouton pour supprimer une livraison
					fenetre.getBoutons().get(4).setEnabled(false);
					
					fenetre.getZoneMessage().setText("Vous pouvez cliquer sur \"Ajouter livraison\"");
					
				//Si l'utilisateur clique sur une Livraison
				} else if (objetSelectionne.getClass().getName() == "model.Livraison") {
					
					//Si l'utilisateur est dans une action d'action d'ajouter une Livraison
					if (etatAjouterLivraison) {
						
						int client = fenetre.saisirClient();
						
						//Si l'ajout d'une Livraison est un succes
						if( client != -1) {
							Adresse adressePrecedente = ((Livraison)objetSelectionne).getAdresse();
							ajouterLivraison(client, adresseSelectionnee, adressePrecedente);
							
							plan.setObjetSelectionne(adresseSelectionnee, false);
							//On desactive le bouton "Ajouter livraison"
							fenetre.getBoutons().get(3).setEnabled(false);
							
							fenetre.getZoneMessage().setText("Vous pouvez cliquer sur une adresse ou une livraison");
							etatAjouterLivraison = false;
							return;
						}
						
						//Si l'ajout d'une livraison echoue
						fenetre.getZoneMessage().setText("Vous pouvez cliquer sur \"Ajouter livraison\"");
						etatAjouterLivraison = false;
						return;
					}
					
					// On active le bouton pour supprimer une livraison
					fenetre.getBoutons().get(4).setEnabled(true);
					// On desactive le bouton pour ajouter une livraison
					fenetre.getBoutons().get(3).setEnabled(false);
					
					fenetre.getZoneMessage().setText("Vous pouvez cliquer sur \"Supprimer livraisons\"");
					
				}
				miseAJourObjetSelectionnee(objetSelectionne);
				return;
			}
			
			//Si objetSelectionne est null
			fenetre.getBoutons().get(3).setEnabled(false);
			fenetre.getBoutons().get(4).setEnabled(false);
			fenetre.getZoneMessage().setText("Vous pouvez cliquer sur une adresse ou une livraison");
			etatAjouterLivraison = false;
			miseAJourObjetSelectionnee(objetSelectionne);
		}
	}
	
	private void miseAJourObjetSelectionnee(Object objet) {
		
		if (objet!=null) {
			if (objet.getClass().getName() == "model.Adresse") {
				plan.setObjetSelectionne(adresseSelectionnee, false);
				adresseSelectionnee = (Adresse) objet;
				plan.setObjetSelectionne(adresseSelectionnee, true);
				// on deselectionne la livraison selectionn�e
				plan.setObjetSelectionne(livraisonSelectionnee, false);
				
			} else if (objet.getClass().getName() == "model.Livraison") {
				plan.setObjetSelectionne(livraisonSelectionnee, false);
				livraisonSelectionnee = (Livraison) objet;
				plan.setObjetSelectionne(livraisonSelectionnee, true);
				// on deselectionne l'adresse selectionn�e
				plan.setObjetSelectionne(adresseSelectionnee, false);
			}
		} else {
			// on deselectionne la livraison selectionn�e
			plan.setObjetSelectionne(livraisonSelectionnee, false);
			// on deselectionne l'adresse selectionn�e
			plan.setObjetSelectionne(adresseSelectionnee, false);
		}
	}
	
	public void actionAjouterLivraison () {
		etatAjouterLivraison = true;
		fenetre.getZoneMessage().setText("Veuillez sélectionner la livraison existante après laquelle placer votre nouvelle livraison");
	}
	
	public void actionSuprimerLivraison () {
		supprimerLivraison();
	}
	
	/**
	 * Créé une livraison à une adresse
	 */
	//Passé en parametre Adresse, Adresse, client, fenetre 
	public void ajouterLivraison(int client, Adresse adresseSelectionnee, Adresse adressePrecedente)
	{
		System.out.println(client+" "+adressePrecedente+" "+adresseSelectionnee);
		AjouterLivraison ajout = new AjouterLivraison(plan, client, adresseSelectionnee, adressePrecedente);
		undoRedo.addCommand(ajout);
	}
	
	/**
	 * Supprime une livraison de la tournee
	 */
	public void supprimerLivraison()
	{
		//TODO
//		SupprimerLivraison suppression = new SupprimerLivraison(plan, livraison, fenetre);
//		undoRedo.addCommand(suppression);
	}
	
	/**
	 * Gestion des touches saisies avec le clavier
	 */
	public void caractereSaisi(String codeCar)
	{
		switch(codeCar){
		case "undo" : 
			undo();
			break;
		case "redo" :
			redo();
			break;
		}
	}

	/**
	 * @return the echelle
	 */
	public double getEchelle() {
		return echelle;
	}

	/**
	 * @param echelle the echelle to set
	 */
	public void setEchelle(double echelle) {
		this.echelle = echelle;
	}
}
