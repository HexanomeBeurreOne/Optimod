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
	private Object objetSelectionne;
	private double echelle;
	private boolean tourneeCalculee;
	private Adresse adresseSelectionnee;
	private Livraison livraisonSelectionnee;
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControleurApplication(Plan p, double echelle)
	{
		adresseSelectionnee = new Adresse();
		livraisonSelectionnee = new Livraison();
		tourneeCalculee = false;
		undoRedo = new PilesEtats();
		plan = p;
		this.echelle = echelle;
		fenetre = new Fenetre(plan, echelle, this);
		objetSelectionne = new Object();
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
	    			    "Le fichier de plan est mal form√©",
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
				
				fenetre.getZoneMessage().setText("Vous pouvez calculer une tourn√©e");
			} else {
				JOptionPane.showMessageDialog(fenetre,
					    "Le fichier de demande de livraisons est mal form√©",
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
		fenetre.getZoneMessage().setText("");
		tourneeCalculee = true;
	}
	
	public void getObjetSelectionne(int x, int y) {
		
		if (tourneeCalculee) {
			objetSelectionne = plan.cherche(x, y);
			
			if (objetSelectionne != null) {
				
				if (objetSelectionne.getClass().getName() == "model.Adresse") {
					// On active le bouton pour ajouter une livraison
					fenetre.getBoutons().get(3).setEnabled(true);
					fenetre.getZoneMessage().setText("Vous pouvez cliquer sur \"Ajouter livraisons\"");
					
					miseAJourObjetSelectionnee(objetSelectionne);
					//adresseSelectionnee = (Adresse) adresseSelectionnee;
					return;
					
				} else if (objetSelectionne.getClass().getName() == "model.Livraison") {
					// On active le bouton pour supprimer une livraison
					fenetre.getBoutons().get(4).setEnabled(true);
					fenetre.getZoneMessage().setText("Vous pouvez cliquer sur \"Supprimer livraisons\"");
					
					miseAJourObjetSelectionnee(objetSelectionne);
					
					//livraisonSelectionnee = (Livraison) livraisonSelectionnee;
					return;
					
				}
			}
			
			//Si objetSelectionne est null
			fenetre.getBoutons().get(3).setEnabled(false);
			fenetre.getBoutons().get(4).setEnabled(false);
			fenetre.getZoneMessage().setText("");
			miseAJourObjetSelectionnee(objetSelectionne);
		}
	}
	
	private void miseAJourObjetSelectionnee(Object objet) {
		if (objet!=null) {
			if (objet.getClass().getName() == "model.Adresse") {
				plan.setObjetSelectionne(adresseSelectionnee, false);
				adresseSelectionnee = (Adresse) objet;
				plan.setObjetSelectionne(adresseSelectionnee, true);
				// on deselectionne la livraison selectionnÈe
				plan.setObjetSelectionne(this.livraisonSelectionnee, false);
			} else if (objet.getClass().getName() == "model.Livraison") {
				plan.setObjetSelectionne(livraisonSelectionnee, false);
				livraisonSelectionnee = (Livraison) objet;
				plan.setObjetSelectionne(livraisonSelectionnee, true);
				// on deselectionne l'adresse selectionnÈe
				plan.setObjetSelectionne(this.adresseSelectionnee, false);
				System.out.println("LOL CONTROLEUR");
			}
		} else {
			// on deselectionne la livraison selectionnÈe
			plan.setObjetSelectionne(this.livraisonSelectionnee, false);
			// on deselectionne l'adresse selectionnÈe
			plan.setObjetSelectionne(this.adresseSelectionnee, false);
		}
	}
	
	public void actionAjouterLivraison () {
		
	}
	
	public void actionSuprimerLivraison () {
		
	}
	
	/**
	 * Cr√©√© une livraison √† une adresse
	 */
	//Pass√© en parametre Adresse, Adresse, client, fenetre 
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
