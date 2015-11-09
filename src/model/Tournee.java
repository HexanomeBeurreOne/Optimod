package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Tournee {

	/**
	 * Attributes
	 */
	private List<Etape> etapes;
	// Chemin qui mene de l'adresse de livraison de la derniere etape � l'entrepot
	private Chemin retourEntrepot;
	Adresse entrepot;
	private double heureDebut;
	private double heureFin;
	
	/**
	 * Constructor
	 */
	public Tournee(Adresse entrepot, int heureDebut, List<Livraison> livraisonsOrdonnees, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		this.etapes = new ArrayList<Etape>();
		this.heureDebut = heureDebut;
		this.entrepot = entrepot;
		int idDepartEtape = entrepot.getId();
		int idArriveeEtape = 0;
		Etape etape;
		for(int i = 0 ; i < livraisonsOrdonnees.size() ; i++)
		{
			idArriveeEtape = livraisonsOrdonnees.get(i).getAdresse().getId();
			etape = new Etape(livraisonsOrdonnees.get(i), plusCourtsChemins.get(idDepartEtape).get(idArriveeEtape));
			etapes.add(etape);
			idDepartEtape = idArriveeEtape;
		}
		retourEntrepot = plusCourtsChemins.get(idDepartEtape).get(entrepot.getId());
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
	
	public double getHeureFin() {
		return heureFin;
	}
	
	public void calculHoraires() {
		double heureDepartEtape = heureDebut;
		for(Etape etape : etapes) {
			etape.calculHeureLivraison(heureDepartEtape);
			// 10 minutes de livraison avant de commencer l'etape suivante
			heureDepartEtape = etape.getHeureLivraison() + 10*60;
		}
		heureFin = heureDepartEtape + retourEntrepot.getTempsDeParcours();
		if(heureFin > 24*3600) System.out.println("La tournee se termine apr�s minuit.");
	}
	
	public int findIndiceEtape(Livraison livraison) {
		boolean found = false;
		int i;
		for( i = 0; i < etapes.size() && !found ; )	{
			if(etapes.get(i).getLivraison() == livraison) {
				found = true;
			}
			else {
				i++;
			}
		}
		if(found) return i;
		return -1;
	}
	
	//TODO: Doit-on gerer la suppression de la seule etape de la tournee
	
	public Adresse[] testSuppression(int indiceEtape){
		Adresse[] extremites = new Adresse[2];
		if(indiceEtape == etapes.size()-1) {
			// Si on a supprime la derniere etape on met a jour le chemin de retour
			Adresse adresseDerniereEtape = etapes.get(indiceEtape-1).getLivraison().getAdresse();
			extremites[0] = adresseDerniereEtape;
			extremites[1] = entrepot;
		}
		else {
			Adresse adressePrecedente;
			if(indiceEtape == 0){
				// Si on a supprime la premiere etape on met a jour le chemin de la nouvelle premiere en partant de l'entrepot
				adressePrecedente = entrepot;
			}
			else {
				adressePrecedente = etapes.get(indiceEtape-1).getLivraison().getAdresse();
			}
			Adresse adresseEtape = etapes.get(indiceEtape+1).getLivraison().getAdresse();
			extremites[0] = adressePrecedente;
			extremites[1] = adresseEtape;
		}
		return extremites;
	}		
	
	public void supprimerEtape(int indiceEtape, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		etapes.remove(indiceEtape);
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
