package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Tournee {

	/**
	 * Attributs
	 */
	private List<Etape> etapes;
	// Chemin qui mene de l'adresse de livraison de la derniere etape a l'entrepot, null si etapes est vide
	private Chemin retourEntrepot;
	private Adresse entrepot;
	private double heureDebut;
	private double heureFin;
	
	/**
	 * Constructeur
	 */
	// Constructeur par defaut, ne pas ENLEVER
	public Tournee() {
		
	}

	public Tournee(DemandeLivraisons demandeLivraisons, Integer[] ordreLivraisons, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		this.etapes = new ArrayList<Etape>();
		this.entrepot = demandeLivraisons.getEntrepot();
		this.heureDebut = demandeLivraisons.getHeureDepart();
		int idDepartEtape = entrepot.getId();
		int idArriveeEtape;
		List<Livraison> livraisons = demandeLivraisons.getAllLivraisons();
		for(int i = 0 ; i < livraisons.size() ; i++)
		{
			idArriveeEtape = livraisons.get(ordreLivraisons[i+1]-1).getAdresse().getId();
			Etape etape = new Etape(livraisons.get(ordreLivraisons[i+1]-1), plusCourtsChemins.get(idDepartEtape).get(idArriveeEtape));
			etapes.add(etape);
			idDepartEtape = idArriveeEtape;
		}
		if(idDepartEtape != entrepot.getId()) {
			retourEntrepot = plusCourtsChemins.get(idDepartEtape).get(entrepot.getId());
		}
		calculerHoraires();
	}
	
	public List<Etape> getEtapes() {
		return etapes;
	}
	
	public Chemin getRetourEntrepot() {
		return retourEntrepot;
	}
	
	public double getHeureDebut() {
		return heureDebut;
	}
	
	public Adresse getEntrepot() {
		return entrepot;
	}
	
	public double getHeureFin() {
		return heureFin;
	}
	
	public void calculerHoraires() {
		double heureDepartEtape = heureDebut;
		for(Etape etape : etapes) {
			etape.calculerHeureLivraison(heureDepartEtape);
			// 10 minutes de livraison avant de commencer l'etape suivante
			heureDepartEtape = etape.getHeureLivraison() + 10*60;
		}
		heureFin = heureDepartEtape + retourEntrepot.getTempsDeParcours();
		if(heureFin > 24*3600) System.out.println("La tournee se termine apr�s minuit.");
	}
	
	public int trouverIndiceEtape(Adresse adresse) {
		for(int i = 0; i < etapes.size() ; i++)	{
			if(etapes.get(i).getLivraison().getAdresse() == adresse) {
				return i;
			}
		}
		return -1;
	}
	
	//TODO : besoin de deux plus courts chemins si on remet une livraison dans une tournee vide
		
	public void supprimerEtape(int indiceEtape, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		etapes.remove(indiceEtape);
		if(etapes.size() == 0) {
			retourEntrepot = null;
			heureFin = heureDebut;
			return;
		}
		if(etapes.size() == indiceEtape) {
			// Si on a supprime la derniere etape on met a jour le chemin de retour
			int idAdresseDerniereEtape = etapes.get(indiceEtape-1).getLivraison().getAdresse().getId();
			retourEntrepot = plusCourtsChemins.get(idAdresseDerniereEtape).get(entrepot.getId());
		}
		else {
			int idAdressePrecedente;
			if(indiceEtape == 0){
				// Si on a supprime la premiere etape on met a jour le chemin de la nouvelle premiere en partant de l'entrepot
				idAdressePrecedente = entrepot.getId();
			}
			else {
				idAdressePrecedente = etapes.get(indiceEtape-1).getLivraison().getAdresse().getId();
			}
			int idAdresseEtape = etapes.get(indiceEtape).getLivraison().getAdresse().getId();
			etapes.get(indiceEtape).setChemin(plusCourtsChemins.get(idAdressePrecedente).get(idAdresseEtape));
		}
		calculerHoraires();
	}
	
	// TODO : Idee : Pourquoi pas un bouleen dans Etape pour savoir si on respecte sa fenetre
	
	// TODO : Reflechit aux adresses et fenetres, ca chie, pcq on compare des fenetres de liv des livraisons associees aux etapes
	// Donc faut aussi metre a jour les fenetres
	// Ou alors mettre les heures de debut et fin en attribut
	
	public void ajouterEtape(Livraison livraison, Adresse adresseLivraisonPrec, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		Chemin aller = plusCourtsChemins.get(adresseLivraisonPrec.getId()).get(livraison.getAdresse().getId());
		int indiceEtapePrec = trouverIndiceEtape(adresseLivraisonPrec);
		if(indiceEtapePrec == etapes.size()-1){
			// Si l'etape precedente est la derniere, le chemin de retour est mis a jour
			retourEntrepot = plusCourtsChemins.get(livraison.getAdresse().getId()).get(entrepot.getId());
		}
		else {
			// Sinon on met a jour le Chemin de l'etape suivante
			Etape etapeSuivante = etapes.get(indiceEtapePrec+1);
			Adresse adresseLivEtapeSuivante = etapeSuivante.getLivraison().getAdresse();
			etapeSuivante.setChemin(plusCourtsChemins.get(livraison.getAdresse().getId()).get(adresseLivEtapeSuivante.getId()));
		}
		Etape etape = new Etape(livraison, aller);
		etapes.add(indiceEtapePrec+1, etape);
		calculerHoraires();
	}
	
	public String toString(){
		return "Tournee de " + etapes.size() + " etapes, " + 
				"debut a " + (int)heureDebut/3600 + ":"+ ((int)heureDebut%3600)/60 + ":"+ (int)heureDebut%60
				+ ", fin a " + (int)heureFin/3600 + ":"+ ((int)heureFin%3600)/60 + ":"+ (int)heureFin%60;
	
	}
	
}
