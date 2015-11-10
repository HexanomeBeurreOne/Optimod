package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Tournee {

	/**
	 * Attributes
	 */
	private List<Etape> etapes;
	// Chemin qui mene de l'adresse de livraison de la derniere etape a l'entrepot
	private Chemin retourEntrepot;
	private Adresse entrepot;
	private double heureDebut;
	private double heureFin;
	
	/**
	 * Constructor
	 */
	public Tournee(DemandeLivraisons demandeLivraisons, Integer[] ordreLivraisons, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		this.etapes = new ArrayList<Etape>();
		this.entrepot = demandeLivraisons.getEntrepot();
		this.heureDebut = demandeLivraisons.getHeureDepart();
		this.retourEntrepot = null;
		int idDepartEtape = entrepot.getId();
		int idArriveeEtape = entrepot.getId();
		Etape etape;
		List<Livraison> livraisons = demandeLivraisons.getAllLivraisons();
		for(int i = 1 ; i < ordreLivraisons.length ; i++)
		{
			idArriveeEtape = livraisons.get(ordreLivraisons[i]-1).getAdresse().getId();
			etape = new Etape(livraisons.get(ordreLivraisons[i]-1), plusCourtsChemins.get(idDepartEtape).get(idArriveeEtape));
			etapes.add(etape);
			idDepartEtape = idArriveeEtape;
		}
		if (idDepartEtape != entrepot.getId()) {
			this.retourEntrepot = plusCourtsChemins.get(idDepartEtape).get(entrepot.getId());
		}
		calculHoraires();
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
	
	public void calculHoraires() {
		if(etapes.size() == 0) {
			heureFin = heureDebut;
			return;
		}
		double heureDepartEtape = heureDebut;
		for(Etape etape : etapes) {
			etape.calculHeureLivraison(heureDepartEtape);
			// 10 minutes de livraison avant de commencer l'etape suivante
			heureDepartEtape = etape.getHeureLivraison() + 10*60;
		}
		heureFin = heureDepartEtape + retourEntrepot.getTempsDeParcours();
		if(heureFin > 24*3600) System.out.println("La tournee se termine apres minuit.");
	}
	
	public int getIndiceEtape(Livraison livraison) {
		for(int i = 0; i < etapes.size() ; i++)	{
			if(etapes.get(i).getLivraison() == livraison) {
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
		calculHoraires();
	}

	/*
	 * Get adresses from the same fenetre of an etape index.
	 * @param etapeIndex
	 */
	public List<Adresse> getAdressesSameFenetre(int etapeIndex)	{
		List<Adresse> adressesOfTheSameFenetre = new ArrayList<Adresse>();
		if(etapeIndex < etapes.size())	{
			FenetreLivraison fenLivraison = etapes.get(etapeIndex).getLivraison().getFenetreLivraison();
			for(int i = 0; i < etapes.size(); i++)	{
				if(etapes.get(i).getLivraison().getFenetreLivraison() == fenLivraison)	{
					adressesOfTheSameFenetre.add(etapes.get(i).getLivraison().getAdresse());
				}
			}
		}
		return adressesOfTheSameFenetre;
	}
	
	public String toString(){
		return "Tournee de " + etapes.size() + " etapes, " + 
				"debut a " + (int)heureDebut/3600 + ":"+ ((int)heureDebut%3600)/60 + ":"+ (int)heureDebut%60
				+ ", fin a " + (int)heureFin/3600 + ":"+ ((int)heureFin%3600)/60 + ":"+ (int)heureFin%60;
	
	}
	
}
