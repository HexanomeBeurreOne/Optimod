package controller;

import model.Plan;
import model.Tournee;
import view.Fenetre;
import model.Adresse;
import model.DemandeLivraisons;
import model.Etape;
import model.factory.FactoryDemandeLivraisons;
import model.factory.FactoryPlan;
import model.Livraison;
import model.FenetreLivraison;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
	/**
	 * Constructeur de ControleurApplication avec un plan et une ecchelle
	 * @param p Plan 
	 * @param e Echelle 
	 */
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
	/**
	 * Enregistre la feuille de route dans le fichier FeuilleDeRoute.txt
	 */
	public void enregistrerFeuilleRoute()
	{
		if(tourneeCalculee)
		{
			//TODO: toStrings
			String feuille = "\t\tFeuille de Route :\n\n";
			feuille += plan.getTournee().toString();
			
			//Save in doc
			String path = "./FeuilleDeRoute.txt";
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
					    new FileOutputStream(path), "UTF-8"));
				out.write(feuille);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			    try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Affiche un message avertissant de l'enregistrement de la feuille de route
			
			String msg = "La feuille de route à été enregistrée dans votre repertoire local :" + path;
			JOptionPane.showMessageDialog(fenetre,
					msg,
				    "Feuille de Route",
				    JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Enregistrer Feuille de Route");
		}
	}
	
	/**
	 * Annuler la derniere action
	 */
	public void undo()
	{
		undoRedo.undo();
		System.out.println("undo");
	}
	
	/**
	 * Retablir la derniere action
	 */
	public void redo()
	{
		undoRedo.redo();
		System.out.println("redo");
	}
	
	/**
	 * Recalcule l'echelle de la vuePlan pour que le plan soit toujours affiche dans son integralite
	 */
	private void changerEchelle() {
		double largeurPlan = (double) this.fenetre.getLargeurVuePlan();
		double hauteurPlan = (double) this.fenetre.getHauteurVuePlan();
		
		double minX = (double) this.plan.getMinX();
		double maxX = (double) this.plan.getMaxX();
		double minY = (double) this.plan.getMinY();
		double maxY = (double) this.plan.getMaxY();
		
		double echX = largeurPlan/(maxY-minY);
		double echY = hauteurPlan/(maxX-minX);
		
		double newEchelle = echX<=echY ? echX : echY;
		newEchelle = newEchelle>1 ? newEchelle*0.7 : newEchelle*0.9;
		
		this.fenetre.getVuePlan().setEchelle(newEchelle);
		this.echelle = newEchelle;
	}
	
	/**
	 * Charge le plan dans la vue graphique
	 */
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
				
				// on adapte l'echelle pour qu'elle corresponde parfaitement � la vue du plan
				this.changerEchelle();
				
				fenetre.getBoutons().get(1).setEnabled(true);
				fenetre.getBoutons().get(2).setEnabled(false);
				fenetre.getBoutons().get(3).setEnabled(false);
				fenetre.getBoutons().get(4).setEnabled(false);
				
				// on passe tous les anciens attributs d'�tat � leurs valeurs initiales
				tourneeCalculee = false;
				objetSelectionne = new Object();
				adresseSelectionnee = new Adresse();
				livraisonSelectionnee = new Livraison();
				etatAjouterLivraison = false;
				
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
		
		// on deselectionne tous les objets selectionn�s au chargement d'un nouveau fichier de demande livraison
		miseAJourObjetSelectionnee(null);
		if (fichier != null) {
			DemandeLivraisons dLTemp = factoryDemandeLivraisons.getDemandeLivraisons(fichier, this.plan);
			
			if(dLTemp != null) {
				
//				ArrayList<Integer> infosCouleurs = new ArrayList<Integer>();
//				List<FenetreLivraison> fenetreLivraisons = dLTemp.getFenetresLivraisons();
//				for (int i = 0; i < fenetreLivraisons.size(); i++) {
//					infosCouleurs.add(fenetreLivraisons.get(i).getLivraisons().size());
//				}
				int infosCouleurs = dLTemp.getFenetresLivraisons().size();
				
				fenetre.genererCouleurs(infosCouleurs);
			
				this.plan.setDemandeLivraisons(dLTemp);
				this.plan.setTournee(new Tournee());
				
				//On efface les piles undo/redo
				undoRedo.clear();
				
				//On active le bouton "calculer tournee
				fenetre.getBoutons().get(2).setEnabled(true);
				//On desactive les autres boutons
				fenetre.getBoutons().get(3).setEnabled(false);
				fenetre.getBoutons().get(4).setEnabled(false);
				
				//on passe tous les anciens attributs d'�tat � leurs valeurs initiales
				tourneeCalculee = false;
				objetSelectionne = new Object();
				adresseSelectionnee = new Adresse();
				livraisonSelectionnee = new Livraison();
				etatAjouterLivraison = false;
				
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
		undoRedo.clear();
		plan.calculTournee();
		fenetre.getZoneMessage().setText("Vous pouvez cliquer sur une adresse ou une livraison");
		tourneeCalculee = true;
	}
	
	public void getObjetSelectionne(int x, int y) {
		
		if (tourneeCalculee) {
			objetSelectionne = plan.cherche(x, y, 15);
			
			//Si l'utilisateur clique sur une Adresse ou une Livraison
			if (objetSelectionne != null) {
				
				//Si l'utilisateur clique sur l'entrepot
				if(objetSelectionne.getClass().getName() == "model.Adresse" && ((Adresse)objetSelectionne) == plan.getDemandeLivraisons().getEntrepot()) {
					//Seul cas : on ajoute une livraison apres l'entrepot					
					if (etatAjouterLivraison) {
						int client = fenetre.saisirClient();
						//Si l'ajout d'une Livraison est un succes
						if( client != -1) {
							FenetreLivraison fenetreL;
							if(plan.getTournee().getEtapes().size() == 0) {
								fenetreL = plan.getDemandeLivraisons().getFenetresLivraisons().get(0);
							} else {
								fenetreL = plan.getTournee().getEtapes().get(0).getLivraison().getFenetreLivraison();
							}
							Adresse adressePrecedente = plan.getDemandeLivraisons().getEntrepot();
	
							ajouterLivraison(client, adresseSelectionnee, adressePrecedente, fenetreL);
	
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
					//Si on est pas dans ajout de livraison on touche pas a l'entrepot
					fenetre.getBoutons().get(3).setEnabled(false);
					fenetre.getBoutons().get(4).setEnabled(false);
					fenetre.getZoneMessage().setText("Vous pouvez cliquer sur une adresse ou une livraison");
					etatAjouterLivraison = false;
					miseAJourObjetSelectionnee(objetSelectionne);		
					return;
				}
				//Si l'utilisateur clique sur une Livraison
				if (objetSelectionne.getClass().getName() == "model.Livraison") {
					
					//Si l'utilisateur est dans une action d'ajouter une Livraison
					if (etatAjouterLivraison) {
						
						int client = fenetre.saisirClient();
						
						//Si l'ajout d'une Livraison est un succes
						if( client != -1) {
							Livraison livraison = (Livraison)objetSelectionne;
							FenetreLivraison fenetreL = livraison.getFenetreLivraison();
							Adresse adressePrecedente = livraison.getAdresse();
							
							ajouterLivraison(client, adresseSelectionnee, adressePrecedente, fenetreL);

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
				//Si l'utilisateur clique sur une adresse
				if (objetSelectionne.getClass().getName() == "model.Adresse") {
					etatAjouterLivraison = false;
					// On active le bouton pour ajouter une livraison
					fenetre.getBoutons().get(3).setEnabled(true);
					// On desactive le bouton pour supprimer une livraison
					fenetre.getBoutons().get(4).setEnabled(false);
					
					fenetre.getZoneMessage().setText("Vous pouvez cliquer sur \"Ajouter livraison\"");

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
	
	public void actionSupprimerLivraison () {
		FenetreLivraison fenetreL = livraisonSelectionnee.getFenetreLivraison();
		Adresse adresseLivraison = livraisonSelectionnee.getAdresse();
		int client = livraisonSelectionnee.getClient();
		int indiceEtapeLivraisonSelectionnee = plan.getTournee().trouverIndiceEtape(adresseLivraison);
		
		Adresse adressePrecedente;
		if(indiceEtapeLivraisonSelectionnee>0) {
			Etape etapePrecedente = plan.getTournee().getEtapes().get( indiceEtapeLivraisonSelectionnee-1);
			adressePrecedente = etapePrecedente.getLivraison().getAdresse(); 
		} else {
			adressePrecedente = plan.getTournee().getEntrepot();
		}
		
		supprimerLivraison(client, adresseLivraison, adressePrecedente, fenetreL);
		livraisonSelectionnee = new Livraison();
		fenetre.getBoutons().get(4).setEnabled(false);
		fenetre.getZoneMessage().setText("Vous pouvez cliquer sur une adresse ou une livraison");
			
	}
		
	
	
	/**
	 * Créé une livraison à une adresse
	 */
	//Passé en parametre Adresse, Adresse, client, fenetre 
	public void ajouterLivraison(int client, Adresse adresseSelectionnee, Adresse adressePrecedente, FenetreLivraison fenetre)
	{
		System.out.println(client+" "+adressePrecedente+" "+adresseSelectionnee);
		AjouterLivraison ajout = new AjouterLivraison(plan, client, adressePrecedente, adresseSelectionnee, fenetre);
		undoRedo.addCommand(ajout);
	}
	
	/**
	 * Supprime une livraison de la tournee
	 */
	public void supprimerLivraison(int client, Adresse adresseSelectionnee, Adresse adressePrecedente, FenetreLivraison fenetre)
	{
		SupprimerLivraison suppression = new SupprimerLivraison(plan, client, adressePrecedente, adresseSelectionnee, fenetre);
		undoRedo.addCommand(suppression);
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
