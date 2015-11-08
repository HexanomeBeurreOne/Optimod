package controller;

import model.Plan;
import model.Tournee;
import view.Fenetre;
import model.DemandeLivraisons;
import model.FactoryDemandeLivraisons;
import model.FactoryPlan;
import model.Livraison;
import model.FenetreLivraison;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControleurApplication(Plan p)
	{
		plan = p;
		fenetre = new Fenetre(plan, 0.83, this);
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
				this.plan.setDemandeLivraisons(dLTemp);
				this.plan.setTournee(new Tournee());
				//On active le bouton "calculer tournee
				fenetre.getBoutons().get(2).setEnabled(true);
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
		fenetre.getZoneMessage().setText("");
	}
	
	/**
	 * Créé une livraison à une adresse
	 */
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
}
